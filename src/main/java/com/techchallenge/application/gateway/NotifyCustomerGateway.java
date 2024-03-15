package com.techchallenge.application.gateway;

import com.techchallenge.domain.entity.NotifyCustomer;
import com.techchallenge.domain.entity.SolicitationExclude;

import java.util.List;
import java.util.Optional;

public interface NotifyCustomerGateway {

    NotifyCustomer insert(NotifyCustomer notifyCustomer);

    List<NotifyCustomer> findNotSent();

    Optional<NotifyCustomer> findOrderId(String orderId);
}
