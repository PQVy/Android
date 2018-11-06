package com.kemofo.speechnote;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class AdapterListNote extends RecyclerView.Adapter {

    private List<Note> listNote;
    private Activity activity;
    private IOnClickItem iOnClickItemListener;

    public AdapterListNote(List<Note> listNote, Activity activity, IOnClickItem iOnClickItemListener) {
        this.listNote = listNote;
        this.activity = activity;
        this.iOnClickItemListener = iOnClickItemListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View v = inflater.inflate(R.layout.note_list, viewGroup, false);
        NoteListHolder noteListHolder = new NoteListHolder(v);
        return noteListHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        NoteListHolder noteListHolder = (NoteListHolder) viewHolder;
        Note note = listNote.get(i);
//        noteListHolder.txtNoteId.setText(note.getNote_id()+"");
        noteListHolder.txtNoteId.setText(listNote.indexOf(note)+"");
        noteListHolder.txtNoteContent.setText(note.getContentNote());
        noteListHolder.txtNoteDate.setText(note.getDateCreate());
        noteListHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickItemListener.OnClickItem(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listNote.size();
    }

    public class NoteListHolder extends RecyclerView.ViewHolder {
        TextView txtNoteId;
        TextView txtNoteContent;
        TextView txtNoteDate;
        public NoteListHolder(@NonNull View itemView) {
            super(itemView);

            txtNoteId = itemView.findViewById(R.id.txtNoteId);
            txtNoteContent = itemView.findViewById(R.id.txtNoteContent);
            txtNoteDate = itemView.findViewById(R.id.txtNoteDate);
        }
    }

    public interface IOnClickItem{
        void OnClickItem( int postion);
    }
}
