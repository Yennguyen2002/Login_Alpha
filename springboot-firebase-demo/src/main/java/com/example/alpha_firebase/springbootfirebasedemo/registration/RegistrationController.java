package com.example.alpha_firebase.springbootfirebasedemo.registration;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @RequestMapping(path = "/api/v1/registration", method = RequestMethod.GET)
    public String register(RegistrationRequest request){
        return "works";
    }
}
