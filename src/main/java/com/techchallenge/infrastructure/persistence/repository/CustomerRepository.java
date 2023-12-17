package com.techchallenge.infrastructure.persistence.repository;

import com.techchallenge.infrastructure.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {

	Optional<CustomerEntity> findByCpf(String cpf);

}
