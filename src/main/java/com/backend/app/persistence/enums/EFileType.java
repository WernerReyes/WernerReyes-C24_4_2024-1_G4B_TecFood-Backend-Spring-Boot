package com.backend.app.persistence.enums;

import lombok.Getter;

@Getter
public enum EFileType {
    IMAGE(new String[]{"image/png", "image/jpeg", "image/jpg"}),
    DOCUMENT(new String[]{"application/pdf", "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"});

    private final String[] allowedExtensions;

    EFileType(String[] allowedExtensions) {
        this.allowedExtensions = allowedExtensions;
    }

}
