package com.example.myprac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.myprac.navigation.GalleryFrag;
import com.example.myprac.navigation.HomeFrag;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private HomeFrag homeFrag;
    private GalleryFrag galleryFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.action_home:
                        setFrag(0);
                        break;
                    case R.id.action_search:
                        setFrag(0);
                        break;
                    case R.id.action_manage:
                        setFrag(0);
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
    }

    private void setFrag(int n) { //화면 교체가 일어나는 위치
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch(n) {
            case 0:
                ft.replace(R.id.main_content, homeFrag);
                ft.commit();
                /* intent0 = new Intent(this, MainActivity.class);
                startActivity(intent0);*/
                break;
            case 3:
                ft.replace(R.id.main_content, galleryFrag);
                ft.commit();
                Intent intent3 = new Intent(this, GalleryActivity.class);
                startActivity(intent3);
                break;
        }

    }

}