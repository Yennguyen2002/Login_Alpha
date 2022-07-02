package com.example.alpha_firebase.springbootfirebasedemo.appuser;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface StudentReposity {
    Optional<AppUser>findByEmail(String email);
}
