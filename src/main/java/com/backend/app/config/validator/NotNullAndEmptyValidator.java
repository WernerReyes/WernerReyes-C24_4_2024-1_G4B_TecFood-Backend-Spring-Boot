package com.backend.app.config.validator;

import com.backend.app.models.dtos.NotNullAndNotEmpty;

import javax.validation.ConstraintValidator;

public class NotNullAndEmptyValidator implements ConstraintValidator<NotNullAndNotEmpty, Object>{
    @Override
    public void initialize(NotNullAndNotEmpty constraintAnnotation) {}

    @Override
    public boolean isValid(Object value, javax.validation.ConstraintValidatorContext context) {
        return value != null && !value.toString().isEmpty();
    }
}
