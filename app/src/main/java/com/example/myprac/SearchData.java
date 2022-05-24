package com.example.myprac;

import com.example.myprac.recipe.StepData;

import java.util.ArrayList;

public class SearchData {

    private int recipe_number;
    private String kind;
    private String recipe_Image;
    private String recipe_title;
    private String recipe_description;
    private String recipe_upload_date;
    /*private ArrayList<String> recipe_ingredient;
    private ArrayList<StepData> recipe_steps;*/

    public int getRecipe_number() { return recipe_number; }
    public void setRecipe_number(int recipe_number) { this.recipe_number = recipe_number; }

    public String getKind() {
        return kind;
    }
    public void setKind(String kind) {
        this.kind = kind;
    }

    public SearchData(String recipe_Image, String recipe_title, String recipe_description, String recipe_upload_date, String kind) {
        this.recipe_Image = recipe_Image;
        this.recipe_title = recipe_title;
        this.recipe_description = recipe_description;
        this.recipe_upload_date = recipe_upload_date;
        this.kind = kind;

    }
    /*public SearchData(String recipe_Image, String recipe_title, String recipe_description,
                      String recipe_upload_date, String kind, ArrayList<String> recipe_ingredient, ArrayList<StepData> recipe_steps) {
        this.recipe_Image = recipe_Image;
        this.recipe_title = recipe_title;
        this.recipe_description = recipe_description;
        this.recipe_upload_date = recipe_upload_date;
        this.kind = kind;
        this.recipe_ingredient = recipe_ingredient;
        this.recipe_steps = recipe_steps;
    }*/

    public String getRecipe_Image() {
        return recipe_Image;
    }
    public void setRecipe_Image(String recipe_Image) {
        this.recipe_Image = recipe_Image;
    }

    public String getRecipe_title() {
        return recipe_title;
    }
    public void setRecipe_title(String recipe_title) {
        this.recipe_title = recipe_title;
    }

    public String getRecipe_description() {
        return recipe_description;
    }
    public void setRecipe_description(String recipe_description) {
        this.recipe_description = recipe_description;
    }

    public String getRecipe_upload_date() {
        return recipe_upload_date;
    }
    public void setRecipe_upload_date(String recipe_upload_date) {
        this.recipe_upload_date = recipe_upload_date;
    }

    /*public ArrayList<String> getRecipe_ingredient() { return recipe_ingredient; }
    public void setRecipe_ingredient(ArrayList<String> recipe_ingredient) {
        this.recipe_ingredient = recipe_ingredient;
    }

    public ArrayList<StepData> getRecipe_steps() { return recipe_steps; }
    public void setRecipe_steps(ArrayList<StepData> recipe_steps) {
        this.recipe_steps = recipe_steps;
    }*/
}
