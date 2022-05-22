package com.example.myprac.diabets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myprac.R;

import java.util.ArrayList;

// Adapter는 RecyclerView.Adapter를 상속받아 구현하고, ViewHolder는 RecyclerView.ViewHolder를 상속받아 구현
public class Diabetes_level_MyRecyclerAdapter extends RecyclerView.Adapter<Diabetes_level_MyRecyclerAdapter.ViewHolder> {

    private ArrayList<Diabetes_level_ItemData> itemData;

    public Diabetes_level_MyRecyclerAdapter(ArrayList<Diabetes_level_ItemData> itemData) {
        this.itemData = itemData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diabetes_level_list_item, parent, false);
        // list_item을 parent ViewGroup위에 inflate시켜 새로운 view 만들기
        return new ViewHolder(view);    // 그리고 이 view를 바탕으로 ViewHolder 객체 생성
    }

    // ViewHolder에 각각의 항목들을 바인딩
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Diabetes_level_ItemData item = itemData.get(position);
        holder.time.setText(item.getTime());
        holder.bef_n.setText(item.getBef_n());
        holder.aft_n.setText(item.getAft_n());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //클릭 시 수행할 내용
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
           @Override
           public boolean onLongClick(View v){
               remove(holder.getAbsoluteAdapterPosition());
               return true;
           }
        });
    }

    @Override
    public int getItemCount() {
        return itemData.size();
    }

    // RecyclerView의 ViewHolder 만들기
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView time, bef_n, aft_n;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.dli_txt_time);
            bef_n = itemView.findViewById(R.id.dli_txt_before_num);
            aft_n = itemView.findViewById(R.id.dli_txt_after_num);
        }
    }
    public void remove (int position){
        try {
            itemData.remove(position);
            notifyDataSetChanged();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}