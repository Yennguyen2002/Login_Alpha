package com.example.alpha_firebase.springbootfirebasedemo.appuser;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity



public class AppUser implements UserDetails  {
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private String id;
    private String name;
    private String phoneNumber;
    private String workAt;

    //public ArrayList<String> questionPatchs;
    //public ArrayList<String> exams;
    //public ArrayList<String> examinations;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private AppUserRole appUswUserRole;
    private Boolean locked;
    private Boolean enabled;



    public AppUser(String fullname, String phoneNumber, String workAt,
//                 ArrayList<String> questionPatchs, ArrayList<String> exams, ArrayList<String> examinations,
                   AppUserRole appUswUserRole, Boolean locked, Boolean enabled, String username, String password) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.workAt = workAt;
//        this.questionPatchs = questionPatchs;
//        this.exams = exams;
//        this.examinations = examinations;
        this.appUswUserRole = appUswUserRole;
        this.locked = locked;
        this.enabled = enabled;
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority  =
                new SimpleGrantedAuthority(appUswUserRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true ;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
