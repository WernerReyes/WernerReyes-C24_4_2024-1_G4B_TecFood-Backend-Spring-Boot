package com.backend.app.models.dtos.annotations;


import com.backend.app.config.validator.FutureOrPresentValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FutureOrPresentValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface FutureOrPresent {
    String message() default "The date must be in the future or present";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
