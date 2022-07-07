package com.example.alpha_firebase.springbootfirebasedemo.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface StudentReposity
         extends JpaRepository<AppUser, String> {
    Optional<AppUser>findByEmail(String email);
}
