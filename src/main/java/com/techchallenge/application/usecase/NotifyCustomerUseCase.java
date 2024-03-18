package com.techchallenge.application.usecase;

import com.techchallenge.domain.entity.NotifyCustomer;

import java.util.List;

public interface NotifyCustomerUseCase {

    NotifyCustomer insert(NotifyCustomer notifyCustomer);

    List<NotifyCustomer> findNotSent();

    NotifyCustomer findOrderId(String orderId);
}
