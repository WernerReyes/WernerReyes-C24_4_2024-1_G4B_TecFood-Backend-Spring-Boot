package com.backend.app.config.validator;

import com.backend.app.models.dtos.annotations.UniqueElements;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class UniqueElementsValidator implements ConstraintValidator<UniqueElements, List<?>> {
    @Override
    public void initialize(UniqueElements constraintAnnotation) {}

    @Override
    public boolean isValid(List<?> value, ConstraintValidatorContext context) {
        return value == null || value.stream().distinct().count() == value.size();
    }

}
