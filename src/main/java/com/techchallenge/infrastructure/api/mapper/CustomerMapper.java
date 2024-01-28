package com.techchallenge.infrastructure.api.mapper;

import com.techchallenge.domain.entity.Customer;
import com.techchallenge.infrastructure.api.dtos.CustomerRequest;
import com.techchallenge.infrastructure.api.dtos.CustomerResponse;

import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

	public Customer toCustomer(CustomerRequest request) {
		return new Customer(request.cpf(), request.name(),request.email());
	}

	public CustomerResponse toCustomerResponse(Customer customer) {
		return new CustomerResponse(customer.getFormatCpf(), customer.getName(), customer.getEmail());
	}

}
