package com.techchallenge.infrastructure.gateway;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import com.techchallenge.core.exceptions.BusinessException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.techchallenge.MysqlTestConfig;
import com.techchallenge.application.gateway.CustomerGateway;
import com.techchallenge.domain.entity.Customer;
import com.techchallenge.infrastructure.persistence.entity.CustomerEntity;
import com.techchallenge.infrastructure.persistence.mapper.CustomerEntityMapper;
import com.techchallenge.infrastructure.persistence.repository.CustomerRepository;
import org.testcontainers.utility.DockerImageName;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@Testcontainers
@TestPropertySource(locations = {"classpath:application-test.properties"})
@ContextConfiguration( classes = {MysqlTestConfig.class})
class CustomerIntegrationRepositoryGatewayIT {

    @SuppressWarnings("rawtypes")
	@Container
    static MySQLContainer mySQLContainer = new MySQLContainer(DockerImageName.parse("mysql:8.0-debian"));

    CustomerGateway gateway;

    @Autowired
    CustomerRepository repository;

    @Autowired
    CustomerEntityMapper mapper;

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
        gateway = new CustomerRepositoryGateway(repository, mapper);  
        createCustomers();
    }

    @Nested
    class TestCpf {
        @Test
        void testFindByCpf() {
            Optional<Customer> customer = gateway.findByCpf("79377085063");
            assertTrue(customer.isPresent(), "Customer must be present");

            assertEquals("79377085063", customer.get().getCpfValue().orElse(""));
            assertEquals("Fred Flintstone", customer.get().getName());
            assertEquals("fred@email", customer.get().getEmail());
        }

        @Test
        void testNotFindByCpf() {
            Optional<Customer> customer = gateway.findByCpf("86959220068");
            assertFalse(customer.isPresent(), "Customer must be not present");
        }
    }

    @Nested
    class TestInsertCustomer {
        @Test
        void testSaveCustomer() {
            Customer catatau = getCustomer("03308250039", "Catatau", "catatau@email");
            gateway.insert(catatau);
            Optional<Customer> customer = gateway.findByCpf("03308250039");
            assertTrue(customer.isPresent(), "Customer must be present");

            assertEquals("03308250039", customer.get().getCpfValue().orElse(""));
            assertEquals("Catatau", customer.get().getName());
            assertEquals("catatau@email", customer.get().getEmail());
        }
    }

    @Nested
    class TestUpdateCustomer {

        @Test
        void testUpdateCustomer() {
            Customer caverna = getCustomer("15324406007", "Caverna Capitão", "caverna@email");
            gateway.update(caverna);
            Optional<Customer> customer = gateway.findByCpf("15324406007");
            assertTrue(customer.isPresent(), "Customer must be present");

            assertEquals("15324406007", customer.get().getCpfValue().orElse(""));
            assertEquals("Caverna Capitão", customer.get().getName());
            assertEquals("caverna@email", customer.get().getEmail());
        }

        @Test
        void testUpdateCustomerWhenNotExists() {
            Customer caverna = getCustomer("38717597080", "XXX", "xx@email");
            assertThrows(BusinessException.class, () -> gateway.update(caverna));
        }
    }

    @Nested
    class TestDeleteCustomer {
        @Test
        void testDeleteCustomer() {
            Customer customer = gateway.findByCpf("24852276080").orElse(null);
            gateway.delete(customer);
            Optional<Customer> maybeCustomer = gateway.findByCpf("24852276080");
            assertFalse(maybeCustomer.isPresent(), "Customer must be present");
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