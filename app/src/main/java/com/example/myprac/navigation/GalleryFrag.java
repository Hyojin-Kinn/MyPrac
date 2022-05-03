package com.example.myprac.navigation;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myprac.GalleryAdapter;
import com.example.myprac.MainActivity;
import com.example.myprac.R;

import java.util.ArrayList;

public class GalleryFrag extends Fragment {

    private View view;

    private static RecyclerView recyclerView;
    private static GridLayoutManager gridLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.galleryfrag, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.account_recyclerview);
        gridLayoutManager = new GridLayoutManager(view.getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);

        Button account_btn_add = (Button)view.findViewById(R.id.account_btn_add);
        account_btn_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MainActivity activity = (MainActivity) getActivity();
                activity.GalleryAdd();
            }
        });

        return view;
    }

    public static void setRecyclerView(GalleryAdapter galleryAdapter){
        recyclerView.setAdapter(galleryAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);
    }
}