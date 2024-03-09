package com.techchallenge.infrastructure.message.consumer;

import com.techchallenge.application.usecase.CustomerUseCase;
import com.techchallenge.application.usecase.NotifyCustomerUseCase;
import com.techchallenge.domain.entity.Customer;
import com.techchallenge.domain.entity.NotifyCustomer;
import com.techchallenge.infrastructure.message.MessagePaymentDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import java.util.concurrent.CountDownLatch;

@Log4j2
public class PaymentConsumer {

    private final CustomerUseCase customerUseCase;
    private final NotifyCustomerUseCase notifyCustomerUseCase;

    private CountDownLatch latch = new CountDownLatch(1);

    public PaymentConsumer(CustomerUseCase customerUseCase, NotifyCustomerUseCase notifyCustomerUseCase) {
        this.customerUseCase = customerUseCase;
        this.notifyCustomerUseCase = notifyCustomerUseCase;
    }

    @KafkaListener(topics = "${kafka.topic.consumer.error.payment}",
            groupId = "${kafka.topic.consumer.error.payment}",
            containerFactory = "kafkaListenerContainerFactoryMessagePaymentDto")
    public void listenPayment(MessagePaymentDto message, Acknowledgment ack) {
        log.info("Received Message Error Payment: {}", message);
        try {
            String description = "O seu pedido teve o pagamento reprovado!";
            Customer customer = customerUseCase.findByCpf(message.cpfCustomer());
            NotifyCustomer notifyCustomer = new NotifyCustomer(customer,description, message.externalReference());
            notifyCustomerUseCase.insert(notifyCustomer);
            ack.acknowledge();
            latch.countDown();
        }catch (Exception ex){
            log.error("Message not process: "+ ex.getMessage());
        }
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
