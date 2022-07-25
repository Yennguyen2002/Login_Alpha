package com.example.alpha_firebase.springbootfirebasedemo.appuser;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class AppUser implements UserDetails  {
//    @Id
//    @SequenceGenerator(
//            name = "student_sequence",
//            sequenceName = "student_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "student_sequence"
//    )
    private String id;

// Thay vì sử dụng JPA thì firebase tự động generata id, tương tự những dòng trên

    private String firstName;
    private String phoneNumber;
    private String workAt;

    //public ArrayList<String> questionPatchs;
    //public ArrayList<String> exams;
    //public ArrayList<String> examinations;

    private String lastName;
    private String password;

//    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
    private Boolean locked;
    private String email;
//    private Boolean enabled;



    public AppUser(String id, String firstName, String phoneNumber, String workAt,
                   AppUserRole appUserRole, boolean locked, boolean enable, String lastName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.workAt = workAt;
        //@Enumerated(EnumType.STRING) error !
        this.appUserRole = appUserRole;
        this.email = email;
        this.password = password;
    }

    public AppUser(String firstname, String lastname, String email, String password, AppUserRole user) {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority  =
                new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername() {
        return email;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
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
        return false;
    }


}
