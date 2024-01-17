package com.techchallenge.infrastructure.gateway;

import com.techchallenge.application.gateway.CustomerGateway;
import com.techchallenge.core.exceptions.BusinessException;
import com.techchallenge.domain.entity.Customer;
import com.techchallenge.infrastructure.persistence.entity.CustomerEntity;
import com.techchallenge.infrastructure.persistence.mapper.CustomerEntityMapper;
import com.techchallenge.infrastructure.persistence.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.lang.Boolean.FALSE;

@Component
public class CustomerRepositoryGateway implements CustomerGateway {

	private CustomerRepository repository;

	private CustomerEntityMapper mapper;

	public CustomerRepositoryGateway(CustomerRepository repository, CustomerEntityMapper mapper) {
		super();
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public Customer insert(Customer customer) {
		CustomerEntity entity = repository.save(mapper.toCustomerEntity(customer));
		return mapper.toCustomer(entity);
	}

	@Override
	public Customer update(Customer customer) {
		if(FALSE.
				equals(existsById(customer.getCpfValue().orElse("")))){
			throw new BusinessException("Customer not found for update");
		}
		return insert(customer);
	}

	@Override
	public Optional<Customer> findByCpf(String cpf) {
		var customerEntity = repository.findByCpf(cpf);
		return customerEntity.map( customer -> mapper.toCustomer(customer));
	}

	@Override
	public void delete(Customer customer) {
		repository.delete(mapper.toCustomerEntity(customer));
	}

	@Override
	public Boolean existsById(String cpf) {
		return repository.existsById(cpf);
	}


}
