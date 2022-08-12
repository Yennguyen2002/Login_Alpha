package com.example.alpha_firebase.springbootfirebasedemo.appuser;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
@Service
public class AppUserService implements UserDetailsService {
    private final static String USER_NOT_FOUND_MSG = "CANNOT FOUND EMAIL %s";
    static Logger logger = LoggerFactory.getLogger(AppUserService.class);
    private final StudentRepository studentRepository;
    @Autowired
    public AppUserService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        AppUser user = studentRepository.findByEmail(email);
        logger.info(user.toString());
        return user;
    }
    public String signUpUser(AppUser appUser){
        return "";
    }
}
