package com.techchallenge.domain.valueobject;


public class Email {

	private String value;
	public Email(String value) {
		this.value = validate(value);
	}

	private String validate(String value) {
		if(value.contains("@")){
	    	return value;
	    }
	    throw new IllegalArgumentException("Invalid Email!");
	}

	public String getValue() {
		return value;
	}

}
