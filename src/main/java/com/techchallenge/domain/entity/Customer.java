package com.techchallenge.domain.entity;

import com.techchallenge.domain.valueobject.Cpf;
import com.techchallenge.domain.valueobject.Email;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {

	private Cpf cpf;
	private String name;	
	private Email email;

	public Customer(String cpf, String name, String email) {
		super();
		this.cpf = new Cpf(cpf);
		this.name = name;	
		this.email = new Email(email);
	}

	public Cpf getCpf() {
		return cpf;
	}
	
	public Optional<String> getCpfValue() {
		return Optional.ofNullable(cpf).map(Cpf::getValue);
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return Optional.ofNullable(email).map(Email::getValue).orElse(null);
	}
	
	public String getFormatCpf() {
		Pattern pattern = Pattern.compile("(\\d{3})(\\d{3})(\\d{3})(\\d{2})");
		Matcher matcher = pattern.matcher(cpf.getValue());
		return  matcher.replaceAll("$1.$2.$3-$4");
	}

}
