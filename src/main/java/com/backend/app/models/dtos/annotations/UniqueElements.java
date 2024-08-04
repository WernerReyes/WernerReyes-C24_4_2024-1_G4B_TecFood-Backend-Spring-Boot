package com.backend.app.models.dtos.annotations;


import com.backend.app.config.validator.UniqueElementsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueElementsValidator.class)
@Documented
public @interface UniqueElements {
    String message() default "The elements must be unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
