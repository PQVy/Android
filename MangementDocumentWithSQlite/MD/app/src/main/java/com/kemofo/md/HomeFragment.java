package com.kemofo.md;

import android.Manifest;
import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class HomeFragment extends Fragment {

    EditText edtNumber, edtUnit, edtDes;
    Button btnChoose, btnAdd;
    ImageView imageView;
    final int REQUEST_CODE_GALLERY = 999;

    public static DBHelper dbHelper;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtNumber = (EditText) getView().findViewById(R.id.edtNumber);
        edtUnit = (EditText) getView().findViewById(R.id.edtUnit);
        edtDes = (EditText) getView().findViewById(R.id.edtDes);
        btnChoose = (Button) getView().findViewById(R.id.btnChoose);
        btnAdd = (Button) getView().findViewById(R.id.btnAdd);

        imageView = (ImageView) getView().findViewById(R.id.imageView);


        dbHelper = new DBHelper(getActivity());
        dbHelper.getWritableDatabase();
//
//
//
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);

//                ActivityCompat.requestPermissions(getActivity()
//                        , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                        REQUEST_CODE_GALLERY
//                );
            }
        });
//
//
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDoc();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home, container, false);
        return v;


    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        if(requestCode == REQUEST_CODE_GALLERY){
//            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent, REQUEST_CODE_GALLERY);
//            }
//            else {
//                Toast.makeText(getActivity().getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
//            }
//            return;
//        }
//
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == getActivity().RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

//    private void connectView(){
//        edtNumber = (EditText) v.findViewById(R.id.edtNumber);
//        edtUnit = (EditText) findViewById(R.id.edtUnit);
//        edtDes = (EditText) findViewById(R.id.edtDes);
//        btnChoose = (Button) findViewById(R.id.btnChoose);
//        btnAdd = (Button) findViewById(R.id.btnAdd);
//        btnList = (Button) findViewById(R.id.btnList);
//        imageView = (ImageView) findViewById(R.id.imageView);
//
//
//    }

    public void insertDoc(){
        String number = edtNumber.getText().toString();
        String unit = edtUnit.getText().toString();
        String description = edtDes.getText().toString();
        try{
            CongVanDen congVanDen = new CongVanDen(number, unit, description, imageViewToByte(imageView));
            String mes = dbHelper.insertDB(congVanDen);
            Toast.makeText(getActivity(), mes, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
