package com.techchallenge.application.gateway;

import com.techchallenge.domain.entity.Customer;

import java.util.Optional;

public interface CustomerGateway {
	
	Customer insert(final Customer custumer);
	Customer update(final Customer custumer);
	Optional<Customer> findByCpf(final String cpf);
	void delete(final Customer customer);
	Boolean existsById(final String cpf);


}
