package com.backend.app.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoException<T> {
    private String error;
    private T data;

    public DtoException(String error, T data) {
        this.error = error;
        this.data = data;
    }
}