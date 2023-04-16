package com.example.simplenoteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.simplenoteapp.Homepage.HomepageActivity;
import com.example.simplenoteapp.SignInActivity.SignInActivity;
import com.example.simplenoteapp.SignUpActivity.SignUpActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Handler handler;
        FirebaseAuth auth;
        FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (user != null) {

                    Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();

                }


            }
        },1000);
    }
}