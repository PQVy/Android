package com.kemofo.md;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment2 extends Fragment implements AdapterDocList.IOnClickItem {

    List<CongVanDen> listItem;
    private RecyclerView rvNoteList;
    private Button btnAddnew;
    AdapterDocList adapter;
    DBHelper db;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        btnAddnew = (Button) getView().findViewById(R.id.btnAddnew);
////        btnAddnew.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent intent = new Intent(, MainActivity.class);
////                startActivity(intent);
////            }
////        });
        //Step 1: Get Data
        getData();


        //Step 2: Adapter
        adapter = new AdapterDocList(listItem, getActivity(), this);

        //Step 3: Recycler View
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);

        //Step 4: Bind Data
        rvNoteList = (RecyclerView) getView().findViewById(R.id.rvNoteList);
        rvNoteList.setLayoutManager(layoutManager);
        rvNoteList.setAdapter(adapter);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home2, container, false);
        return v;

    }

    public void getData(){
        db = new DBHelper(getActivity());
        listItem = db.getDate();
    }

    @Override
    public void OnClickItem(final int position) {
        Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT).show();

        CharSequence[] items = {"Update", "Delete"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        dialog.setTitle("Choose an action");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    showDialogUpdate(getActivity(), listItem.get(position));
                } else {
                    // delete
                    Cursor c = db.getData("SELECT id FROM TBL_DOC");
                    ArrayList<Integer> arrID = new ArrayList<Integer>();
                    while (c.moveToNext()){
                        arrID.add(c.getInt(0));
                    }
                    showDialogDelete(arrID.get(position));
                }
            }
        });
        dialog.show();
    }

    private void showDialogDelete(final int idDoc){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(getActivity());

        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to this delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    String mes = db.deteleDoc(idDoc);
                    Toast.makeText(getActivity(), mes, Toast.LENGTH_SHORT).show();

                } catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                dialog.dismiss();
                getData();
                Fragment fragment = new HomeFragment2();
//                startActivity(getActivity().getIntent());
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    ImageView imageViewDoc;
    private void showDialogUpdate(Activity activity, final CongVanDen congVanDen) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_doc);
        dialog.setTitle("Update");
        imageViewDoc = (ImageView) dialog.findViewById(R.id.imageVieDoc);
        final EditText editNumber = (EditText) dialog.findViewById(R.id.editNumber);
        final EditText editUnit = (EditText) dialog.findViewById(R.id.editUnit);
        final EditText editDes = (EditText) dialog.findViewById(R.id.editDes);
        Button btnUpdate = (Button) dialog.findViewById(R.id.btnChoose);
        editNumber.setText(congVanDen.getNumber());
        editUnit.setText(congVanDen.getReleaseUnit());
        editDes.setText(congVanDen.getDescription());
        byte[] docImage = congVanDen.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(docImage, 0, docImage.length);
        imageViewDoc.setImageBitmap(bitmap);

        // set width for dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        // set height for dialog
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        imageViewDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // request photo library

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
//                ActivityCompat.requestPermissions(
//                        getActivity(),
//                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                        888
//                );
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = listItem.indexOf(congVanDen) + 1;
                try {
                    CongVanDen congVanDen = new CongVanDen( editNumber.getText().toString(),
                            editUnit.getText().toString(),
                            editDes.getText().toString(),
                            HomeFragment.imageViewToByte(imageViewDoc));
                    String mes = db.updateDoc(congVanDen, position);
                    Toast.makeText(getActivity(), mes, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Please insert all information", Toast.LENGTH_SHORT).show();
                }
                getData();
                Fragment fragment = new HomeFragment2();
//                startActivity(getActivity().getIntent());
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 888 && resultCode == getActivity().RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageViewDoc.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
