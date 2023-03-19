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
import com.example.giveandtake.fragments.PlaceListFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceHolder> {

    private List<PlaceListFragment> list;
    OnPlaceClicked onPlaceClicked;
    FirebaseUser place = FirebaseAuth.getInstance().getCurrentUser();

    public PlaceAdapter(List<PlaceListFragment> list){this.list = list;}

    @NonNull
    @Override
    public PlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_items, parent, false);
        return new PlaceHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PlaceHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.layout.setVisibility(View.VISIBLE);
        holder.placeNameTV.setText(list.get(position).getName());
        //holder.descriptionTv.setText(list.get(position).getDescription());
        Glide.with(holder.itemView.getContext().getApplicationContext())
                .load(list.get(position).getImageURL())
                .placeholder(R.drawable.ic_person)
                .timeout(6500)
                .into(holder.placeImage);

/*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlaceClicked.onClick(list.get(position).getUid());
            }
        });

        //holder.clickListener(list.get(position).getId());


 */
    }
    @Override
    public int getItemCount() {
        return list.size();
    }


    static class PlaceHolder extends RecyclerView.ViewHolder{

        private CircleImageView placeImage;
        private TextView placeNameTV;
        private RelativeLayout layout;



        public PlaceHolder(@NonNull View itemView) {
            super(itemView);
            placeImage = itemView.findViewById(R.id.placeImage);
            placeNameTV = itemView.findViewById(R.id.place_nameTv);
            layout = itemView.findViewById(R.id.relativeLayout_place);
        }





    }




    public void OnPlaceClicked(OnPlaceClicked onPlaceClicked){this.onPlaceClicked = onPlaceClicked;}
    public interface OnPlaceClicked
    {
        void onClick(String id);
    }
}
