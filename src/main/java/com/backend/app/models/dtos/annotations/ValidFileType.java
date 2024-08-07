package com.backend.app.models.dtos.annotations;

import com.backend.app.config.validator.FileTypeValidator;
import com.backend.app.persistence.enums.EFileType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = FileTypeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFileType {
    String message() default "Invalid file type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    EFileType[] value();
}
