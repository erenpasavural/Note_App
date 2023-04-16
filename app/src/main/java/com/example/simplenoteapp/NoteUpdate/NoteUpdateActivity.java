package com.example.simplenoteapp.NoteUpdate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.simplenoteapp.Homepage.HomepageActivity;
import com.example.simplenoteapp.R;
import com.example.simplenoteapp.databinding.ActivityNoteUpdateBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NoteUpdateActivity extends AppCompatActivity {

    ActivityNoteUpdateBinding binding;
    FirebaseFirestore firebaseFirestore;
    String note_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseFirestore = FirebaseFirestore.getInstance();


        Intent intent = getIntent();
        note_id = intent.getStringExtra("note_id");

        firebaseFirestore = FirebaseFirestore.getInstance();

        String update_tag = getIntent().getStringExtra("tag");
        String update_title = getIntent().getStringExtra("title");
        String update_content = getIntent().getStringExtra("content");

        binding.noteUpdateTag.setText(update_tag);
        binding.noteUpdateTitle.setText(update_title);
        binding.noteUpdateContent.setText(update_content);


    }

    public void note_update_done_imageButton (View view){

        String guncellenen_tag = binding.noteUpdateTag.getText().toString();
        String guncellenen_title = binding.noteUpdateTitle.getText().toString();
        String guncellenen_content = binding.noteUpdateContent.getText().toString();

        Map<String,Object> guncellenenVeri = new HashMap<>();
        guncellenenVeri.put("tag",guncellenen_tag);
        guncellenenVeri.put("title",guncellenen_title);
        guncellenenVeri.put("content",guncellenen_content);

        firebaseFirestore.collection("Notes").document(note_id).update(guncellenenVeri).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Intent intent = new Intent(NoteUpdateActivity.this, HomepageActivity.class);
                startActivity(intent);
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(NoteUpdateActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void note_update_delete_imageButton (View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(NoteUpdateActivity.this);
        builder.setTitle("Warning");
        builder.setMessage("Are you sure you want to delete the note?");
        builder.setIcon(R.drawable.warning);

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                firebaseFirestore.collection("Notes").document(note_id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override

                    public void onSuccess(Void unused) {

                        Intent intent = new Intent(NoteUpdateActivity.this,HomepageActivity.class);
                        finish();
                        startActivity(intent);

                    }
                });

            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });

        builder.create().show();

    }


}