package com.techchallenge.application.usecase.interactor;

import com.techchallenge.application.gateway.NotifyCustomerGateway;
import com.techchallenge.application.usecase.NotifyCustomerUseCase;
import com.techchallenge.domain.entity.NotifyCustomer;

import java.util.List;

public class NotifyCustomerUseCaseInteractor implements NotifyCustomerUseCase {

    private NotifyCustomerGateway notifyCustomerGateway;

    public NotifyCustomerUseCaseInteractor(NotifyCustomerGateway notifyCustomerGateway) {
        this.notifyCustomerGateway = notifyCustomerGateway;
    }

    @Override
    public NotifyCustomer insert(NotifyCustomer notifyCustomer) {
        return notifyCustomerGateway.insert(notifyCustomer);
    }

    @Override
    public List<NotifyCustomer> findNotSent() {
        return notifyCustomerGateway.findNotSent();
    }
}
