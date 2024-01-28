package com.techchallenge.infrastructure.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techchallenge.MysqlTestConfig;
import com.techchallenge.application.gateway.CustomerGateway;
import com.techchallenge.application.usecase.CustomerUseCase;
import com.techchallenge.application.usecase.interactor.CustomerUseCaseInteractor;
import com.techchallenge.core.exceptions.handler.ExceptionHandlerConfig;
import com.techchallenge.core.response.JsonUtils;
import com.techchallenge.core.response.Result;
import com.techchallenge.core.utils.FileUtils;
import com.techchallenge.domain.entity.Customer;
import com.techchallenge.infrastructure.api.dtos.CustomerRequest;
import com.techchallenge.infrastructure.api.dtos.CustomerResponse;
import com.techchallenge.infrastructure.api.mapper.CustomerMapper;
import com.techchallenge.infrastructure.gateway.CustomerRepositoryGateway;
import com.techchallenge.infrastructure.persistence.entity.CustomerEntity;
import com.techchallenge.infrastructure.persistence.mapper.CustomerEntityMapper;
import com.techchallenge.infrastructure.persistence.repository.CustomerRepository;
import io.restassured.RestAssured;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerApiIT {

    @Autowired
    CustomerEntityMapper mapper;
    @Autowired
    CustomerRepository repository;

    @LocalServerPort
    private int port;
    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer(DockerImageName.parse("mysql:8.0-debian"));

    @DynamicPropertySource
    public static void configureTestProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto",() -> "create");
    }
    @BeforeAll
    static void init(){
        mySQLContainer.withReuse(true);
        mySQLContainer.start();
    }
    @AfterAll
    static void end(){
        mySQLContainer.stop();
    }
    @BeforeEach
    void up(){
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        createCustomers();
    }


    @Nested
    class TestInsertCustomer {

        @Test
        void testInsertCustomerFieldsInvalidCpf() throws Exception {
            CustomerRequest request = new CustomerRequest("12345568911", "Ze Comeia", "comeia@email.com");
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when()
                    .post("/api/v1/customers")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("$", hasKey("errors"))
                    .body("errors[0]", equalTo("Invalid Cpf!"));
        }

        @Test
        void testInsertCustomerFieldsInvalidRequest() throws Exception {
            CustomerRequest request = new CustomerRequest("", "", "comeia@email.com");
            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when()
                    .post("/api/v1/customers")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("$", hasKey("errors"))
                    .body("errors[0]", equalTo("Cpf is required!"))
                    .body("errors[1]", equalTo("Cpf without correct number of digits!"))
                    .body("errors[2]", equalTo("Name is required!"));
        }

        @Test
        void testInsertCustomerFieldsValid() throws Exception {
            CustomerRequest request = new CustomerRequest("33672342068", "Ze Comeia", "comeia@email.com");
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when()
                    .post("/api/v1/customers")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .header("Content-Type", notNullValue())
                    .header("Content-Type", startsWith("application/json"))
                    .body(matchesJsonSchemaInClasspath("./data/customer-schema.json"))
                    .body(matchesJsonSchemaInClasspath("./data/customerInsert.json"));

        }
    }

    @Nested
    class TestUpdateCustomer {
        @Test
        void testUpdatetCustomerFieldsValid() throws Exception {
            CustomerRequest request = new CustomerRequest("09651313005", "Doug Funny Test", "doug@email.com");
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request).when().put("/api/v1/customers")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .header("Content-Type", notNullValue())
                    .header("Content-Type", startsWith("application/json"))
                    .body(matchesJsonSchemaInClasspath("./data/customer-schema.json"))
                    .body(matchesJsonSchemaInClasspath("./data/customerUpdate.json"));

        }
        @Test
        void testUpdatetCustomerNotFound() throws Exception {
            CustomerRequest request = new CustomerRequest("64438401003", "Ze Comeia", "comeia@email.com");
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request).when().put("/api/v1/customers")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("$", hasKey("errors"))
                    .body("errors[0]", equalTo("Customer not found for update"));
        }
    }

    @Nested
    class TestFindCpfCustomer {
        @Test
        void testFindByCpf() throws Exception {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/api/v1/customers/find/{cpf}", "79377085063")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .header("Content-Type", notNullValue())
                    .header("Content-Type", startsWith("application/json"))
                    .body(matchesJsonSchemaInClasspath("./data/customer-schema.json"))
                    .body(matchesJsonSchemaInClasspath("./data/customer.json"));
        }

        @Test
        void testFindByCpfNotFound() throws Exception {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("/api/v1/customers/find/{cpf}", "79377085063")
                    .then()
                    .statusCode(HttpStatus.OK.value());

        }
    }

    @Nested
    class TestDelete{

        @Test
        void testDeleteCustomer(){
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().delete("/api/v1/customers/delete/{cpf}", "15324406007")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("$", hasKey("code"))
                    .body("$", hasKey("body"))
                    .body("code", equalTo(200))
                    .body("body", equalTo("Custumer delete with success!"));
        }
    }

    void createCustomers() {
        List<CustomerEntity> entities =  getListCustomer().stream()
                .map(customer -> mapper.toCustomerEntity(customer)).toList();
        entities.stream().filter(ent -> repository.findByCpf(ent.getCpf()).isEmpty()).forEach(repository::save);
    }

    static List<Customer> getListCustomer(){
        return asList(
                getCustomer("09651313005", "Doug Funny","doug@email" ),
                getCustomer("79377085063", "Fred Flintstone","fred@email" ),
                getCustomer("37260972017", "Zé Colméia","ze@email" ),
                getCustomer("24852276080", "Tintim","tintim@email" ),
                getCustomer("76787174071", "Archibald Haddock","haddock@email" ),
                getCustomer("15324406007", "Capitão Caverna","caverna@email" )
        );
    }

    @NotNull
    static Customer getCustomer(String cpf, String name, String email) {
        return new Customer(cpf,name, email);
    }

}