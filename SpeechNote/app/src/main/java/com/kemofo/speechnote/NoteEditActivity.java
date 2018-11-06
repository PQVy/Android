package com.kemofo.speechnote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NoteEditActivity extends AppCompatActivity {

    EditText note_edit_edNoteContent;
    EditText note_edit_edNoteDayCreate;
    Button note_edit_btnDelete;
    Button note_edit_btnUpdate;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        connectView();
        db = new DBHelper(this);

        Intent intent = getIntent();
        final int noteId = intent.getIntExtra("NOTEID", 1);
        String noteContent = intent.getStringExtra("NOTECONTENT");
        String noteDayCreate = intent.getStringExtra("NOTEDATE");


        note_edit_edNoteContent.setText(noteContent);
        note_edit_edNoteDayCreate.setText(noteDayCreate);

        final Intent intent1 = new Intent(NoteEditActivity.this, ListNoteActivity.class);

        note_edit_btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mes = db.deteleNote(noteId);
                Toast.makeText(NoteEditActivity.this, mes, Toast.LENGTH_SHORT).show();
                startActivity(intent1);
            }
        });

        note_edit_btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = new Note(note_edit_edNoteContent.getText().toString(), note_edit_edNoteDayCreate.getText().toString());
                String mes = db.updateNote(note, noteId);
                Toast.makeText(NoteEditActivity.this, mes, Toast.LENGTH_SHORT).show();
                startActivity(intent1);
            }
        });




    }

    public void connectView(){
        note_edit_edNoteContent = (EditText) findViewById(R.id.note_edit_edNoteContent);
        note_edit_edNoteDayCreate = (EditText) findViewById(R.id.note_edit_edNoteDayCreate);
        note_edit_btnDelete = (Button) findViewById(R.id.note_edit_btnDelete);
        note_edit_btnUpdate = (Button) findViewById(R.id.note_edit_btnUpdate);
    }
}
