package com.example.giveandtake.fragments.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giveandtake.R;
import com.example.giveandtake.model.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


class HomeViewHolder extends RecyclerView.ViewHolder{
    CircleImageView profileImage;
    TextView nameTv;
    TextView postTv;
    TextView likeCountTv;
    ImageView postImage;
    ImageButton likeBtn;
    ImageButton sendBtn;
    List<Post> data;

    public HomeViewHolder(@NonNull View itemView, HomeRecyclerAdapter.OnItemClickListener listener, List<Post> data) {
        super(itemView);
        this.data = data;
        profileImage = itemView.findViewById(R.id.profile_image);
        nameTv = itemView.findViewById(R.id.nameTv);
        postTv = itemView.findViewById(R.id.addpost_postText);
        likeCountTv = itemView.findViewById(R.id.like_countTv);
        postImage = itemView.findViewById(R.id.postImage);
        likeBtn = itemView.findViewById(R.id.likeBtn);
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int)likeBtn.getTag();
                Post post = data.get(pos);
                post.likeCount++;
            }
        });

    }
    public void bind(Post post, int pos) {
        nameTv.setText(post.userName);
        postTv.setText(post.postText);
        likeCountTv.setText(post.postLikeText);
        likeBtn.setTag(pos);
        if (post.getProfileImage()  != null && post.getProfileImage().length() > 5) {
            Picasso.get().load(post.getProfileImage()).placeholder(R.drawable.ic_person).into(profileImage);
        }else{
            profileImage.setImageResource(R.drawable.ic_person);
        }
        if (post.getPostImage()  != null && post.getPostImage().length() > 5) {
            Picasso.get().load(post.getPostImage()).placeholder(R.drawable.ic_photo_24).into(postImage);
            postImage.setVisibility(View.VISIBLE);
        }else{
            postImage.setImageResource(R.drawable.ic_photo_24);
            postImage.setVisibility(View.GONE);
        }
    }
}

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeViewHolder> {
    OnItemClickListener listener;
    public static interface OnItemClickListener{
        void onItemClick(int pos);
    }

    LayoutInflater inflater;
    List<Post> data;
    public void setData(List<Post> data){
        this.data = data;
        notifyDataSetChanged();
    }
    public HomeRecyclerAdapter(LayoutInflater inflater, List<Post> data){
        this.inflater = inflater;
        this.data = data;
    }
    void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.home_items,parent,false);
        return new HomeViewHolder(view,listener, data);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Post post = data.get(position);
        holder.bind(post,position);
    }


    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }
}
