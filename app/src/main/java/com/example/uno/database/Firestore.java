package com.example.uno.database;

import android.util.Log;

import com.example.uno.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Firestore {

    public static final String DB_PROFILE = "profiles";

    MainActivity activity;

    public FirebaseFirestore firestore;

    public StorageReference storage;

    public FirebaseAuth mAuth;

    public Firestore(MainActivity activity) {
        this.activity = activity;
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance().getReference();
    }

    public FirebaseUser getCurrentUser(){
        return mAuth.getCurrentUser();
    }

}
