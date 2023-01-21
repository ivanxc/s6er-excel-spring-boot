package com.ivanxc.sber.excelnamessearch.exception;

import com.ivanxc.sber.excelnamessearch.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice(basePackages = "com.ivanxc.sber.excelnamessearch")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ParameterException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ParameterException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        return new ResponseEntity<>(new ErrorResponse("Incorrect request", details), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UncheckedIOException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(UncheckedIOException ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        return new ResponseEntity<>(new ErrorResponse("Internal error", details), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}