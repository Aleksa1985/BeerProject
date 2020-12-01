package com.lexsoft.project.beer.exception.types;

import lombok.Data;

import com.lexsoft.project.beer.exception.model.ErrorMessage;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class InternalWebException extends RuntimeException {

    List<ErrorMessage> errors;
    HttpStatus httpStatus;

    public InternalWebException(HttpStatus httpStatus, List<ErrorMessage> errors) {
        super();
        this.errors = errors;
        this.httpStatus = httpStatus;
    }
}
