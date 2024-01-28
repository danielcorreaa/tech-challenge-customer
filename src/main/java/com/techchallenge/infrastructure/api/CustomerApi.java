package com.techchallenge.infrastructure.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.techchallenge.application.usecase.CustomerUseCase;
import com.techchallenge.core.response.Result;
import com.techchallenge.domain.entity.Customer;
import com.techchallenge.infrastructure.api.dtos.CustomerRequest;
import com.techchallenge.infrastructure.api.dtos.CustomerResponse;
import com.techchallenge.infrastructure.api.mapper.CustomerMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerApi {

	private CustomerUseCase custumerUseCase;
	private CustomerMapper mapper;

	public CustomerApi(CustomerUseCase custumerUseCase, CustomerMapper mapper) {
		super();
		this.custumerUseCase = custumerUseCase;
		this.mapper = mapper;
	}

	@PostMapping
	public ResponseEntity<Result<CustomerResponse>> insert(@RequestBody @Valid CustomerRequest request,
														   UriComponentsBuilder uri) {
		Customer custumer = custumerUseCase.insert(mapper.toCustomer(request));
		UriComponents uriComponents = uri.path("/api/v1/custumers/find/{cpf}")
				.buildAndExpand(custumer.getCpfValue().orElse(""));
		return ResponseEntity.created(uriComponents.toUri()).body(Result.create(mapper.toCustomerResponse(custumer)));
	}

	@PutMapping
	public ResponseEntity<Result<CustomerResponse>> update(@RequestBody @Valid CustomerRequest request) {
		Customer custumer = custumerUseCase.update(mapper.toCustomer(request));
		return ResponseEntity.ok(Result.ok(mapper.toCustomerResponse(custumer)));
	}

	@DeleteMapping("/delete/{cpf}")
	public ResponseEntity<Result<String>> delete(@PathVariable String cpf) {
		custumerUseCase.delete(cpf);
		return ResponseEntity.ok(Result.ok("Custumer delete with success!"));
	}

	@GetMapping("/find/{cpf}")
	public ResponseEntity<Result<CustomerResponse>> find(@PathVariable String cpf) {
		Customer customer = custumerUseCase.findByCpf(cpf);
		return ResponseEntity.ok(Result.ok(mapper.toCustomerResponse(customer)));
	}

}
