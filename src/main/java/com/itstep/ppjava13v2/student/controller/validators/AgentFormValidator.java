package com.itstep.ppjava13v2.student.controller.validators;

import com.itstep.ppjava13v2.student.db.domain.Agent;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component("agentFormValidator")
public class AgentFormValidator implements Validator {
	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class clazz) {
		return Agent.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object model, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "agentFirstName", "required.agentFirstName", "First Name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "agentLastName", "required.agentLastName", "Last Name  is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "agentStreetAddress", "required.agentStreetAddress", "Street Address is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "agentCity", "required.agentCity", "City is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "agentState", "required.agentState", "State is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "agentPhoneNumber", "required.agentPhoneNumber", "Phone Number is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "agentDateHired", "required.agentDateHired", "Date Hired is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "agentSalary", "required.agentSalary", "Salary is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "agentCommissionRate", "required.agentCommissionRate", "Commission Rate is required.");
	}

}
