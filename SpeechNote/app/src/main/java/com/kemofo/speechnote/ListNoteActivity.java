package com.kemofo.speechnote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

public class ListNoteActivity extends AppCompatActivity implements AdapterListNote.IOnClickItem {
    List<Note> listNote;

    private Context mContext;
    private Activity mActivity;
    private RelativeLayout mRelativeLayout;
    private PopupWindow mPopupWindow;
    private RecyclerView rvNoteList;
    private Button note_list_btnNewNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);

        mContext = getApplicationContext();
        mActivity = ListNoteActivity.this;
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl);
        note_list_btnNewNote = (Button) findViewById(R.id.note_list_btnNewNote);
        note_list_btnNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListNoteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });



        //Step 1: Get Data
        getData();

        //Step 2: Adapter
        AdapterListNote adapter = new AdapterListNote(listNote, this, this);

        //Step 3: Recycler View
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        //Step 4: Bind Data
        rvNoteList = (RecyclerView) findViewById(R.id.rvNoteList);
        rvNoteList.setLayoutManager(layoutManager);
        rvNoteList.setAdapter(adapter);
    }

    public void getData(){
        DBHelper db = new DBHelper(this);
        listNote = db.getDate();
    }


    @Override
    public void OnClickItem(int position) {
        final Note note = listNote.get(position);
        final DBHelper db = new DBHelper(this);
        Intent intent = new Intent(ListNoteActivity.this, NoteEditActivity.class);
        intent.putExtra("NOTEID", note.getNote_id());
        intent.putExtra("NOTECONTENT", note.getContentNote());
        intent.putExtra("NOTEDATE", note.getDateCreate());
        startActivity(intent);

//        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
//        View customView = inflater.inflate(R.layout.popup_window,null);
//        mPopupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        mPopupWindow.setOutsideTouchable(true);
//        mPopupWindow.setBackgroundDrawable(new ShapeDrawable());
//        if(Build.VERSION.SDK_INT>=21){
//            mPopupWindow.setElevation(5.0f);
//        }
//        mPopupWindow.setTouchInterceptor(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
//                    mPopupWindow.dismiss();
//                    return true;
//                }
//                return false;
//            }
//        });
//        mPopupWindow.showAtLocation(mRelativeLayout, Gravity.BOTTOM,0,0);
//        Button btnDelete = (Button) findViewById(R.id.btnDelete);
//        btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String mes = db.deteleNote(note.getNote_id());
//
//                Toast.makeText(ListNoteActivity.this, mes, Toast.LENGTH_SHORT).show();
//            }
//        });
//        Button btnCancel = (Button) customView.findViewById(R.id.btnCancel);
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mPopupWindow.dismiss();
//            }
//        });


        //Toast.makeText(this, "Note " + note.getNote_id(), Toast.LENGTH_SHORT).show();
    }

}
