package com.example.alpha_firebase.springbootfirebasedemo.utils;

import com.example.alpha_firebase.springbootfirebasedemo.question.QuestionController;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

@Service
public class FireBase {
    static Logger logger = LoggerFactory.getLogger(FireBase.class);
    private FirebaseApp firebaseApp;
    private Firestore db;
    public Firestore getDb() {
        return db;
    }
    private final String firebaseCredentials;
    private void init(){
        try{
            InputStream credentialsStream = new ByteArrayInputStream(firebaseCredentials.getBytes());
//            InputStream serviceAccount =
//                    new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com" +
//                            "/example/alpha_firebase/springbootfirebasedemo/utils/alpha-service-account.json");
//            InputStream credentialsStream = new ByteArrayInputStream(firebaseCredentials.getBytes());
            GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();
            firebaseApp = FirebaseApp.initializeApp(options);
        }catch (Exception error){
            System.out.println("failed to connect to firebase");
            System.out.println(error.getMessage());
        }
    }

    public FireBase(@Value("${custom.firebase.credentials}") String firebaseCredentials){
        this.firebaseCredentials = firebaseCredentials;
        init();
        db = FirestoreClient.getFirestore(firebaseApp);
    }
}
