package com.techchallenge.infrastructure.api;

import com.techchallenge.application.usecase.SolicitationExcludeUseCase;
import com.techchallenge.domain.entity.Customer;
import com.techchallenge.domain.entity.SolicitationExclude;
import com.techchallenge.helper.SolicitationExcludeHelper;
import com.techchallenge.infrastructure.api.dtos.CustomerRequest;
import com.techchallenge.infrastructure.api.dtos.SolicitationExcludeRequest;
import com.techchallenge.infrastructure.persistence.entity.CustomerEntity;
import com.techchallenge.infrastructure.persistence.mapper.CustomerEntityMapper;
import com.techchallenge.infrastructure.persistence.mapper.SolicitationEntityMapper;
import com.techchallenge.infrastructure.persistence.repository.CustomerRepository;
import com.techchallenge.infrastructure.persistence.repository.SolicitationExcludeResository;
import io.restassured.RestAssured;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsMapContaining.hasKey;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SolicitationExcludeApiIT {

    @Autowired
    SolicitationEntityMapper mapper;
    @Autowired
    SolicitationExcludeResository repository;
    @Autowired
    SolicitationExcludeUseCase solicitationExcludeUseCase;

    @LocalServerPort
    private int port;
    @Container
    static MySQLContainer mySQLContainer =
            new MySQLContainer(DockerImageName.parse("mysql:8.0-debian"));

    @Container
    static KafkaContainer kafkaContainer =
            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.0"));

    @DynamicPropertySource
    static void overrrideMongoDBContainerProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);

        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);

    }
    @BeforeAll
    static void setUp(){
        mySQLContainer.withReuse(true);
        mySQLContainer.start();
        kafkaContainer.withReuse(true);
        kafkaContainer.start();
    }
    @AfterAll
    static void setDown(){
        mySQLContainer.stop();
        kafkaContainer.stop();
    }
    @BeforeEach
    void up(){
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        createSolicitationExcludes();
    }


    @Nested
    class TestInsertSolicitationExclude {

        @Test
        void testInsertSolicitationExclude_validation(){
            SolicitationExcludeRequest request =
                    new SolicitationExcludeRequest("", null
                            ,
                            "", "", "", "", "", "",
                            null);
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when()
                    .post("solicitation/api/v1")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body(matchesJsonSchemaInClasspath("./data/solicitation-exclude-validation-schema.json"));

        }


        @Test
        void testInsertNewSolicitationExclude(){
            SolicitationExcludeRequest request =
                    new SolicitationExcludeRequest("38846859103", "Ze Comeia",
                            "45336878", "Lins", "1652244", "SP", "xx", "122",
                            "test");
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when()
                    .post("solicitation/api/v1")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .header("Content-Type", notNullValue())
                    .header("Content-Type", startsWith("application/json"))
                    .body(matchesJsonSchemaInClasspath("./data/solicitation-exclude-schema.json"));

        }

        @Test
        void testInsertDuplicateSolicitationExclude_error(){
            SolicitationExcludeRequest request =
                    new SolicitationExcludeRequest("47388188053", "Ze Comeia",
                            "45336878", "Lins", "1652244", "SP", "xx", "122",
                            "test");
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .when()
                    .post("solicitation/api/v1")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("$", hasKey("errors"))
                    .body("errors[0]", equalTo("Solicitation has already been registered"));


        }
    }

    @Nested
    class TestFindSolicitationExclude {

        @Test
        void testFindByCpf() throws Exception {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("solicitation/api/v1/find/{cpf}", "47388188053")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .header("Content-Type", notNullValue())
                    .header("Content-Type", startsWith("application/json"))
                    .body(matchesJsonSchemaInClasspath("./data/solicitation-exclude-schema.json"));
        }

        @Test
        void testFindByCpfNotFound() throws Exception {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().get("solicitation/api/v1/find/{cpf}", "79377085063")
                    .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());

        }

        @Test
        void testFindAllSolicitation_waitedTodelete() throws Exception {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .get( "/solicitation/api/v1/find?exclude=false&page=0&size=10")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .header("Content-Type", notNullValue())
                    .header("Content-Type", startsWith("application/json"))
                    .body(matchesJsonSchemaInClasspath("./data/solicitation-exclude-schema-list.json"));

        }

        @Test
        void testFindAllSolicitation_deleted() throws Exception {
            given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .get( "/solicitation/api/v1/find?exclude=true&page=0&size=10")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .header("Content-Type", notNullValue())
                    .header("Content-Type", startsWith("application/json"))
                    .body(matchesJsonSchemaInClasspath("./data/solicitation-exclude-schema-list.json"));

        }
    }



    private void createSolicitationExcludes() {

        SolicitationExclude solicitationExclude =
                SolicitationExcludeHelper.solicitationExclude("47388188053");

        SolicitationExclude solicitationExcluded =
                SolicitationExcludeHelper.solicitationExclude("17442474802");

        if (!repository.existsById("47388188053")){
            solicitationExcludeUseCase.insert(solicitationExclude);
        }

        if (!repository.existsById("17442474802")){
            solicitationExcludeUseCase.insert(solicitationExcluded.toExclude());
        }
    }




}