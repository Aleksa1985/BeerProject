package com.lexsoft.project.beer.security.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lexsoft.project.beer.database.model.UserDb;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.lexsoft.project.beer.security.SecurityConstants.EXPIRATION_TIME;
import static com.lexsoft.project.beer.security.SecurityConstants.KEY;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/v2/users/login");

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            UserDb applicationUser = new ObjectMapper().readValue(req.getInputStream(), UserDb.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(applicationUser.getUsername(),
                            applicationUser.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        Date exp = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        Claims claims = Jwts.claims().setSubject(((User) auth.getPrincipal()).getUsername());
        String role = auth.getAuthorities().stream().findFirst().map(a -> a.getAuthority()).orElse(null);

        String token = Jwts.builder()
                .setClaims(claims)
                .claim("role",role)
                .signWith(SignatureAlgorithm.HS512, KEY)
                .setExpiration(exp)
                .compact();
        res.addHeader("token", token);

    }

}
