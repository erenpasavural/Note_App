package com.example.simplenoteapp.SignInActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.simplenoteapp.ForgotPassword.ForgotPasswordActivity;
import com.example.simplenoteapp.Homepage.HomepageActivity;
import com.example.simplenoteapp.R;
import com.example.simplenoteapp.SignUpActivity.SignUpActivity;
import com.example.simplenoteapp.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignInActivity extends AppCompatActivity {
    ActivitySignInBinding binding;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();



    }

    public void üye_ol_textview (View view) {

        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void sign_in_button (View view) {

        String giris_email = binding.signInEmail.getText().toString();
        String giris_parola = binding.signInPassword.getText().toString();

        if(giris_email.isEmpty() || giris_parola.isEmpty()) {

            Toast.makeText(this, "E-posta veya parola boş bırakılamaz", Toast.LENGTH_SHORT).show();

        } else {

            auth.signInWithEmailAndPassword(giris_email,giris_parola).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    Intent intent = new Intent(SignInActivity.this, HomepageActivity.class);
                    startActivity(intent);
                    finish();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(SignInActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }


    }

    public void forgot_password (View view) {

        Intent intent = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

}