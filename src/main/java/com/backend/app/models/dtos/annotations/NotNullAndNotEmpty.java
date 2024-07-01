package com.backend.app.models.dtos.annotations;

import com.backend.app.config.validator.NotNullAndEmptyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.*;

@NotNull(message = "The field is required")
@NotEmpty(message = "The field is required")
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotNullAndEmptyValidator.class)
@Documented
public @interface NotNullAndNotEmpty {
    String message() default "The field is required";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}