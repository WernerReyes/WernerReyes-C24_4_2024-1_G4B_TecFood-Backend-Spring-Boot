package com.backend.app.models.dtos;

import com.backend.app.config.validator.NotNullAndEmptyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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