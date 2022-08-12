package com.example.alpha_firebase.springbootfirebasedemo;

import com.example.alpha_firebase.springbootfirebasedemo.utils.FireBase;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class SpringbootFirebaseDemoApplication {
    static Logger logger = LoggerFactory.getLogger(SpringbootFirebaseDemoApplication.class);

    @Autowired
    private FireBase fireBase;

	public static void main(String[] args) {
        logger.info("Firebase");
		SpringApplication.run(SpringbootFirebaseDemoApplication.class, args);
	}

}
