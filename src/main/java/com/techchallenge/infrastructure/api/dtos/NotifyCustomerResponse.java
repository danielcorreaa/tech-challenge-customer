package com.techchallenge.infrastructure.api.dtos;

import com.techchallenge.domain.entity.Customer;
import lombok.Builder;

@Builder
public record NotifyCustomerResponse(CustomerResponse customer,
        String description,
        Boolean notifySent,
        String orderId) {
}
