package com.example.simplenoteapp.Model;

public class NoteModel {

    public String model_tag;
    public String model_title;
    public String model_content;
    public String model_note_id;

    public NoteModel(String model_tag, String model_title, String model_content, String model_note_id) {
        this.model_tag = model_tag;
        this.model_title = model_title;
        this.model_content = model_content;
        this.model_note_id = model_note_id;
    }
}
