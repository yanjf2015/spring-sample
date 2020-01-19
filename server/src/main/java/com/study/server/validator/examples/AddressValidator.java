package com.study.server.validator.examples;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author jingfeng.yan
 */
public class AddressValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Address.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "province", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "district", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "town", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "detail", "field.required");
        Address address = (Address) target;
        if (address.getDetail().length() < 20) {
            errors.rejectValue("detail", "too short");
        }
    }
}
