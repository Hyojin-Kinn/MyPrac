package com.example.myprac;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myprac.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.CustomViewHolder>{


    private ArrayList<SearchData> arrayList;

    public SearchAdapter(ArrayList<SearchData> arrayList) {
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);



        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.CustomViewHolder holder, int position) {
        holder.recipe_Image.setText(arrayList.get(position).getRecipe_Image());
        holder.recipe_title.setText(arrayList.get(position).getRecipe_title());
        holder.recipe_decription.setText(arrayList.get(position).getRecipe_decription());
        holder.recipe_recommend_number.setText(arrayList.get(position).getRecipe_recommend_number());
        holder.recipe_comment_number.setText(arrayList.get(position).getRecipe_comment_number());
        holder.recipe_upload_date.setText(arrayList.get(position).getRecipe_upload_date());

        holder.itemView.setTag(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                remove(holder.getAdapterPosition());
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null !=arrayList ? arrayList.size():0);
    }
    public void remove(int position){
        try{
            arrayList.remove(position);
            notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView recipe_Image;
        protected TextView recipe_title;
        protected TextView recipe_decription;
        protected TextView recipe_recommend_number;
        protected TextView recipe_comment_number;
        protected TextView recipe_upload_date;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.recipe_Image = (TextView) itemView.findViewById(R.id.recipe_Image);
            this.recipe_title = (TextView) itemView.findViewById(R.id.recipe_title);
            this.recipe_decription = (TextView) itemView.findViewById(R.id.recipe_decription);
            this.recipe_recommend_number = (TextView) itemView.findViewById(R.id.recipe_recommend_number);
            this.recipe_comment_number = (TextView) itemView.findViewById(R.id.recipe_comment_number);
            this.recipe_upload_date = (TextView) itemView.findViewById(R.id.recipe_upload_date);


        }
    }
    public  void filterList(ArrayList<SearchData> m){
        arrayList = m;
        notifyDataSetChanged();
    }


    //정렬 처음에는 MainActivity쪽에 놓았다가 검색한 것은 정렬이 안되길래 여기다 옮김
    //compareTo는 내림차순으로 맞춰둠
    public void Array_recent(){
        Collections.sort(arrayList, new Comparator<SearchData>() {
            @Override
            public int compare(SearchData m1, SearchData m2) {
                return m2.getRecipe_upload_date().compareTo(m1.getRecipe_upload_date());
            }
        });
    }

    public void Array_recommend(){
        Collections.sort(arrayList, new Comparator<SearchData>() {
            @Override
            public int compare(SearchData m1, SearchData m2) {
                return m2.getRecipe_recommend_number().compareTo(m1.getRecipe_recommend_number());
            }
        });

    }
    public void Array_comment(){
        Collections.sort(arrayList, new Comparator<SearchData>() {
            @Override
            public int compare(SearchData m1, SearchData m2) {
                return m2.getRecipe_comment_number().compareTo(m1.getRecipe_comment_number());
            }
        });
    }
}
