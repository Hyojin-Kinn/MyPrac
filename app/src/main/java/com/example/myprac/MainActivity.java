package com.example.myprac;

import static android.content.ContentValues.TAG;

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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.example.myprac.navigation.SeeMoreFrag;
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

import org.json.JSONArray;
import org.json.JSONException;

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
    private SeeMoreFrag seeMoreFrag;

    ArrayList<SearchData> recipeList = new ArrayList<>(); //????????? ?????????
    ArrayList<Diabetes_level_ItemData> diabetesList = new ArrayList<>(); //?????? ?????? ?????????
    ArrayList<GalleryData> galleryList = new ArrayList<>(); //????????? ?????? ?????????

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        diabetesList = getDiabetesArrayPref(getApplicationContext(),
                "diabetes");
        galleryList = getGalleryArrayPref(getApplicationContext(),
                "gallery");

        //??????
        toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //?????? ?????? ?????? ??????
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24); //?????? ?????? ?????? ?????????

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
                    //menu1??? ???????????? ??????
                }
                else if(id == R.id.nav_menu_2){
                    //menu2??? ???????????? ??????
                }
                else if(id == R.id.nav_menu_3){
                    //menu3??? ???????????? ??????
                }
                return true;
            }
        });

        //????????????
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
                        setFrag(4);
                        break;
                }
                return true;
            }
        });

        setFrag(0); //?????? ?????? ??????*/
        FragmentManager fragmentManager = getSupportFragmentManager();
        homeFrag = new HomeFrag();
        fragmentManager.beginTransaction().replace(R.id.main_content, homeFrag).commit();

    }

    public void setFrag(int n) { //?????? ????????? ???????????? ??????
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
                if(seeMoreFrag != null) { fragmentManager.beginTransaction().hide(seeMoreFrag).commit(); }
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
                if(seeMoreFrag != null) { fragmentManager.beginTransaction().hide(seeMoreFrag).commit(); }
                if(recipeFrag != null) { fragmentManager.beginTransaction().hide(recipeFrag).commit(); }
                if(galleryAddFrag != null) { fragmentManager.beginTransaction().hide(galleryAddFrag).commit(); }
                break;
            case 2:
                if(diabetesFrag == null) {
                    diabetesFrag = new DiabetesFrag();
                    diabetesFrag.setDataList(diabetesList);
                    fragmentManager.beginTransaction().add(R.id.main_content, diabetesFrag).commit();
                }
                if(homeFrag != null) { fragmentManager.beginTransaction().hide(homeFrag).commit(); }
                if(searchFrag != null) { fragmentManager.beginTransaction().hide(searchFrag).commit(); }
                if(diabetesFrag != null) { fragmentManager.beginTransaction().show(diabetesFrag).commit(); }
                if(galleryFrag != null) { fragmentManager.beginTransaction().hide(galleryFrag).commit(); }
                if(seeMoreFrag != null) { fragmentManager.beginTransaction().hide(seeMoreFrag).commit(); }
                if(recipeFrag != null) { fragmentManager.beginTransaction().hide(recipeFrag).commit(); }
                if(galleryAddFrag != null) { fragmentManager.beginTransaction().hide(galleryAddFrag).commit(); }
                //ft.replace(R.id.main_content, diabetesFrag);
                //ft.commit();
                break;
            case 3:
                if(galleryFrag == null) {
                    galleryFrag = new GalleryFrag();
                    galleryFrag.setGdList(galleryList);
                    fragmentManager.beginTransaction().add(R.id.main_content, galleryFrag).commit();
                }
                if(homeFrag != null) { fragmentManager.beginTransaction().hide(homeFrag).commit(); }
                if(searchFrag != null) { fragmentManager.beginTransaction().hide(searchFrag).commit(); }
                if(diabetesFrag != null) { fragmentManager.beginTransaction().hide(diabetesFrag).commit(); }
                if(galleryFrag != null) { fragmentManager.beginTransaction().show(galleryFrag).commit(); }
                if(seeMoreFrag != null) { fragmentManager.beginTransaction().hide(seeMoreFrag).commit(); }
                if(recipeFrag != null) { fragmentManager.beginTransaction().hide(recipeFrag).commit(); }
                if(galleryAddFrag != null) { fragmentManager.beginTransaction().hide(galleryAddFrag).commit(); }
                break;
            case 4:
                if(seeMoreFrag == null) {
                    seeMoreFrag = new SeeMoreFrag();
                    fragmentManager.beginTransaction().add(R.id.main_content, seeMoreFrag).commit();
                }
                if(homeFrag != null) { fragmentManager.beginTransaction().hide(homeFrag).commit(); }
                if(searchFrag != null) { fragmentManager.beginTransaction().hide(searchFrag).commit(); }
                if(diabetesFrag != null) { fragmentManager.beginTransaction().hide(diabetesFrag).commit(); }
                if(galleryFrag != null) { fragmentManager.beginTransaction().hide(galleryFrag).commit(); }
                if(seeMoreFrag != null) { fragmentManager.beginTransaction().show(seeMoreFrag).commit(); }
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
                if(seeMoreFrag != null) { fragmentManager.beginTransaction().hide(seeMoreFrag).commit(); }
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
        searchView.setQueryHint("???????????? ???????????????");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:{ //?????? ?????? ?????? ????????? ??? ??????
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
            case R.id.tool_search: //????????? ?????? ?????? ????????? ??? ??????
                Toast.makeText(getApplicationContext(), "?????? ?????? ??????",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.tool_more:
                Toast.makeText(getApplicationContext(), "?????? ?????? ??????",Toast.LENGTH_SHORT).show();
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

    public void setGalleryList(ArrayList<GalleryData> galleryList) {
        this.galleryList = galleryList;
    }
    public ArrayList<GalleryData> getGalleryList() { return galleryList; }

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

    private void setDiabetesArrayPref(Context context, String key, ArrayList<Diabetes_level_ItemData> values) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();

        for(int i = 0; i<values.size();i++) {
            Diabetes_level_ItemData data = values.get(i);
            String str = data.getDate() + "," + data.getTime() + "," + data.getBef_n() + ","
                    + data.getAft_n(); //float??? ?????? String?????? ?????????
            a.put(str);
        }

        if(!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }

        editor.apply();
    }

    private ArrayList getDiabetesArrayPref(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);
        ArrayList urls = new ArrayList();

        if(json != null) {
            try {
                JSONArray a = new JSONArray(json);

                for(int i = 0; i<a.length(); i++) {
                    String url = a.optString(i);
                    String[] r = url.split(",");
                    Diabetes_level_ItemData data = new Diabetes_level_ItemData(r[0],r[1],
                            Float.parseFloat(r[2]),Float.parseFloat(r[3]));
                    urls.add(data);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

    private void setGalleryArrayPref(Context context, String key, ArrayList<GalleryData> values) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();

        for(int i = 0; i<values.size();i++) {
            GalleryData data = values.get(i);
            String str = data.getImgUri().toString() + "," + data.getPreDiabets() +
                    "," + data.getPostDiabets();
            a.put(str);
        }

        if(!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }

        editor.apply();
    }

    private ArrayList getGalleryArrayPref(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);
        ArrayList urls = new ArrayList();

        if(json != null) {
            try {
                JSONArray a = new JSONArray(json);

                for(int i = 0; i<a.length(); i++) {
                    String url = a.optString(i);
                    String[] r = url.split(",");
                    GalleryData data = new GalleryData(Uri.parse(r[0]),r[1],r[2]);
                    urls.add(data);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

    @Override
    protected void onPause() {
        super.onPause();

        setDiabetesArrayPref(getApplicationContext(),
                "diabetes", diabetesList);
        setGalleryArrayPref(getApplicationContext(),
                "gallery", galleryList);
        Log.d(TAG, "Put json");
    }
}