package ar.com.insonet.dao;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ar.com.insonet.model.InsonetUser;

public class InsonetUserValidator implements Validator {

	public boolean supports(@SuppressWarnings("rawtypes") Class insonetUser) {
		
		return InsonetUser.class.equals(insonetUser);
	}

	public void validate(Object obj, Errors e) {
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "name", "name.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "surname", "surname.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "username", "username.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "email", "email.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "password", "password.empty");
		
		/*InsonetUser iu = (InsonetUser) obj;
		if ( iu.getAge() < 0) {
			e.rejectValue("age", "negativevalue");
		} else if (p.getAge() > 110) {
			e.rejectValue("age", "too.darn.old");
		}*/

	}

}
