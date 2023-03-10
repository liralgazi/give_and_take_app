package com.example.giveandtake.adapter;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giveandtake.R;
import com.example.giveandtake.model.GalleryImages;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryHolder> {
    private final List<GalleryImages> list;



    public GalleryAdapter(List<GalleryImages> list)
    {
        this.list = list;
    }


    @NonNull
    @Override
    public GalleryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_items, parent,false);
        return new GalleryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.imageView.setImageURI(list.get(position).getPicUri());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage(list.get(position).getPicUri());
            }
        });

    }

    private void chooseImage(Uri picUri)
    {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class GalleryHolder extends RecyclerView.ViewHolder{

        private final ImageView imageView;

       public GalleryHolder(@NonNull View itemView) {
           super(itemView);
           imageView = itemView.findViewById(R.id.imageView);
       }
   }
}
