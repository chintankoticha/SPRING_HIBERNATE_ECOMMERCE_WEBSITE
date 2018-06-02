package com.me.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.me.pojo.Seller;

public class SellerValidator implements Validator {
	
	public boolean supports(Class aClass) {
		return aClass.equals(Seller.class);
	}

	public void validate(Object obj, Errors errors) {
		Seller seller = (Seller) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.invalid.user", "Field Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.invalid.user", "User Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.invalid.password", "Password Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactnumber", "error.invalid.user", "Contact Number Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress", "error.invalid.emailAddress",	"Email Required");
	}
		
}
