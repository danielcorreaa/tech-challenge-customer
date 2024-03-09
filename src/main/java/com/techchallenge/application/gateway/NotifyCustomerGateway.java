package com.techchallenge.application.gateway;

import com.techchallenge.domain.entity.NotifyCustomer;

import java.util.List;

public interface NotifyCustomerGateway {

    NotifyCustomer insert(NotifyCustomer notifyCustomer);

    List<NotifyCustomer> findNotSent();
}
