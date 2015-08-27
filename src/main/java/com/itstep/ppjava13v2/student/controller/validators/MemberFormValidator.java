package com.itstep.ppjava13v2.student.controller.validators;

import com.itstep.ppjava13v2.student.db.domain.Member;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**

 */
@Component("memberFormValidator")
public class MemberFormValidator implements Validator {
	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class clazz) {
		return Member.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object model, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "memberFirstName", "required.memberFirstName", "First Name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "memberLastName", "required.memberLastName", "Last Name  is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "memberPhoneNumber", "required.memberPhoneNumber", "Phone Number is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "memberGender", "required.memberGender", "Gender is required.");
	}
}