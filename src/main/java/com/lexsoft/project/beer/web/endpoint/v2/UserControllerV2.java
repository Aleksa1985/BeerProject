package com.lexsoft.project.beer.web.endpoint.v2;

import com.lexsoft.project.beer.core.conversion.Converter;
import com.lexsoft.project.beer.core.validation.aspects.ValidateBody;
import com.lexsoft.project.beer.database.model.UserDb;
import com.lexsoft.project.beer.database.repository.impl.UserRepository;
import com.lexsoft.project.beer.web.dto.user.UserWeb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v2/users")
public class UserControllerV2 {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private Converter<UserWeb,UserDb> converter;

    @PostMapping("/record")
    @Transactional
    @ValidateBody
    public ResponseEntity signUpUser(@RequestBody UserWeb userWeb) {
        UserDb userDb = converter.convert(userWeb);
        userDb.setPassword(bCryptPasswordEncoder.encode(userDb.getPassword()));
        userRepository.create(userDb);
        return ResponseEntity.ok().build();
    }


}
