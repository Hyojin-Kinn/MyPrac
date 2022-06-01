package com.example.myprac;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myprac.diabets.Diabetes_level_ItemData;
import com.example.myprac.gallery.GalleryAdapter;
import com.example.myprac.gallery.GalleryAddFrag;
import com.example.myprac.gallery.GalleryData;
import com.example.myprac.navigation.DiabetesFrag;
import com.example.myprac.navigation.GalleryFrag;
import com.example.myprac.navigation.HomeFrag;
import com.example.myprac.navigation.SearchFrag;
import com.example.myprac.recipe.RecipeFrag;
import com.example.myprac.search.SearchData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationview;
    private BottomNavigationView bottomNavigationView;
    private HomeFrag homeFrag;
    private GalleryFrag galleryFrag;
    private SearchFrag searchFrag;
    private DiabetesFrag diabetesFrag;
    private RecipeFrag recipeFrag;
    private GalleryAddFrag galleryAddFrag;

    ArrayList<SearchData> recipeList = new ArrayList<>(); //레시피 리스트
    ArrayList<Diabetes_level_ItemData> diabetesList = new ArrayList<>(); //수치 기록 리스트

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

        setFrag(0); //초기 화면 지정*/
        FragmentManager fragmentManager = getSupportFragmentManager();
        homeFrag = new HomeFrag();
        fragmentManager.beginTransaction().replace(R.id.main_content, homeFrag).commit();

    }

    public void setFrag(int n) { //화면 교체가 일어나는 위치
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch(n) {
            case 0:
                if(homeFrag != null) {
                    fragmentManager.beginTransaction().remove(homeFrag).commit();
                    homeFrag = new HomeFrag();
                    fragmentManager.beginTransaction().add(R.id.main_content, homeFrag).commit();
                    fragmentManager.beginTransaction().show(homeFrag).commit();
                }
                if(searchFrag != null) { fragmentManager.beginTransaction().hide(searchFrag).commit(); }
                if(diabetesFrag != null) { fragmentManager.beginTransaction().hide(diabetesFrag).commit(); }
                if(galleryFrag != null) { fragmentManager.beginTransaction().hide(galleryFrag).commit(); }
                if(recipeFrag != null) { fragmentManager.beginTransaction().hide(recipeFrag).commit(); }
                if(galleryAddFrag != null) { fragmentManager.beginTransaction().hide(galleryAddFrag).commit(); }
                break;
            case 1:
                if(searchFrag == null) {
                    searchFrag = new SearchFrag();
                    fragmentManager.beginTransaction().add(R.id.main_content, searchFrag).commit();
                }
                if(homeFrag != null) { fragmentManager.beginTransaction().hide(homeFrag).commit(); }
                if(searchFrag != null) { fragmentManager.beginTransaction().show(searchFrag).commit(); }
                if(diabetesFrag != null) { fragmentManager.beginTransaction().hide(diabetesFrag).commit(); }
                if(galleryFrag != null) { fragmentManager.beginTransaction().hide(galleryFrag).commit(); }
                if(recipeFrag != null) { fragmentManager.beginTransaction().hide(recipeFrag).commit(); }
                if(galleryAddFrag != null) { fragmentManager.beginTransaction().hide(galleryAddFrag).commit(); }
                break;
            case 2:
                if(diabetesFrag == null) {
                    diabetesFrag = new DiabetesFrag();
                    fragmentManager.beginTransaction().add(R.id.main_content, diabetesFrag).commit();
                }
                if(homeFrag != null) { fragmentManager.beginTransaction().hide(homeFrag).commit(); }
                if(searchFrag != null) { fragmentManager.beginTransaction().hide(searchFrag).commit(); }
                if(diabetesFrag != null) { fragmentManager.beginTransaction().show(diabetesFrag).commit(); }
                if(galleryFrag != null) { fragmentManager.beginTransaction().hide(galleryFrag).commit(); }
                if(recipeFrag != null) { fragmentManager.beginTransaction().hide(recipeFrag).commit(); }
                if(galleryAddFrag != null) { fragmentManager.beginTransaction().hide(galleryAddFrag).commit(); }
                //ft.replace(R.id.main_content, diabetesFrag);
                //ft.commit();
                break;
            case 3:
                if(galleryFrag == null) {
                    galleryFrag = new GalleryFrag();
                    fragmentManager.beginTransaction().add(R.id.main_content, galleryFrag).commit();
                }
                if(homeFrag != null) { fragmentManager.beginTransaction().hide(homeFrag).commit(); }
                if(searchFrag != null) { fragmentManager.beginTransaction().hide(searchFrag).commit(); }
                if(diabetesFrag != null) { fragmentManager.beginTransaction().hide(diabetesFrag).commit(); }
                if(galleryFrag != null) { fragmentManager.beginTransaction().show(galleryFrag).commit(); }
                if(recipeFrag != null) { fragmentManager.beginTransaction().hide(recipeFrag).commit(); }
                if(galleryAddFrag != null) { fragmentManager.beginTransaction().hide(galleryAddFrag).commit(); }
                break;
            case 5:
                fragmentManager.beginTransaction().replace(R.id.main_content, recipeFrag).addToBackStack(null).commit();
                break;
            case 6:
                if(galleryAddFrag == null) {
                    galleryAddFrag = new GalleryAddFrag();
                    fragmentManager.beginTransaction().add(R.id.main_content, galleryAddFrag).commit();
                }
                if(homeFrag != null) { fragmentManager.beginTransaction().hide(homeFrag).commit(); }
                if(searchFrag != null) { fragmentManager.beginTransaction().hide(searchFrag).commit(); }
                if(diabetesFrag != null) { fragmentManager.beginTransaction().hide(diabetesFrag).commit(); }
                if(galleryFrag != null) { fragmentManager.beginTransaction().hide(galleryFrag).commit(); }
                if(recipeFrag != null) { fragmentManager.beginTransaction().hide(recipeFrag).commit(); }
                if(galleryAddFrag != null) { fragmentManager.beginTransaction().show(galleryAddFrag).addToBackStack(null).commit(); }
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
        intent.setType("image/*");
        startActivityForResult(intent,1);
    }

    public void setRecipeList(ArrayList<SearchData> recipeList) {
        this.recipeList = recipeList;
    }
    public void setRecipeFrag(int position) {
        recipeFrag = new RecipeFrag(recipeList, position);
    }

    public void setDiabetesList(ArrayList<Diabetes_level_ItemData> diabetesList) {
        this.diabetesList = diabetesList;
    }
    public ArrayList<Diabetes_level_ItemData> getDiabetesList() {
        return diabetesList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 1:
                if(resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    GalleryAddFrag.setGalleryImg(uri);
                }
                break;
        }
    }
}