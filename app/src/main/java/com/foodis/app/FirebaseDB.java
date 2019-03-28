package com.foodis.app;

import com.foodis.app.data_models.Seller;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDB {

    private static DatabaseReference ref;
    private FirebaseDB(){}

    public static DatabaseReference getRef(){
        if (ref == null) {
            // Write a message to the database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            ref = database.getReference();
        }
        return ref;
    }

    public static DatabaseReference getSellersRef(){
        return getRef().child("Sellers");
    }

    public static void setSellerProfile(Seller seller){
//        getSellersRef().setValue();
    }




}
