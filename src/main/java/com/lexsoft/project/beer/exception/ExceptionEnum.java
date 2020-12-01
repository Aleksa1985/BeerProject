package com.lexsoft.project.beer.exception;

import lombok.Getter;

import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionEnum {

    MANDATORY_PARAM(1000, "Param %s is mandatory", HttpStatus.BAD_REQUEST),
    OBJECT_DOES_NOT_EXIST(1001, "Object %s identified by %s does not exist.", HttpStatus.BAD_REQUEST),
    EXTERNAL_CLIENT_ERROR(2000, "%s", HttpStatus.BAD_GATEWAY),
    FORBIDDEN(5000,"Forbiden for this user", HttpStatus.FORBIDDEN);

    private Integer code;
    private String message;
    private HttpStatus statusCode;

    ExceptionEnum(Integer code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

}
