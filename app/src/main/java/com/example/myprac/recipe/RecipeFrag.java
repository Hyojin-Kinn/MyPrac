package com.example.myprac.recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myprac.R;
import com.example.myprac.SearchData;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecipeFrag extends Fragment {

    private View view;
    private ArrayList<SearchData> recipeList;
    private int positionNum;

    private SearchData data;

    private ImageView recipeImg;
    private TextView recipeTitle;
    private TextView recipeDescript;

    public RecipeFrag(ArrayList<SearchData> recipeList, int positionNum) {
        this.recipeList = recipeList;
        this.positionNum = positionNum;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recipe, container, false);

        recipeImg = view.findViewById(R.id.recipe_img);
        recipeTitle = view.findViewById(R.id.recipe_title);
        recipeDescript = view.findViewById(R.id.recipe_description);

        data = recipeList.get(positionNum);

        Glide.with(this).load(data.getRecipe_Image()).into(recipeImg);
        recipeTitle.setText(data.getRecipe_title());
        recipeDescript.setText(data.getRecipe_description());

        return view;
    }

}
