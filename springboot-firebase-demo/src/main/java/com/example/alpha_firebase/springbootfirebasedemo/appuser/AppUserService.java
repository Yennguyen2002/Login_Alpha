package com.example.alpha_firebase.springbootfirebasedemo.appuser;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private final static String USER_NOT_FOUND_MSG = "CANNOT FOUND EMAIL %s";
    static Logger logger = LoggerFactory.getLogger(AppUserService.class);
    private StudentReposity studentReposity = new StudentReposity();
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        AppUser user = studentReposity.findByEmail(email);
        logger.info(user.toString());
        return user;
    }
//54'56
    public String signUpUser(AppUser appUser){
        return "";
    }
}
