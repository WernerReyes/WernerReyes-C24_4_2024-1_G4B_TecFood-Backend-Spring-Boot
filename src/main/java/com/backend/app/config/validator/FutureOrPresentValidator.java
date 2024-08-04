package com.backend.app.config.validator;

import com.backend.app.models.dtos.annotations.FutureOrPresent;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class FutureOrPresentValidator implements ConstraintValidator<FutureOrPresent, LocalDateTime> {

    @Override
    public void initialize(FutureOrPresent constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return !value.isBefore(LocalDateTime.now());
    }
}
