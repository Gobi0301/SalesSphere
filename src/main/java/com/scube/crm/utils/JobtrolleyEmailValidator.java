package com.scube.crm.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class JobtrolleyEmailValidator implements Validator {
	static String status;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Override
	public boolean supports(Class<?> arg0) {
		
		return false;
	}

	public static String validate(Object arg0) {
		final String emailAddress = (String) arg0;
		final Pattern p2 = Pattern
				.compile(JobtrolleyEmailValidator.EMAIL_PATTERN);
		final Matcher m1 = p2.matcher(emailAddress);
		final boolean matchFound1 = m1.matches();
		if (!matchFound1) {
			JobtrolleyEmailValidator.status = "Enter valid email ID";
		} else {
			JobtrolleyEmailValidator.status = null;
		}
		return JobtrolleyEmailValidator.status;

	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		

	}

}
