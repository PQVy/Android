package com.kemofo.speechnote;

public class Note {
    private int note_id;
    private String contentNote;
    private String dateCreate;

    public Note() {
    }

    public Note(String contentNote, String dateCreate) {
        this.contentNote = contentNote;
        this.dateCreate = dateCreate;
    }

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public String getContentNote() {
        return contentNote;
    }

    public void setContentNote(String contentNote) {
        this.contentNote = contentNote;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }
}
