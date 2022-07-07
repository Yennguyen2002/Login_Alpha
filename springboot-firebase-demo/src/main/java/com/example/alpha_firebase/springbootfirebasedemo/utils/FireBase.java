package com.example.alpha_firebase.springbootfirebasedemo.utils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;


import java.io.FileInputStream;
import java.io.InputStream;


public class FireBase {
    private static FirebaseApp firebaseApp;
    private static Firestore db;

    public static Firestore getDb() {
        return db;
    }

    public static void initFireBase(){
        try{
            InputStream serviceAccount =
                    new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com" +
                            "/example/alpha_firebase/springbootfirebasedemo/utils/alpha-service-account.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();
            firebaseApp = FirebaseApp.initializeApp(options);
        }catch (Exception error){
            System.out.println("failed to connect to firebase");
            System.out.println(error.getMessage());
        }
        db = FirestoreClient.getFirestore(firebaseApp);
    }
}
