package com.capgemini.controller;

import com.capgemini.dto.BasicResponse;
import com.capgemini.exception.LicenseAppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * APIExceptionController
 *
 * contains the exception handling logic for the API
 */
@ControllerAdvice
public class APIExceptionController {

    @ExceptionHandler(value = LicenseAppException.class)
    public ResponseEntity<BasicResponse> exception(LicenseAppException exception) {
        return new ResponseEntity<>(
                new BasicResponse(false, exception.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
