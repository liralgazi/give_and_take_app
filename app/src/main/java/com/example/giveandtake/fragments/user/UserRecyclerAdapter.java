package com.example.giveandtake.fragments.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giveandtake.R;
import com.example.giveandtake.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

class UserViewHolder extends RecyclerView.ViewHolder{
    TextView nameTv;
    TextView statusTv;
    List<User> data;
    ImageView profileImage;
    public UserViewHolder(@NonNull View itemView, UserRecyclerAdapter.OnItemClickListener listener, List<User> data) {
        super(itemView);
        this.data = data;
        nameTv = itemView.findViewById(R.id.user_nameTv);
        statusTv = itemView.findViewById(R.id.user_statusTv);
        profileImage = itemView.findViewById(R.id.user_profileImage);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getBindingAdapterPosition();
                listener.onItemClick(pos);
            }
        });
    }

    public void bind(User user, int pos) {
        nameTv.setText(user.name);
        statusTv.setText(user.uid);
        if (user.getProfileImageURL()  != null && user.getProfileImageURL().length() > 5) {
            Picasso.get().load(user.getProfileImageURL()).placeholder(R.drawable.ic_person).into(profileImage);
        }else{
            profileImage.setImageResource(R.drawable.ic_person);
        }
    }
}

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserViewHolder> {
    OnItemClickListener listener;

    public static interface OnItemClickListener {
        void onItemClick(int pos);
    }

    LayoutInflater inflater;
    List<User> data;

    public void setData(List<User> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public UserRecyclerAdapter(LayoutInflater inflater, List<User> data) {
        this.inflater = inflater;
        this.data = data;
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.user_items, parent, false);
        return new UserViewHolder(view, listener, data);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = data.get(position);
        holder.bind(user, position);
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        return data.size();
    }
}
