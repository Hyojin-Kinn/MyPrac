package com.example.myprac.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.example.myprac.MainActivity;
import com.example.myprac.R;
import com.example.myprac.SearchAdapter;
import com.example.myprac.SearchData;
import com.example.myprac.SearchViewModel;
import com.example.myprac.databinding.ActivityMainBinding;
import com.example.myprac.databinding.SearchfragBinding;
import com.example.myprac.recipe.StepData;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.searchfrag, container, false);
        recyclerView = view.findViewById(R.id.recipe);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recipe_menu = new ArrayList<>();
        search_menu = new ArrayList<>();

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
