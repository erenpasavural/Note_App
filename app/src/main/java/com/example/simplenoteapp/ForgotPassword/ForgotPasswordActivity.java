package com.example.simplenoteapp.ForgotPassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.simplenoteapp.R;
import com.example.simplenoteapp.SignInActivity.SignInActivity;
import com.example.simplenoteapp.databinding.ActivityForgotPasswordBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    ActivityForgotPasswordBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();



        binding.btnGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = binding.emailBox.getText().toString().trim();

                if(email.isEmpty()) {

                    Toast.makeText(ForgotPasswordActivity.this, "Lütfen e-posta adresinizi girin", Toast.LENGTH_SHORT).show();

                }

               else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    Toast.makeText(ForgotPasswordActivity.this, "Geçersiz e-posta adresi", Toast.LENGTH_SHORT).show();

                } else {

                   auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void unused) {

                           Toast.makeText(ForgotPasswordActivity.this, "E-posta adresinizi kontrol edin", Toast.LENGTH_SHORT).show();

                               Intent intent = new Intent(ForgotPasswordActivity.this, SignInActivity.class);
                               startActivity(intent);
                               finish();


                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {

                           Toast.makeText(ForgotPasswordActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                       }
                   });

                }



            }
        });

        binding.forgotPasswordBackImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ForgotPasswordActivity.this,SignInActivity.class);
                startActivity(intent);

            }
        });

    }

}