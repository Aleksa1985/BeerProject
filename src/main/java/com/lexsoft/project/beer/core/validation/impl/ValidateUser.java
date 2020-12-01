package com.lexsoft.project.beer.core.validation.impl;

import com.lexsoft.project.beer.core.validation.AbstractValidator;
import com.lexsoft.project.beer.core.validation.Validate;
import com.lexsoft.project.beer.exception.model.ErrorMessage;
import com.lexsoft.project.beer.exception.types.InternalWebException;
import com.lexsoft.project.beer.web.dto.user.UserWeb;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ValidateUser extends AbstractValidator implements Validate<UserWeb> {

    @Override
    public void validate(UserWeb userWeb) {
        List<ErrorMessage> errorMessageList = new ArrayList<>();
        validateMandatory("username",userWeb.getUsername(),errorMessageList);
        validateMandatory("password",userWeb.getPassword(),errorMessageList);
        validateMandatory("role",userWeb.getRole(),errorMessageList);
        if(!errorMessageList.isEmpty()){
            throw new InternalWebException(HttpStatus.BAD_REQUEST,errorMessageList);
        }
    }
}
