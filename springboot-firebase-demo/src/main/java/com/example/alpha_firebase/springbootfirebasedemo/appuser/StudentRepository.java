package com.example.alpha_firebase.springbootfirebasedemo.appuser;


import com.example.alpha_firebase.springbootfirebasedemo.utils.FireBase;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class StudentRepository{
    static Logger logger = LoggerFactory.getLogger(StudentRepository.class);
    private final FireBase fireBase;
    private final Firestore db;
    @Autowired
    public StudentRepository(FireBase fireBase){
        this.fireBase = fireBase;
        this.db = fireBase.getDb();
    }
    public AppUser findByEmail(String email){
        try{
            // Create a reference to the cities collection
            CollectionReference users = db.collection("users");
            // Create a query against the collection.
            Query query = users.whereEqualTo("userName", email);
            // retrieve  query results asynchronously using query.get()
            ApiFuture<QuerySnapshot> querySnapshot = query.get();

            DocumentSnapshot document = querySnapshot.get().getDocuments().get(0);
            if (document.exists()){
                Map<String, Object> data = document.getData();
                return new AppUser(document.getId(),"Tuan", "", "", AppUserRole.USER,(boolean)data.get("locked"),
                        (boolean)data.get("enable"), (String) data.get("userName"),(String)data.get("password"));
            }
            logger.warn("wrong account");
            return new AppUser();
        }catch (Exception error) {
            System.out.println(error.getMessage());
            return null;
        }
    }
}
