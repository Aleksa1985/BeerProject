package com.lexsoft.project.beer.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserWeb {

    Long id;
    String username;
    String password;
    String role;

}
