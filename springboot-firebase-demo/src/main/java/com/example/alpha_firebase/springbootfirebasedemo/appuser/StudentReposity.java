package com.example.alpha_firebase.springbootfirebasedemo.appuser;


import com.example.alpha_firebase.springbootfirebasedemo.utils.FireBase;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StudentReposity{
//    Optional<AppUser>findByEmail(String email);
    private static Firestore db = FireBase.getDb();
    static Logger logger = LoggerFactory.getLogger(StudentReposity.class);
    // Thay JPA bằng firebase
    public AppUser findByEmail(String email){
        try{
            logger.info("Begin interact with database");
            // Create a reference to the cities collection
            CollectionReference users = db.collection("users");
            // Create a query against the collection.
            Query query = users.whereEqualTo("userName", email);
            // retrieve  query results asynchronously using query.get()
            ApiFuture<QuerySnapshot> querySnapshot = query.get();

            DocumentSnapshot document = querySnapshot.get().getDocuments().get(0);
            if (document.exists()){
                Map<String, Object> data = document.getData();
                AppUser user = new AppUser(document.getId(),"Tuan", "", "", AppUserRole.USER,(boolean)data.get("locked"),
                        (boolean)data.get("enable"), (String) data.get("userName"),(String)data.get("password"));
//                AppUser user = new AppUser(document.getId(),"Tuan", "", "", AppUserRole.USER,(boolean)data.get("locked"),
//                        (boolean)data.get("enable"), (String) data.get("userName"),(String)data.get("password"));
                return user;
            }
            logger.warn("wrong account");
            return new AppUser();
        }catch (Exception error) {
            System.out.println(error.getMessage());
            return new AppUser();
        }
    }
}
