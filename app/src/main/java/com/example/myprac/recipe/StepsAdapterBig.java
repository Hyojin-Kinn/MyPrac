package com.example.myprac.recipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myprac.R;

import java.util.ArrayList;

public class StepsAdapterBig extends RecyclerView.Adapter<StepsAdapterBig.ViewHolder> {

    Context context;
    private ArrayList<StepData> stepList;

    public StepsAdapterBig(Context context, ArrayList<StepData> stepList) {
        this.context = context;
        this.stepList = stepList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_step_big, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapterBig.ViewHolder holder, int position) {
        StepData stepData = stepList.get(position);
        holder.textView.setText(stepData.getStep());
        Glide.with(context).load(stepList.get(position).getImg()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recipe_step_des);
            imageView = itemView.findViewById(R.id.recipe_step_img);
        }

    }
}
