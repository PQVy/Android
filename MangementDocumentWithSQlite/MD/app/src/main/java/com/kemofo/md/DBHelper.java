package com.kemofo.md;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {

    public static String DB_NAME = "DocList.db";
    public static int DB_VERSION = 1;
    public static String TABLE_NAME = "TBL_DOC";
    public static String id = "id";
    public static String number = "number";
    public static String releaseUnit = "releaseUnit";
    public static String description = "description";
    public static String image = "image";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " ( " +
                id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                number + " text, " +
                releaseUnit + " text, " +
                description + " text, " +
                image + " blob)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String insertDB(CongVanDen congVanDen){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(this.number, congVanDen.getNumber());
        cv.put(this.releaseUnit, congVanDen.getReleaseUnit());
        cv.put(this.description, congVanDen.getDescription());
        cv.put(this.image, congVanDen.getImage());
        long isSuccess = db.insert(TABLE_NAME,null, cv);
        if (isSuccess > 0) {
            return "Save Success";
        }else {
            return "Save Fail";
        }
    }

    //Get All Note
    public List<CongVanDen> getDate(){
        List<CongVanDen> listItem = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM "+ TABLE_NAME;
        Cursor c = db.rawQuery(sql, null);
        if (c != null){
            c.moveToFirst();
            do{
                CongVanDen congVanDen =new CongVanDen();
                congVanDen.setId(c.getInt(c.getColumnIndex(id)));
                congVanDen.setNumber(c.getString(c.getColumnIndex(number)));
                congVanDen.setReleaseUnit(c.getString(c.getColumnIndex(releaseUnit)));
                congVanDen.setDescription(c.getString(c.getColumnIndex(description)));
                congVanDen.setImage(c.getBlob(c.getColumnIndex(image)));
                listItem.add(congVanDen);
            } while (c.moveToNext());
        }
        return listItem;
    }

    public String deteleDoc(int docId){
        SQLiteDatabase db = this.getWritableDatabase();
        int iSuccess = db.delete(TABLE_NAME, id + " = "+docId, null);
        if (iSuccess > 0) {
            return "Delete Success";
        }else {
            return "Fail";
        }
    }

    public String updateDoc(CongVanDen congVanDen, int docId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(this.number, congVanDen.getNumber());
        cv.put(this.releaseUnit, congVanDen.getReleaseUnit());
        cv.put(this.description, congVanDen.getDescription());
        cv.put(this.image, congVanDen.getImage());
        int isSuccess = db.update(TABLE_NAME, cv,id + " = " +docId, null);
        if (isSuccess > 0) {
            return "Update Success";
        }else {
            return "Fail";
        }
    }
}
