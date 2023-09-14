package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class SupplierAlreadyExistsException extends RuntimeException {

    public SupplierAlreadyExistsException(String message) {
        super(message);
    }

}