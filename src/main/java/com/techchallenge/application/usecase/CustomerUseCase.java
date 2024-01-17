package com.techchallenge.application.usecase;

import com.techchallenge.domain.entity.Customer;

public interface CustomerUseCase {

	Customer insert(Customer customer);
	Customer update(Customer customer);
	Customer findByCpf(String cpf);
	void delete(String cpf);
}
