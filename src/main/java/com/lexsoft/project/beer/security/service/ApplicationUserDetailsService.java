package com.lexsoft.project.beer.security.service;

import com.lexsoft.project.beer.database.model.UserDb;
import com.lexsoft.project.beer.database.repository.impl.UserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    private UserRepository applicationUserRepository;

    public ApplicationUserDetailsService(UserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDb applicationUser = applicationUserRepository.findUserByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        GrantedAuthority a = new SimpleGrantedAuthority(applicationUser.getRole());
        return new User(applicationUser.getUsername(), applicationUser.getPassword(), Arrays.asList(a));
    }
}
