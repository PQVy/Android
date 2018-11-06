package com.kemofo.speechnote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static String DB_NAME = "ListNote.db";
    public static int DB_VERSION = 1;
    public static String TABLE_NAME = "TBL_NOTE";
    public static String note_id = "node_id";
    public static String contentNote = "contentNote";
    public static String dateCreate = "dateCreate";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //Create DB
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " ( " +
                note_id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                contentNote + " text, " +
                dateCreate + " text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Insert new Note
    public String insertDB(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(this.contentNote, note.getContentNote());
        cv.put(this.dateCreate, note.getDateCreate());
        long isSuccess = db.insert(TABLE_NAME,null, cv);
        if (isSuccess > 0) {
            return "Save Success";
        }else {
            return "Save Fail";
        }
    }

    //Get All Note
    public List<Note> getDate(){
        List<Note> listNote = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM "+TABLE_NAME;
        Cursor c = db.rawQuery(sql, null);
        if (c != null){
            c.moveToFirst();
            do{
                Note note =new Note();
                note.setNote_id(c.getInt(c.getColumnIndex(note_id)));
                note.setContentNote(c.getString(c.getColumnIndex(contentNote)));
                note.setDateCreate(c.getString(c.getColumnIndex(dateCreate)));
                listNote.add(note);
            } while (c.moveToNext());
        }
        return listNote;
    }

    public String deteleNote(int noteid){
        SQLiteDatabase db = this.getWritableDatabase();
        int iSuccess = db.delete(TABLE_NAME, note_id + "="+noteid, null);
        if (iSuccess > 0) {
            return "Delete Success";
        }else {
            return "Fail";
        }
    }

    public String updateNote(Note note, int noteid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(this.contentNote, note.getContentNote());
        cv.put(this.dateCreate, note.getDateCreate());
        int isSuccess = db.update(TABLE_NAME, cv,note_id + "=" +noteid, null);
        if (isSuccess > 0) {
            return "Update Success";
        }else {
            return "Fail";
        }
    }


}
