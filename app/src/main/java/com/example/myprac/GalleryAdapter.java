package com.example.myprac;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private ArrayList<Uri> gData = null;
    private Context gContext = null;

    public GalleryAdapter(ArrayList<Uri> arrayList, Context context) {
        this.gData = arrayList;
        this.gContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.account_iv_pic);
        }
    }

    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.gallery_item_list, parent, false);
        GalleryAdapter.ViewHolder viewHolder = new GalleryAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GalleryAdapter.ViewHolder holder, int position) {
        Uri image_uri = gData.get(position);

        Glide.with(gContext)
                .load(image_uri)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return gData.size();
    }
}
