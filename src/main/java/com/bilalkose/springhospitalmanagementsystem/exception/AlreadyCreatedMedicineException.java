package com.bilalkose.springhospitalmanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AlreadyCreatedMedicineException extends RuntimeException {
    public AlreadyCreatedMedicineException(String message) {
        super(message);
    }
}
