package com.example.simplenoteapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplenoteapp.Model.NoteModel;
import com.example.simplenoteapp.NoteUpdate.NoteUpdateActivity;
import com.example.simplenoteapp.R;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.Holder>{

    ArrayList<NoteModel> noteModelArrayList;
    Context mcontext;

    public void filterList(ArrayList<NoteModel> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        noteModelArrayList = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    public NoteAdapter(ArrayList<NoteModel> noteModelArrayList, Context mcontext) {
        this.noteModelArrayList = noteModelArrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_note_shape,parent,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {

        holder.recyclerview_note_tag.setText(noteModelArrayList.get(position).model_tag);
        holder.recyclerview_note_title.setText(noteModelArrayList.get(position).model_title);
        holder.recyclerview_note_content.setText(noteModelArrayList.get(position).model_content);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mcontext, NoteUpdateActivity.class);
                intent.putExtra("tag", noteModelArrayList.get(position).model_tag);
                intent.putExtra("title",noteModelArrayList.get(position).model_title);
                intent.putExtra("content", noteModelArrayList.get(position).model_content);
                intent.putExtra("note_id",noteModelArrayList.get(position).model_note_id);
                mcontext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {

        return noteModelArrayList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView recyclerview_note_tag;
        TextView recyclerview_note_title;
        TextView recyclerview_note_content;

        public Holder(@NonNull View itemView) {
            super(itemView);

            recyclerview_note_tag = itemView.findViewById(R.id.recyclerview_note_tag);
            recyclerview_note_title = itemView.findViewById(R.id.recyclerview_note_title);
            recyclerview_note_content = itemView.findViewById(R.id.recyclerview_note_content);
        }
    }

}
