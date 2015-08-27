package com.itstep.ppjava13v2.student.controller.validators;

import com.itstep.ppjava13v2.student.db.domain.MusicalStyle;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**

 */

@Component("musicalStyleFormValidator")
public class MusicalStyleFormValidator implements Validator {
	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class clazz) {
		return MusicalStyle.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object model, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "musicalStyleName", "required.musicalStyleName", "Musical style name is required.");
	}
}
