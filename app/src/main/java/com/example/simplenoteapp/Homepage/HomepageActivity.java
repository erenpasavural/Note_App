package com.example.simplenoteapp.Homepage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.simplenoteapp.Adapter.NoteAdapter;
import com.example.simplenoteapp.MainActivity;
import com.example.simplenoteapp.Model.NoteModel;
import com.example.simplenoteapp.NoteAddActivity.NoteAddActivity;
import com.example.simplenoteapp.R;
import com.example.simplenoteapp.SignInActivity.SignInActivity;
import com.example.simplenoteapp.databinding.ActivityHomepageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class HomepageActivity extends AppCompatActivity {
    ActivityHomepageBinding binding;
    NoteAdapter noteAdapter;
    ArrayList<NoteModel> homepageArraylist;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        homepageArraylist = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        binding.homepageRecyclerview.setLayoutManager(linearLayoutManager);
        noteAdapter = new NoteAdapter(homepageArraylist,this);
        binding.homepageRecyclerview.setAdapter(noteAdapter);

        getData();

        binding.homepageSearchdata.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;

            }
        });



    }

    public void fab (View view) {

        Intent intent = new Intent(HomepageActivity.this, NoteAddActivity.class);
        startActivity(intent);
    }
    public void getData(){
        firebaseFirestore.collection("Notes").whereEqualTo("user_id",auth.getUid())
                .orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if(value != null){

                    for(DocumentSnapshot snapshot : value.getDocuments()){

                        Map<String,Object> getData = snapshot.getData();

                        String get_tag = (String) getData.get("tag");
                        String get_title = (String) getData.get("title");
                        String get_content_ = (String) getData.get("content");
                        String get_note_id = (String) getData.get("note_id");

                        NoteModel model = new NoteModel(get_tag,get_title,get_content_,get_note_id);
                        homepageArraylist.add(model);

                    }

                    noteAdapter.notifyDataSetChanged();
                }

            }
        });
    }
    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<NoteModel> filteredlist = new ArrayList<NoteModel>();

        // running a for loop to compare elements.
        for (NoteModel item : homepageArraylist) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.model_tag.toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            noteAdapter.filterList(filteredlist);
        }


    }
    public void logout (View view) {

        auth.signOut();

        Intent intent = new Intent(HomepageActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();

    }
}