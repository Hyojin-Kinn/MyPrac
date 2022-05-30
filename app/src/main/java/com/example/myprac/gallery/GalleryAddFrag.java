package com.example.myprac.gallery;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myprac.MainActivity;
import com.example.myprac.R;
import com.example.myprac.navigation.GalleryFrag;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class GalleryAddFrag extends Fragment {

    private View view;

    private static ImageView galleryImg;

    private EditText editText1;
    private EditText editText2;

    private String preDiabet;
    private String postDiabet;

    private static Uri imageUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.gallery_add_page, container, false);

        Button selectBtn =  view.findViewById(R.id.button);
        Button uploadBtn = view.findViewById(R.id.button2);

        galleryImg = view.findViewById(R.id.gallery_img);
        editText1 = view.findViewById(R.id.prediabet);
        editText2 = view.findViewById(R.id.postdiabet);

        selectBtn.setOnClickListener(new View.OnClickListener() { //이미지 선택 버튼 클릭
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                activity.GalleryAdd();
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() { //업로드 버튼 클릭
            @Override
            public void onClick(View view) {
                preDiabet = editText1.getText().toString();
                postDiabet = editText2.getText().toString();
                GalleryData gd = new GalleryData(imageUri, preDiabet, postDiabet);
                GalleryFrag.addGalleryList(gd);
                MainActivity activity = (MainActivity)getActivity();
                activity.setFrag(3);
            }
        });

        return view;
    }
    public static void setGalleryImg(Uri uri) {
        galleryImg.setImageURI(uri);
        imageUri = uri;
    }
}
