package com.example.uno;

import android.os.Bundle;
import android.util.Log;

import androidx.core.splashscreen.SplashScreen;

import com.example.uno.fragments.ForgotPasswordFragment;
import com.example.uno.fragments.RegisterFragment;
import com.example.uno.database.Firestore;
import com.example.uno.fragments.LoginFragment;
import com.example.uno.models.User;

public class MainActivity extends CommonActivity implements LoginFragment.ILogin, RegisterFragment.IRegister, ForgotPasswordFragment.IForgot {

    private Firestore db;

    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);

        setContentView(R.layout.activity_main);
    }

    public Firestore getDb() {
        if(this.db == null) this.db = new Firestore(this);
        return this.db;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}