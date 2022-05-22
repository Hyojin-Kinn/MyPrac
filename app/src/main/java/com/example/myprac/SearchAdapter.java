package com.example.myprac;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myprac.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.CustomViewHolder>{


    private ArrayList<SearchData> arrayList;
    private Context context;

    public SearchAdapter(ArrayList<SearchData> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
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
        Glide.with(holder.itemView).load(arrayList.get(position).getRecipe_Image()).into(holder.recipe_Image);
        holder.recipe_title.setText(arrayList.get(position).getRecipe_title());
        holder.recipe_description.setText(arrayList.get(position).getRecipe_description());
        holder.recipe_upload_date.setText(arrayList.get(position).getRecipe_upload_date());

        holder.itemView.setTag(position);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });

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

        protected ImageView recipe_Image;
        protected TextView recipe_title;
        protected TextView recipe_description;
        protected TextView recipe_upload_date;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.recipe_Image = itemView.findViewById(R.id.recipe_Image);
            this.recipe_title = (TextView) itemView.findViewById(R.id.recipe_title);
            this.recipe_description = (TextView) itemView.findViewById(R.id.recipe_description);
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


}
