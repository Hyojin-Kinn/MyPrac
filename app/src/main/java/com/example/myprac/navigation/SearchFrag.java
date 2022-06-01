package com.example.myprac.navigation;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

import com.example.myprac.MainActivity;
import com.example.myprac.R;
import com.example.myprac.search.SearchAdapter;
import com.example.myprac.search.SearchData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SearchFrag extends Fragment {

    private ArrayList<SearchData> recipe_menu;
    private ArrayList<SearchData> search_menu;


    private LinearLayoutManager linearLayoutManager;

    private View view;
    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private SearchAdapter searchAdapter;
    private EditText searchBar;
    private ImageButton search_btn;

    private ImageView breadImg;
    private ImageView cakeImg;
    private ImageView deliImg;
    private ImageView dessertImg;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.searchfrag, container, false);
        recyclerView = view.findViewById(R.id.recipe);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        searchBar = view.findViewById(R.id.search_bar);
        search_btn = view.findViewById(R.id.search_button);

        recipe_menu = new ArrayList<>();
        search_menu = new ArrayList<>();

        //검색버튼 기능
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchBar.getText().toString();
                search_menu.clear();

                if(searchText.equals("")){
                    searchAdapter.filterList(recipe_menu);
                }
                else {
                    for(int i = 0;i<recipe_menu.size();i++) {
                        if(recipe_menu.get(i).recipe_title.toLowerCase().contains(searchText.toLowerCase())) {
                            search_menu.add(recipe_menu.get(i));
                        }
                        searchAdapter.filterList(search_menu);
                    }
                }
            }
        });

        /*searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchText = searchBar.getText().toString();
                search_menu.clear();

                if(searchText.equals("")){
                    searchAdapter.filterList(recipe_menu);
                }
                else {
                    for(int i = 0;i<recipe_menu.size();i++) {
                        if(recipe_menu.get(i).recipe_title.toLowerCase().contains(searchText.toLowerCase())) {
                            search_menu.add(recipe_menu.get(i));
                        }
                        searchAdapter.filterList(search_menu);
                    }
                }
            }
        });*/

        breadImg = view.findViewById(R.id.bread);
        cakeImg = view.findViewById(R.id.cake);
        deliImg = view.findViewById(R.id.deli);
        dessertImg = view.findViewById(R.id.dessert);

        //카테고리 선택기능
        breadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_menu.clear();
                for(int i = 0; i<recipe_menu.size(); i++) {
                    if(recipe_menu.get(i).getKind().equals("bread")) {
                        search_menu.add(recipe_menu.get(i));
                    }
                    searchAdapter.filterList(search_menu);
                }
            }
        });
        cakeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_menu.clear();
                for(int i = 0; i<recipe_menu.size(); i++) {
                    if(recipe_menu.get(i).getKind().equals("cake")) {
                        search_menu.add(recipe_menu.get(i));
                    }
                    searchAdapter.filterList(search_menu);
                }
            }
        });
        deliImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_menu.clear();
                for(int i = 0; i<recipe_menu.size(); i++) {
                    if(recipe_menu.get(i).getKind().equals("deli")) {
                        search_menu.add(recipe_menu.get(i));
                    }
                    searchAdapter.filterList(search_menu);
                }
            }
        });
        dessertImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_menu.clear();
                for(int i = 0; i<recipe_menu.size(); i++) {
                    if(recipe_menu.get(i).getKind().equals("dessert")) {
                        search_menu.add(recipe_menu.get(i));
                    }
                    searchAdapter.filterList(search_menu);
                }
            }
        });

        database = FirebaseDatabase.getInstance(); //데이터베이스 연동
        databaseReference = database.getReference("Recipe"); //DB 테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //데이터베이스를 받아오는 곳
                recipe_menu.clear(); //기존 배열이 남아있지 않게 초기화
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SearchData searchData = snapshot.getValue(SearchData.class);
                    recipe_menu.add(searchData);
                }
                searchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //에러가 발생할 시 실행
            }
        });


        searchAdapter = new SearchAdapter(recipe_menu, getContext());
        recyclerView.setAdapter(searchAdapter);

        ((MainActivity)getActivity()).setRecipeList(recipe_menu);

        return view;
    }

}
