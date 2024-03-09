package com.techchallenge;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(value = {"com.techchallenge"})
@EnableJpaRepositories
public class KafkaTestConfig {

    @Value(value = "${kafka.topic.consumer.error.groupId}")
    private String groupIdOrders;

    @Value(value = "${kafka.topic.consumer.error.payment}")
    private String groupIdPayment;
}


