package com.example.myprac;

public class SearchData {
    //private int recipe_Image;
    private String recipe_Image;
    private String recipe_title;
    private String recipe_decription;
    private String recipe_recommend_number;
    private String recipe_comment_number;
    private String recipe_upload_date;



    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    private String kind;

    public SearchData(String recipe_Image, String recipe_title, String recipe_decription, String recipe_recommend_number, String recipe_comment_number, String recipe_upload_date, String kind) {
        //this.recipe_Image = recipe_Image;
        this.recipe_Image = recipe_Image;
        this.recipe_title = recipe_title;
        this.recipe_decription = recipe_decription;
        this.recipe_recommend_number = recipe_recommend_number;
        this.recipe_comment_number = recipe_comment_number;
        this.recipe_upload_date = recipe_upload_date;
        this.kind = kind;

    }

    /*public int getRecipe_Image() {
        return recipe_Image;
    }
    public void setRecipe_Image(int recipe_Image) {
        this.recipe_Image = recipe_Image;
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

    public String getRecipe_decription() {
        return recipe_decription;
    }

    public void setRecipe_decription(String recipe_decription) {
        this.recipe_decription = recipe_decription;
    }

    public String getRecipe_recommend_number() {
        return recipe_recommend_number;
    }

    public void setRecipe_recommend_number(String recipe_recommend_number) {
        this.recipe_recommend_number = recipe_recommend_number;
    }

    public String getRecipe_comment_number() {
        return recipe_comment_number;
    }

    public void setRecipe_comment_number(String recipe_comment_number) {
        this.recipe_comment_number = recipe_comment_number;
    }

    public String getRecipe_upload_date() {
        return recipe_upload_date;
    }

    public void setRecipe_upload_date(String recipe_upload_date) {
        this.recipe_upload_date = recipe_upload_date;
    }
    /*
   public ArrayList<String> getHashTag() {
        return hashTag;
    }
    public void setHashTag(ArrayList hashTag) {
        this.hashTag = hashTag;
    }
     */
}
