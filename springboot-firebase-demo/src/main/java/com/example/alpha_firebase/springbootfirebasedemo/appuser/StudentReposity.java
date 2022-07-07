package com.example.alpha_firebase.springbootfirebasedemo.appuser;


import com.example.alpha_firebase.springbootfirebasedemo.utils.FireBase;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.util.Optional;

public class StudentReposity{
//    Optional<AppUser>findByEmail(String email);
    private static Firestore db = FireBase.getDb();

    // Thay JPA bằng firebase
    public static Object findByEmail(String email){
        try{
            // Create a reference to the cities collection
            CollectionReference users = db.collection("users");
            // Create a query against the collection.
            Query query = users.whereEqualTo("email", email);
            // retrieve  query results asynchronously using query.get()
            ApiFuture<QuerySnapshot> querySnapshot = query.get();

            DocumentSnapshot document = querySnapshot.get().getDocuments().get(0);
            if (document.exists()){
                return document.getData();
            }
            return null;
        }catch (Exception error) {
            System.out.println(error.getMessage());
            return null;
        }
    }
}
