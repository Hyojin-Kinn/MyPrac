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

import java.util.ArrayList;

import com.example.myprac.SearchAdapter;
import com.example.myprac.SearchData;
import com.example.myprac.SearchViewModel;
import com.example.myprac.databinding.ActivityMainBinding;
import com.example.myprac.databinding.SearchfragBinding;


public class SearchFrag extends Fragment {

    private SearchfragBinding binding;
    private ArrayList<SearchData> recipe_menu;
    private ArrayList<SearchData> search_menu;

    private com.example.myprac.SearchAdapter SearchAdapter;
    private LinearLayoutManager linearLayoutManager;
    private SearchData SearchData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SearchViewModel notificationsViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = SearchfragBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setContentView(R.layout.activity_main);
        //id 부여 이것들 나중에 뷰 바인딩해서 없애야 하는 건가?
        //recyclerView = (RecyclerView)findViewById(R.id.recipe);
        linearLayoutManager = new LinearLayoutManager(getContext());
        binding.recipe.setLayoutManager(linearLayoutManager);
        /*recyclerView = (RecyclerView)findViewById(R.id.recipe);
        //array_recent = (TextView)findViewById(R.id.array_recent_button);
        //array_recommend = (TextView)findViewById(R.id.array_recommend_button);
        //array_comment = (TextView)findViewById(R.id.array_comment_button);
        //search_button = (ImageButton) findViewById(R.id.search_button);
        //tableLayout = (TableLayout)findViewById(R.id.invisible);
        //search_bar = (EditText)findViewById(R.id.search_bar);
        //cake = (ImageView)findViewById(R.id.cake);
        //snack = (ImageView)findViewById(R.id.snack);
        //toast = (ImageView)findViewById(R.id.toast);
        //baguette = (ImageView)findViewById(R.id.baguette);*/

        recipe_menu = new ArrayList<>();
        search_menu = new ArrayList<>();





        //임시로 데이터 넣기 -> 음식 페이지에서 데이터 받아 와야 한다.
        SearchData = new SearchData("케이크","easy recipe1","샘플","1","2","2021-12-15","cake");
        recipe_menu.add(SearchData);


        SearchData = new SearchData("쿠키","easy recipe2","샘플","2","1","2020-12-15","cake");
        recipe_menu.add(SearchData);


        SearchData = new SearchData("토스트","hard recipe2","샘플","3","1","2020-12-15","toast");
        recipe_menu.add(SearchData);


        SearchData = new SearchData("바게트","very hard recipe3","샘플","2","3","2019-12-15","snack");
        recipe_menu.add(SearchData);


        SearchAdapter = new SearchAdapter(recipe_menu);

        binding.recipe.setAdapter(SearchAdapter);

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = binding.searchBar.getText().toString();
                searchFilter(str); //검색 필터를 사용해서 버튼을 누를 경우 제목에 내용이 포함되어 있을 경우 나옴
            }
        });

        binding.cake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //tableLayout.setVisibility(View.GONE);
                kindSearch("cake");


            }
        });
        binding.snack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //tableLayout.setVisibility(View.GONE);
                kindSearch("snack");


            }
        });
        binding.toast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //tableLayout.setVisibility(View.GONE);
                kindSearch("toast");


            }
        });
        binding.baguette.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //tableLayout.setVisibility(View.GONE);
                kindSearch("baguette");


            }
        });



        //Button btn_add1 = (Button)findViewById(R.id.btn_add);
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //SearchData = new SearchData(R.drawable.ic_launcher_background,"쉬운 요리1","샘플","5","5","2019-12-15","스낵");
                //recipe_menu.add(SearchData);
                //SearchAdapter.notifyDataSetChanged();//새로고침
            }
        });
        binding.arrayRecentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchAdapter.Array_recent();
                SearchAdapter.notifyDataSetChanged();


            }
        });
        binding.arrayRecommendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchAdapter.Array_recommend();
                SearchAdapter.notifyDataSetChanged();

            }
        });

        binding.arrayCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchAdapter.Array_comment();
                SearchAdapter.notifyDataSetChanged();

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void searchFilter(String str){
        search_menu.clear();

        for(SearchData m : recipe_menu) {
            if(m.getRecipe_title().contains(str)){
                search_menu.add(m);
            }
        }
        SearchAdapter.filterList(search_menu);
    }
    public void kindSearch(String str){
        search_menu.clear();
        for(SearchData m : recipe_menu){
            if(m.getKind().contains(str)){
                search_menu.add(m);
            }
        }
        SearchAdapter.filterList(search_menu);
    }
}
