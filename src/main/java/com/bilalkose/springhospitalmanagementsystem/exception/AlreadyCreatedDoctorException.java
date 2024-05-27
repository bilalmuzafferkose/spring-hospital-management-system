package com.bilalkose.springhospitalmanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AlreadyCreatedDoctorException extends RuntimeException {
    public AlreadyCreatedDoctorException(String message) {
        super(message);
    }
}
