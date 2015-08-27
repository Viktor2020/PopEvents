package com.itstep.ppjava13v2.student.controller.validators;

import com.itstep.ppjava13v2.student.db.domain.Customer;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component("customerFormValidator")
public class CustomerFormValidator implements Validator {
	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class clazz) {
		return Customer.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object model, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerFirstName", "required.customerFirstName", "First Name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerLastName", "required.customerLastName", "Last Name  is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerStreetAddress", "required.customerStreetAddress", "Street Address is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerCity", "required.customerCity", "City is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerState", "required.customerState", "State is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerPhoneNumber", "required.customerPhoneNumber", "Phone Number is required.");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerDateHired", "required.customerDateHired", "Date Hired is required.");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerSalary", "required.customerSalary", "Salary is required.");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerCommissionRate", "required.customerCommissionRate", "Commission Rate is required.");
	}
}
