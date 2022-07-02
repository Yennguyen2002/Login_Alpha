package com.example.alpha_firebase.springbootfirebasedemo.registration;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString

public class RegistrationRequest {
    private final String firstname;
    private final String lastname;
    private final String password;
    private final String email;
}
