package com.itstep.ppjava13v2.student.controller.validators;

import com.itstep.ppjava13v2.student.db.domain.Entertainer;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component("entertainerFormValidator")
public class EntertainerFormValidator implements Validator {
	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class clazz) {
		return Entertainer.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object model, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "entertainerStageName", "required.entertainerStageName", "Stage Name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "entertainerStreetAddress", "required.entertainerStreetAddress", "Street Address is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "entertainerCity", "required.entertainerCity", "City is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "entertainerState", "required.entertainerState", "State is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "entertainerPhoneNumber", "required.entertainerPhoneNumber", "Phone Number is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "entertainerWebPage", "required.entertainerWebPage", "Web Page is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "entertainerEmailAddress", "required.entertainerEmailAddress", "Email Address is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "entertainerDateEntered", "required.entertainerDateEntered", "Date Entered is required.");
	}
}