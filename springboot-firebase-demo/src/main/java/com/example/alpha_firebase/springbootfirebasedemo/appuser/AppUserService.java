package com.example.alpha_firebase.springbootfirebasedemo.appuser;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private final StudentReposity studentReposity;
    private final static String USER_NOT_FOUND_MSG = "CANNOT FOUND EMAIL %s";

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return studentReposity.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }
}