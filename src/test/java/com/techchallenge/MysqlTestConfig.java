package com.techchallenge;

import com.techchallenge.config.KafkaConfig;
import com.techchallenge.core.kafka.KafkaConsumerConfig;
import com.techchallenge.core.kafka.produce.TopicProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.mockito.Mockito.mock;

//@Configuration
@ComponentScan(value = {"com.techchallenge"})
@EnableJpaRepositories
public class MysqlTestConfig {

    @Value(value = "${kafka.topic.consumer.error.groupId}")
    private String groupIdOrders;

    @Value(value = "${kafka.topic.consumer.error.payment}")
    private String groupIdPayment;


    @Primary
    @Bean
    public KafkaConfig kafkaConfigTest(){
        return mock(KafkaConfig.class);
    }

    @Primary
    @Bean
    public KafkaConsumerConfig kafkaConsumerTest(){
        return mock(KafkaConsumerConfig.class);
    }
	
	

}
