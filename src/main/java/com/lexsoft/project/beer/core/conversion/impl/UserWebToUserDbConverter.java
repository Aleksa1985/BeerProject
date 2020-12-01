package com.lexsoft.project.beer.core.conversion.impl;

import com.lexsoft.project.beer.core.conversion.Converter;
import com.lexsoft.project.beer.database.model.UserDb;
import com.lexsoft.project.beer.web.dto.user.UserWeb;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserWebToUserDbConverter implements Converter<UserWeb, UserDb> {

    @Override
    public UserDb convert(UserWeb userWeb) {
        return UserDb.builder()
                .role(userWeb.getRole())
                .username(userWeb.getUsername())
                .password(userWeb.getPassword())
                .build();
    }

    @Override
    public List<UserDb> convertList(List<UserWeb> tList) {
        return null;
    }
}
