package com.example.myprac;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myprac.home.BannerAdapter;
import com.example.myprac.navigation.DiabetesFrag;
import com.example.myprac.navigation.GalleryFrag;
import com.example.myprac.navigation.HomeFrag;
import com.example.myprac.navigation.SearchFrag;
import com.example.myprac.recipe.RecipeFrag;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationview;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private HomeFrag homeFrag;
    private GalleryFrag galleryFrag;
    private SearchFrag searchFrag;
    private DiabetesFrag diabetesFrag;
    private RecipeFrag recipeFrag;

    private GalleryAdapter galleryAdapter;

    private static final String TAG = "GalleryFrag";
    ArrayList<Uri> uriList = new ArrayList<>();

    ArrayList<SearchData> recipeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //툴바
        toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //왼쪽 상단 버튼 생성
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24); //왼쪽 상단 버튼 이미지

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationview = (NavigationView)findViewById(R.id.left_navi);

        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();

                int id = item.getItemId();
                String title = item.getTitle().toString();

                if(id == R.id.nav_menu_1){
                    //menu1을 눌렀을때 실행
                }
                else if(id == R.id.nav_menu_2){
                    //menu2을 눌렀을때 실행
                }
                else if(id == R.id.nav_menu_3){
                    //menu3을 눌렀을때 실행
                }
                return true;
            }
        });

        //바텀네비
        bottomNavigationView = findViewById(R.id.bottom_navi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.action_home:
                        setFrag(0);
                        break;
                    case R.id.action_search:
                        setFrag(1);
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
        diabetesFrag = new DiabetesFrag();
        searchFrag = new SearchFrag();
        setFrag(0); //초기 화면 지정

        /*AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.action_home, R.id.action_search,R.id.action_manage,R.id.action_gallery, R.id.action_more)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.main_content);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNavi, navController);*/
    }

    public void setFrag(int n) { //화면 교체가 일어나는 위치
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
            case 5:
                ft.replace(R.id.main_content, recipeFrag);
                ft.commit();
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        SearchView searchView = (SearchView)menu.findItem(R.id.tool_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("검색어를 입력하세요");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:{ //왼쪽 상단 버튼 눌렀을 때 실행
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
            case R.id.tool_search: //오른쪽 상단 버튼 눌렀을 때 실행
                Toast.makeText(getApplicationContext(), "검색 버튼 클릭",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.tool_more:
                Toast.makeText(getApplicationContext(), "설정 버튼 클릭",Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void GalleryAdd(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2222);
    }

    public void setRecipeList(ArrayList<SearchData> recipeList) {
        this.recipeList = recipeList;
    }
    public void setRecipeFrag(int position) {
        recipeFrag = new RecipeFrag(recipeList, position);
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
                GalleryFrag.setRecyclerView(galleryAdapter);
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
                    GalleryFrag.setRecyclerView(galleryAdapter);
                }
            }
        }
    }
}