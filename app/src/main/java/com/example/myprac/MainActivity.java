package com.example.myprac;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myprac.databinding.ActivityMainBinding;
import com.example.myprac.navigation.DiabetesFrag;
import com.example.myprac.navigation.GalleryFrag;
import com.example.myprac.navigation.HomeFrag;
import com.example.myprac.navigation.SearchFrag;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private HomeFrag homeFrag;
    private GalleryFrag galleryFrag;
    private SearchFrag searchFrag;
    private DiabetesFrag diabetesFrag;

    private GalleryAdapter galleryAdapter;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;

    //search기능 추가
    private ActivityMainBinding binding;

    private static final String TAG = "GalleryFrag";
    ArrayList<Uri> uriList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater()); //search
        setContentView(binding.getRoot());

        bottomNavigationView = findViewById(R.id.bottom_navi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.action_home:
                        setFrag(0);
                        break;
                    case R.id.action_search:
                        setFrag(0); //누를 시 어플 종료되어서 임시조치
                        break;
                    case R.id.action_manage:
                        setFrag(2);
                        break;
                    case R.id.action_gallery:
                        setFrag(3);
                        break;
                    case R.id.action_more:
                        setFrag(0);
                        break;
                }
                return true;
            }
        });
        homeFrag = new HomeFrag();
        galleryFrag = new GalleryFrag();
        setFrag(0); //초기 화면 지정

        /*AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.action_home, R.id.action_search,R.id.action_manage,R.id.action_gallery, R.id.action_more)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.main_content);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavi, navController);*/
    }

    private void setFrag(int n) { //화면 교체가 일어나는 위치
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch(n) {
            case 0:
                ft.replace(R.id.main_content, homeFrag);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_content, searchFrag);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_content, diabetesFrag);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_content, galleryFrag);
                ft.commit();
                break;
        }

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