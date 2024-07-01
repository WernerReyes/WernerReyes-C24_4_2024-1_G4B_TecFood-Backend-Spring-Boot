package com.backend.app.utilities;

import com.backend.app.exceptions.CustomException;
import com.backend.app.exceptions.DtoException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

public class RequestValidatorUtility {
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    public static <T> DtoException<T> validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<T> violation : violations) {
                sb.append(violation.getMessage());
                break;
            }
            throw CustomException.badRequest(sb.toString());
            // return new DtoException<>(sb.toString(), null);
        }

        return new DtoException<>(null, object);
    }

}
