package com.example.alpha_firebase.springbootfirebasedemo.registration;

import com.example.alpha_firebase.springbootfirebasedemo.appuser.AppUser;
import com.example.alpha_firebase.springbootfirebasedemo.appuser.AppUserRole;
import com.example.alpha_firebase.springbootfirebasedemo.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final AppUserService appUserService;
    private EmailValidation emailValidation;
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidation.test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalArgumentException("Email invalid");
        }
        return appUserService.signUpUser(
                new AppUser(
                        request.getFirstname(),
                        request.getLastname(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
    }
}
