package com.example.myprac.navigation;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.TreeSet;

import com.example.myprac.MainActivity;
import com.example.myprac.R;
import com.example.myprac.databinding.ActivityMainBinding;
import com.example.myprac.databinding.SearchfragBinding;
import com.example.myprac.recipe.StepData;
import com.example.myprac.search.SearchAdapter;
import com.example.myprac.search.SearchData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.utilities.Tree;
import com.ramotion.foldingcell.FoldingCell;


public class SearchFrag extends Fragment {

    private ArrayList<SearchData> recipe_menu;
    private ArrayList<SearchData> search_menu;


    private LinearLayoutManager linearLayoutManager;

    private View view;
    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private SearchAdapter searchAdapter;

    private ImageButton search_button;
    private EditText search_bar;
    private Spinner spinner;

    private String k;

    private LinearLayout kindHash;
    private Button rollButton;

    private FoldingCell fc;

    private ImageView bread;
    private ImageView cake;
    private ImageView deli;
    private ImageView dessert;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.searchfrag, container, false);
        recyclerView = view.findViewById(R.id.foodSearchRecyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recipe_menu = new ArrayList<>();
        search_menu = new ArrayList<>();
        search_button = view.findViewById(R.id.search_button);
        search_bar = view.findViewById(R.id.search_bar);
        spinner = view.findViewById(R.id.search_spinner);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                k = null;
                k =  parent.getItemAtPosition(position).toString();
                Log.e("MainActivity: ",k);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();; //검색 필터를 사용해서 버튼을 누를 경우 제목에 내용이 포함되어 있을 경우 나옴
            }
        });

        search_bar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if(keyCode == KeyEvent.KEYCODE_ENTER) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_ENTER:
                            search(); //검색 필터를 사용해서 버튼을 누를 경우 제목에 내용이 포함되어 있을 경우 나옴
                            break;
                    }
                    return true;
                }
                return false;
            }
        });

        search_bar.addTextChangedListener(new TextWatcher() {  //입력했을 때 이벤트 발생
            // 아무것도 입력하지 않으면 초기화하는 역할
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // 입력하기 전에 호출되는 API
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // charSequence가 안에 글자 start가 첫번째 글자?,
                // EditText에 변화가 있을 때
                if(recipe_menu !=null){
                    if(charSequence.length()==0){
                        search();
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

                // 입력이 끝났을 때

            }
        });
        bread = view.findViewById(R.id.bread);
        cake = view.findViewById(R.id.cake);
        deli = view.findViewById(R.id.deli);
        dessert = view.findViewById(R.id.dessert);

        bread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchFilter("BREAD","종류");
            }
        });
        bread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchFilter("brea","종류");
            }
        });
        cake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchFilter("cake","종류");
            }
        });
        deli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchFilter("deli","종류");
            }
        });
        dessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchFilter("dessert","종류");
            }
        });


        database = FirebaseDatabase.getInstance(); //데이터베이스 연동
        databaseReference = database.getReference("Recipe"); //DB 테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //데이터베이스를 받아오는 곳
                recipe_menu.clear(); //기존 배열이 남아있지 않게 초기화
                TreeSet<String> treeSet = new TreeSet<>();// 중복 제거
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SearchData searchData = snapshot.getValue(SearchData.class);
                    recipe_menu.add(searchData);
                }
                for(SearchData data : recipe_menu){
                    treeSet.add(data.getKind()); //recipe_menu에 있는 kind를 treeSet으로 옮김으로서 중복 제거
                }

                for(String kind: treeSet){
                    createButton(kind);
                }


                search();

                rollButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fc.toggle(true);
                    }
                });

                searchAdapter.notifyDataSetChanged();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //에러가 발생할 시 실행
            }
        });


        kindHash = view.findViewById(R.id.kindHash);
        searchAdapter = new SearchAdapter(recipe_menu, getContext());
        recyclerView.setAdapter(searchAdapter);
        rollButton = view.findViewById(R.id.kindHashRollButton);
        fc = view.findViewById(R.id.kindHashRoll);


        ((MainActivity)getActivity()).setRecipeList(recipe_menu);

        return view;
    }
    public void searchFilter(String str, String k){
        search_menu.clear();

        for(SearchData f : recipe_menu) {
            if(k.equals("제목")){
                if(f.getRecipe_title().contains(str)){
                    search_menu.add(f);
                }
            }
            else if(k.equals("내용")){
                if(f.getRecipe_description().contains(str)){
                    search_menu.add(f);
                }
            }
            else if(k.equals("종류")){
                if(f.getKind().contains(str)){
                    search_menu.add(f);
                }
            }

        }
        searchAdapter.filterList(search_menu);
    }

    public void search(){
        String str = search_bar.getText().toString();
        Log.e("SearchFrag",str);
        searchFilter(str,k);
    }
    private void createButton(String str){

        Button button = new Button(view.getContext());

        button.setText(str);

        button.setMaxLines(1);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);


        params.leftMargin = 5;
        button.setLayoutParams(params);
        button.setHorizontallyScrolling(false);
        button.setTransformationMethod(null);// 넣은 알파벳 그대로 출력
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchFilter(button.getText().toString(),"종류");
            }
        });

        kindHash.addView(button);
    }

}