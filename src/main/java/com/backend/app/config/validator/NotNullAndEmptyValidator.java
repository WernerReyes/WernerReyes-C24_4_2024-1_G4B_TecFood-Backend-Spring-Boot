package com.backend.app.config.validator;

import com.backend.app.models.dtos.annotations.NotNullAndNotEmpty;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotNullAndEmptyValidator implements ConstraintValidator<NotNullAndNotEmpty, Object> {
    @Override
    public void initialize(NotNullAndNotEmpty constraintAnnotation) {}

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return value != null && !value.toString().isEmpty();
    }
}
