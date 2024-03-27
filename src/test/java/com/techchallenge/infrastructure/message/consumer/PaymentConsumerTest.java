package com.techchallenge.infrastructure.message.consumer;

import com.techchallenge.KafkaTestConfig;
import com.techchallenge.application.usecase.CustomerUseCase;
import com.techchallenge.application.usecase.NotifyCustomerUseCase;
import com.techchallenge.core.kafka.KafkaProducerConfig;
import com.techchallenge.core.kafka.produce.TopicProducer;
import com.techchallenge.core.response.JsonUtils;
import com.techchallenge.core.response.ObjectMapperConfig;
import com.techchallenge.core.utils.FileUtils;
import com.techchallenge.domain.entity.Customer;
import com.techchallenge.domain.entity.NotifyCustomer;
import com.techchallenge.infrastructure.message.MessagePaymentDto;
import com.techchallenge.infrastructure.persistence.entity.CustomerEntity;
import com.techchallenge.infrastructure.persistence.entity.NotifyCustomerEntity;
import com.techchallenge.infrastructure.persistence.mapper.CustomerEntityMapper;
import com.techchallenge.infrastructure.persistence.repository.CustomerRepository;
import com.techchallenge.infrastructure.persistence.repository.NotifyCustomerRespository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration( classes = {KafkaTestConfig.class})
@TestPropertySource(locations = {"classpath:application-test.properties"})
@Testcontainers
class PaymentConsumerTest {

    JsonUtils jsonUtils;

    PaymentConsumer paymentConsumer;

    @Autowired
    NotifyCustomerRespository notifyCustomerRespository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerEntityMapper mapper;

    @Autowired
    private NotifyCustomerUseCase notifyCustomerUseCase;
    @Autowired
    private  CustomerUseCase customerUseCase;



    @Value(value = "${kafka.topic.consumer.error.payment}")
    String topic;

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
    void start(){
        paymentConsumer = new PaymentConsumer(customerUseCase, notifyCustomerUseCase);
        jsonUtils = new JsonUtils(new ObjectMapperConfig().objectMapper());
        cleandb();
        createCustomers();
    }

    @Test
    void testListenPayment_withSuccess() throws InterruptedException {
        MessagePaymentDto messagePaymentDto =
                jsonUtils.parse(new FileUtils().getFile("/data/payment-error-message.json"), MessagePaymentDto.class).get();

        topicProducer().produce(messagePaymentDto);
        boolean messageConsumed = paymentConsumer.getLatch().await(10, TimeUnit.SECONDS);
        List<NotifyCustomer> production = notifyCustomerUseCase.findNotSent();

        assertEquals(1,production.size());

    }

    public KafkaProducerConfig kafkaProducer(){
        return new KafkaProducerConfig(kafkaContainer.getBootstrapServers());
    }


    public ProducerFactory<String, MessagePaymentDto> producerFactory(){
        return kafkaProducer().producerFactory();
    }

    @Bean
    public KafkaTemplate<String, MessagePaymentDto> kafkaTemplate() {
        return kafkaProducer().kafkaTemplate();
    }

    @Bean
    public TopicProducer<MessagePaymentDto> topicProducer(){
        return new TopicProducer<>(kafkaTemplate(), topic);
    }
    private void cleandb() {
        notifyCustomerRespository.deleteAll();
        customerRepository.deleteAll();
    }


    void createCustomers() {
        List<CustomerEntity> entities =  getListCustomer().stream()
                .map(customer -> mapper.toCustomerEntity(customer)).toList();
        entities.stream().filter(ent -> customerRepository.findByCpf(ent.getCpf())
                .isEmpty()).forEach(customerRepository::save);
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