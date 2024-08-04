package com.backend.app.config.validator;

import com.backend.app.models.dtos.annotations.ValidFileType;
import com.backend.app.persistence.enums.EFileType;
import jakarta.validation.ConstraintValidator;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class FileTypeValidator implements ConstraintValidator<ValidFileType, List<MultipartFile>>{

    private EFileType[] allowedFileTypes;
    @Override
    public void initialize(ValidFileType constraintAnnotation) {
        allowedFileTypes = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(List<MultipartFile> files, jakarta.validation.ConstraintValidatorContext constraintValidatorContext) {
        if (files == null || files.isEmpty()) return false;

        for (MultipartFile file : files) {
            if (file == null) return true;
            String contentType = file.getContentType();
            if (contentType == null || contentType.isEmpty()) return false;
            for (EFileType fileType : allowedFileTypes) {
                return Arrays.asList(fileType.getAllowedExtensions()).contains(contentType);
            }
        }
        return false;
    }




}
