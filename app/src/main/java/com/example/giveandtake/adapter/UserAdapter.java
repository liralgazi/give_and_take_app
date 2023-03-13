package com.example.giveandtake.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.giveandtake.R;
import com.example.giveandtake.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {


    private List<User> list;
    OnUserClicked onUserClicked;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public UserAdapter(List<User> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_items, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, @SuppressLint("RecyclerView") int position) {
        if (list.get(position).getId().equals(user.getUid())) {
            holder.layout.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
        }
        else
            holder.layout.setVisibility(View.VISIBLE);

        holder.nameTV.setText(list.get(position).getName());
        holder.statusTV.setText(list.get(position).getVolunteerStatus());
        Glide.with(holder.itemView.getContext().getApplicationContext())
                .load(list.get(position).getProfileImageURL())
                .placeholder(R.drawable.ic_person)
                .timeout(6500)
                .into(holder.profileImage);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onUserClicked.onClick(list.get(position).getId());
                }
            });

        //holder.clickListener(list.get(position).getId());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class UserHolder extends RecyclerView.ViewHolder{

        private CircleImageView profileImage;
        private TextView nameTV, statusTV;
        private RelativeLayout layout;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            nameTV = itemView.findViewById(R.id.nameTv);
            statusTV = itemView.findViewById(R.id.statusTv);
            layout = itemView.findViewById(R.id.relativeLayout);


        }

    }
    public void OnUserClicked(OnUserClicked onUserClicked)
    {
        this.onUserClicked = onUserClicked;
    }
    public interface OnUserClicked
    {
        void onClick(String id);
    }

}
