package com.example.alpha_firebase.springbootfirebasedemo;

import com.example.alpha_firebase.springbootfirebasedemo.utils.FireBase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootFirebaseDemoApplication {

	public static void main(String[] args) {
		FireBase.initFireBase();
		SpringApplication.run(SpringbootFirebaseDemoApplication.class, args);
	}

}
