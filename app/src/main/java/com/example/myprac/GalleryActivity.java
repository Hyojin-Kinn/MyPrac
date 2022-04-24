package com.example.myprac;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {

    private GalleryAdapter galleryAdapter;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;

    private static final String TAG = "GalleryActivity";
    ArrayList<Uri> uriList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.galleryfrag);

        recyclerView = (RecyclerView)findViewById(R.id.account_recyclerview);
        gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);

        Button account_btn_add = (Button)findViewById(R.id.account_btn_add);
        account_btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2222);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data == null){
            Toast.makeText(getApplicationContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
        }
        else{
            if(data.getClipData()==null){ //이미지를 한장 선택
                Log.e("single choice: ", String.valueOf(data.getData()));
                Uri imageUri = data.getData();
                uriList.add(imageUri);

                galleryAdapter = new GalleryAdapter(uriList,getApplicationContext());
                recyclerView.setAdapter(galleryAdapter);
                recyclerView.setLayoutManager(gridLayoutManager);
            }
            else{
                ClipData clipData = data.getClipData();
                Log.e("clipData", String.valueOf(clipData.getItemCount()));

                if(clipData.getItemCount()>10){
                    Toast.makeText(getApplicationContext(),"사진은 10장까지 선택 가능합니다.",Toast.LENGTH_LONG).show();
                }
                else{
                    Log.e(TAG,"multiple choice");
                    for(int i=0;i<clipData.getItemCount();i++){
                        Uri imageUri = clipData.getItemAt(i).getUri();
                        try{
                            uriList.add(imageUri);
                        } catch(Exception e){
                            Log.e(TAG, "File select error", e);
                        }
                    }
                    galleryAdapter = new GalleryAdapter(uriList,getApplicationContext());
                    recyclerView.setAdapter(galleryAdapter);
                    recyclerView.setLayoutManager(gridLayoutManager);
                }
            }
        }
    }
}
