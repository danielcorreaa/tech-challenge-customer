package com.techchallenge.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "cpf")
@Entity
@Table(name = "customer")
public class CustomerEntity {
	
	@Id
	@Column(name = "cpf", nullable = false)		
	private String cpf;
	private String nome;
	private String email;


}
