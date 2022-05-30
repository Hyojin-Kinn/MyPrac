package com.example.myprac.gallery;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myprac.R;
import com.example.myprac.search.SearchAdapter;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private ArrayList<GalleryData> gData;
    private Context context;

    public GalleryAdapter(ArrayList<GalleryData> arrayList, Context context) {
        this.gData = arrayList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView preDiabet;
        TextView postDiabet;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.account_iv_pic);
            preDiabet = itemView.findViewById(R.id.account_pre);
            postDiabet = itemView.findViewById(R.id.account_post);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item_list,parent,false);
        GalleryAdapter.ViewHolder viewHolder = new GalleryAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GalleryAdapter.ViewHolder holder, int position) {
        Uri image_uri = gData.get(position).getImgUri();
        Glide.with(holder.itemView)
                .load(image_uri)
                .into(holder.image);
        holder.preDiabet.setText(gData.get(position).getPreDiabets());
        holder.postDiabet.setText(gData.get(position).getPostDiabets());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //이미지를 클릭했을 때 수행
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                remove(holder.getAdapterPosition());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return gData.size();
    }

    public void remove(int position){
        try {
            gData.remove(position);
            notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }
}
