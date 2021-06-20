package com.expedia.fitflights.controllers;

import com.expedia.fitflights.exceptions.ErrorMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@RestControllerAdvice
@Log4j2
public class AdviceController {

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage badRequest(Exception ex, WebRequest request){
        log.error(ex.getMessage());
        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
    }
}
