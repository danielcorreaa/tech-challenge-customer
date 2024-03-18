package com.techchallenge.config;

import com.techchallenge.application.usecase.CustomerUseCase;
import com.techchallenge.application.usecase.NotifyCustomerUseCase;
import com.techchallenge.core.kafka.KafkaConsumerConfig;
import com.techchallenge.infrastructure.message.MessagePaymentDto;
import com.techchallenge.infrastructure.message.consumer.PaymentConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${kafka.topic.consumer.error.payment}")
    private String topicError;

    @Value(value = "${kafka.topic.consumer.error.groupId}")
    private String groupId;

    @Bean
    public KafkaConsumerConfig kafkaConsumer(){
        return new KafkaConsumerConfig(bootstrapAddress, groupId);
    }

    @Bean
    public ConsumerFactory<String, MessagePaymentDto> consumerFactoryMessagePaymentDto(){
        return kafkaConsumer().consumerFactory(jsonDeserializer(new JsonDeserializer<>(MessagePaymentDto.class)));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MessagePaymentDto> kafkaListenerContainerFactoryMessagePaymentDto(){
        return kafkaConsumer().kafkaListenerContainerFactory(consumerFactoryMessagePaymentDto());
    }

    public <T> JsonDeserializer<T> jsonDeserializer(JsonDeserializer<T> deserializer){
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);
        return deserializer;
    }

}
