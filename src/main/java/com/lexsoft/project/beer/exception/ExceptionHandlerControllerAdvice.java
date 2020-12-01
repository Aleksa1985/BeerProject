package com.lexsoft.project.beer.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.lexsoft.project.beer.exception.model.ExceptionResponse;
import com.lexsoft.project.beer.exception.types.InternalWebException;

import com.lexsoft.project.beer.logging.filter.RequestCorrelation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {InternalWebException.class})
    protected ResponseEntity<Object> handleConflict(InternalWebException ex, WebRequest request) throws JsonProcessingException {

        ExceptionResponse response = new ExceptionResponse(new Date().getTime(), RequestCorrelation.getId(), ex.getErrors());
        return handleExceptionInternal(ex, response, new HttpHeaders(), ex.getHttpStatus(), request);

    }

}
