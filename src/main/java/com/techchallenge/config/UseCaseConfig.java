package com.techchallenge.config;

import com.techchallenge.application.gateway.CustomerGateway;
import com.techchallenge.application.usecase.CustomerUseCase;
import com.techchallenge.application.usecase.interactor.CustomerUseCaseInteractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CustomerUseCase customerUseCase(CustomerGateway customerGateway) {
        return new CustomerUseCaseInteractor(customerGateway);
    }

}
