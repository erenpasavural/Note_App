package com.example.simplenoteapp.SignUpActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.simplenoteapp.Homepage.HomepageActivity;
import com.example.simplenoteapp.R;
import com.example.simplenoteapp.SignInActivity.SignInActivity;
import com.example.simplenoteapp.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();




    }

    public void sign_up_back_imageButton (View view) {

        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(intent);
    }

    public void giris_yap_textview (View view) {

        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(intent);
    }

    public void sign_up_button (View view) {

        String kayit_ad = binding.signUpName.getText().toString();
        String kayit_soyad = binding.signUpSurname.getText().toString();
        String kayit_cep_telefonu = binding.signUpPhoneNumber.getText().toString();
        String kayit_email = binding.signUpEmail.getText().toString();
        String kayit_password = binding.signUpPassword.getText().toString();

        if(kayit_ad.isEmpty() || kayit_soyad.isEmpty() ||kayit_cep_telefonu.isEmpty() || kayit_email.isEmpty() ||kayit_password.isEmpty()){

            Toast.makeText(this, "Kayıt için boş alan bırakmayınız", Toast.LENGTH_SHORT).show();

        } else {

            auth.createUserWithEmailAndPassword(kayit_email,kayit_password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    Intent intent = new Intent(SignUpActivity.this, HomepageActivity.class);
                    startActivity(intent);
                    finish();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(SignUpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            });


        }
    }

}