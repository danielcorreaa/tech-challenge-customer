package com.techchallenge.domain.valueobject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Email {

	private String value;
	private static final String REGEX = "^(.+)@(.+)$";
	public Email(String value) {
		this.value = validate(value);
	}

	private String validate(String value) {
	    if(Pattern.compile(REGEX).matcher(value).matches()) {
	    	return value;
	    }
	    throw new IllegalArgumentException("Invalid Email!");
	}

	public String getValue() {
		return value;
	}

}
