package com.example.myprac.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myprac.R;

import java.util.ArrayList;
import java.util.Arrays;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {

    int[] images;
    int bannerNum;
    View view;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            notifyDataSetChanged();
        }
    };

    public BannerAdapter(int[] images, int bannerNum) {
        this.images = images;
        this.bannerNum = bannerNum;
    }

    @NonNull
    @Override
    public BannerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(bannerNum == 0) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.banner1, parent, false);
        }
        if(bannerNum == 1) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.banner2, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerAdapter.ViewHolder holder, int position) {
        int index = position%images.length;
        holder.imageView.setImageResource(images[index]);

        if(bannerNum==0) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //이미지 클릭시 수행
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return images.length*100;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            if(bannerNum == 0){ imageView = itemView.findViewById(R.id.banner_img); }
            if(bannerNum == 1){ imageView = itemView.findViewById(R.id.banner_img_2); }
        }
    }
}
