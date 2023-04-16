package com.example.simplenoteapp.NoteAddActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.simplenoteapp.Homepage.HomepageActivity;
import com.example.simplenoteapp.R;
import com.example.simplenoteapp.databinding.ActivityNoteAddBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NoteAddActivity extends AppCompatActivity {
    ActivityNoteAddBinding binding;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

    }

    public void note_add_done_imageButton (View view) {

        String kayit_tag = binding.noteAddTag.getText().toString();
        String kayit_title = binding.noteAddTitle.getText().toString();
        String kayit_content = binding.noteAddContent.getText().toString();
        String note_id = UUID.randomUUID().toString();

        if(kayit_tag.isEmpty() ||kayit_title.isEmpty()) {

            Toast.makeText(this, "Etiket veya başlık boş bırakılamaz", Toast.LENGTH_SHORT).show();

        } else {

            Map<String,Object> saveData = new HashMap<>();
            saveData.put("tag",kayit_tag);
            saveData.put("title",kayit_title);
            saveData.put("content",kayit_content);
            saveData.put("date",FieldValue.serverTimestamp());
            saveData.put("user_id",auth.getUid());
            saveData.put("note_id",note_id);
            firebaseFirestore.collection("Notes").document(note_id).set(saveData).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    Intent intent = new Intent(NoteAddActivity.this, HomepageActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(NoteAddActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}