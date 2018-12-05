package com.kemofo.md;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterDocList extends RecyclerView.Adapter {

    private List<CongVanDen> listItem;
    private Activity activity;
    private IOnClickItem iOnClickItemListener;

    public AdapterDocList(List<CongVanDen> listItem, Activity activity, IOnClickItem iOnClickItemListener) {
        this.listItem = listItem;
        this.activity = activity;
        this.iOnClickItemListener = iOnClickItemListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View v = inflater.inflate(R.layout.doc_items, viewGroup, false);
        DocListHolder noteListHolder = new DocListHolder(v);
        return noteListHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        DocListHolder noteListHolder = (DocListHolder) viewHolder;
        CongVanDen congVanDen = listItem.get(i);
        byte[] docImage = congVanDen.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(docImage, 0, docImage.length);
        noteListHolder.imageDoc.setImageBitmap(bitmap);
        noteListHolder.txtNumber.setText(congVanDen.getNumber());
        noteListHolder.txtDes.setText(congVanDen.getDescription());
        noteListHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickItemListener.OnClickItem(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }



    public class DocListHolder extends RecyclerView.ViewHolder {
        ImageView imageDoc;
        TextView txtNumber;
        TextView txtDes;
        private DocListHolder(@NonNull View itemView) {
            super(itemView);

            imageDoc = itemView.findViewById(R.id.imageList);
            txtNumber = itemView.findViewById(R.id.txtNumberList);
            txtDes = itemView.findViewById(R.id.txtDesList);
        }
    }

    public interface IOnClickItem{
        void OnClickItem( int postion);
    }
}

