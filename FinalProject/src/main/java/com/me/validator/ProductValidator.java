package com.me.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.me.pojo.Product;

@Component
public class ProductValidator implements Validator {

	public boolean supports(Class aClass) {
		return aClass.equals(Product.class);
	}

	public void validate(Object obj, Errors errors) {
		Product newProduct = (Product) obj;

		// ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title",
		// "error.invalid.category", "Category Required");
	}
}