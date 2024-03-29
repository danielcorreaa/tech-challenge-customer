package com.techchallenge.application.usecase.interactor;
import com.techchallenge.application.gateway.CustomerGateway;
import com.techchallenge.application.usecase.CustomerUseCase;
import com.techchallenge.domain.entity.Customer;
import com.techchallenge.core.exceptions.NotFoundException;

public class CustomerUseCaseInteractor implements CustomerUseCase {

	private final CustomerGateway customerGateway;

	public CustomerUseCaseInteractor(CustomerGateway customerGateway) {
		this.customerGateway = customerGateway;
	}

	public Customer insert(Customer custumer) {
		return customerGateway.insert(custumer);
	}

	public Customer update(Customer customer) {
		return customerGateway.update(customer);
	}

	public Customer findByCpf(String cpf) {
		return customerGateway.findByCpf(cpf).orElseThrow(() -> new NotFoundException("Customer not found!"));
	}

	public void delete(String cpf) {
		Customer findByCpf = findByCpf(cpf);
		customerGateway.delete(findByCpf);
	}

}
