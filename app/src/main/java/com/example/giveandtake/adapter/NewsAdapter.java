package com.example.giveandtake.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.giveandtake.R;
import com.example.giveandtake.fragments.WebView;
import com.example.giveandtake.model.ModelNews;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    Context context;
    ArrayList<ModelNews> modelNewsArrayList;

    public NewsAdapter(Context context, ArrayList<ModelNews> modelNewsArrayList) {
        this.context = context;
        this.modelNewsArrayList = modelNewsArrayList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.layout_news_item, null,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        /*
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, WebView.class);
                intent.putExtra("url", modelNewsArrayList.get(position).getUrl());
                context.startActivity(intent);
            }
        });
         */
        holder.mTime.setText("Published At: " + modelNewsArrayList.get(position).getPublishedAt());
        holder.mAuthor.setText("Author: " + modelNewsArrayList.get(position).getAuthor());
        holder.mHeading.setText(modelNewsArrayList.get(position).getTitle());
        holder.mContent.setText(modelNewsArrayList.get(position).getDescription());
        Glide.with(context).load(modelNewsArrayList.get(position).getUrlToImage())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return modelNewsArrayList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView mHeading, mContent, mAuthor, mTime;
        CardView cardView;
        ImageView imageView;



        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            mHeading = itemView.findViewById(R.id.mainHeading_news);
            mAuthor = itemView.findViewById(R.id.author_news);
            mTime = itemView.findViewById(R.id.time_news);
            mContent = itemView.findViewById(R.id.content_news);
            imageView = itemView.findViewById(R.id.imageView_news);
            cardView = itemView.findViewById(R.id.cardView_news);
        }
    }
}
