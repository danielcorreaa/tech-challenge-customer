package com.techchallenge.infrastructure.persistence.mapper;

import com.techchallenge.domain.entity.Customer;
import com.techchallenge.domain.entity.NotifyCustomer;
import com.techchallenge.infrastructure.persistence.entity.CustomerEntity;
import com.techchallenge.infrastructure.persistence.entity.NotifyCustomerEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotifyCustomerMapper {

    private CustomerEntityMapper customerEntityMapper;

    public NotifyCustomerMapper(CustomerEntityMapper customerEntityMapper) {
        this.customerEntityMapper = customerEntityMapper;
    }

    public NotifyCustomerEntity toNotifyCustomerEntity(NotifyCustomer notifyCustomer){
        CustomerEntity customerEntity = customerEntityMapper.toCustomerEntity(notifyCustomer.getCustomer());
        return NotifyCustomerEntity.builder()
                .customerEntity(customerEntity)
                .notifySent(notifyCustomer.getNotifySent())
                .orderId(notifyCustomer.getOrderId())
                .description(notifyCustomer.getDescription())
                .build();

    }


    public NotifyCustomer toNotifyCustomer(NotifyCustomerEntity notifyCustomerEntity) {
        Customer customer = customerEntityMapper.toCustomer(notifyCustomerEntity.getCustomerEntity());
        return new NotifyCustomer().notifyCustomer(customer, notifyCustomerEntity.getDescription(),
                notifyCustomerEntity.getOrderId(), notifyCustomerEntity.getNotifySent());
    }

    public List<NotifyCustomer> toNotifyCustomers(List<NotifyCustomerEntity> entities) {
        return entities.stream().map(this::toNotifyCustomer).toList();
    }
}
