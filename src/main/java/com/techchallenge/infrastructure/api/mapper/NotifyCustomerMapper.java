package com.techchallenge.infrastructure.api.mapper;

import com.techchallenge.domain.entity.NotifyCustomer;
import com.techchallenge.infrastructure.api.dtos.NotifyCustomerResponse;
import org.springframework.stereotype.Component;

@Component
public class NotifyCustomerMapper {

    private CustomerMapper mapper;

    public NotifyCustomerMapper(CustomerMapper mapper) {
        this.mapper = mapper;
    }

    public NotifyCustomerResponse toNotifyCustomer(NotifyCustomer notifyCustomer) {
        return NotifyCustomerResponse.builder()
                .customer(mapper.toCustomerResponse(notifyCustomer.getCustomer()))
                .description(notifyCustomer.getDescription())
                .notifySent(notifyCustomer.getNotifySent())
                .orderId(notifyCustomer.getOrderId())
                .build();
    }
}
