package com.techchallenge.config;

import com.techchallenge.application.gateway.CustomerGateway;
import com.techchallenge.application.gateway.NotifyCustomerGateway;
import com.techchallenge.application.gateway.SolicitationExcludeGateway;
import com.techchallenge.application.usecase.CustomerUseCase;
import com.techchallenge.application.usecase.NotifyCustomerUseCase;
import com.techchallenge.application.usecase.SolicitationExcludeUseCase;
import com.techchallenge.application.usecase.interactor.CustomerUseCaseInteractor;
import com.techchallenge.application.usecase.interactor.NotifyCustomerUseCaseInteractor;
import com.techchallenge.application.usecase.interactor.SolicitationExcludeUseCaseInteractor;
import com.techchallenge.domain.entity.SolicitationExclude;
import com.techchallenge.infrastructure.message.consumer.PaymentConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CustomerUseCase customerUseCase(CustomerGateway customerGateway) {
        return new CustomerUseCaseInteractor(customerGateway);
    }

    @Bean
    public NotifyCustomerUseCase notifyCustomerUseCase(NotifyCustomerGateway notifyCustomerGateway) {
        return new NotifyCustomerUseCaseInteractor(notifyCustomerGateway);
    }

    public SolicitationExcludeUseCase solicitationExcludeUseCase(SolicitationExcludeGateway gateway){
        return  new SolicitationExcludeUseCaseInteractor(gateway);
    }

    @Bean
    public PaymentConsumer paymentConsumer(CustomerUseCase customerUseCase, NotifyCustomerUseCase notifyCustomerUseCase){
        return new PaymentConsumer(customerUseCase, notifyCustomerUseCase);
    }

}
