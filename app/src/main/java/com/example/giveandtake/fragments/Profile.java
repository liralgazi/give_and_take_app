package com.example.giveandtake.fragments;

import android.graphics.drawable.Icon;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.giveandtake.R;
import com.example.giveandtake.model.PostImageActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.protobuf.StringValue;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends Fragment {

    private TextView nameTv, volunteerStatusTv, friendsCountTv, postCountTv , volunteerPlacesTv;
    private ImageButton editProfileBtn;
    private CircleImageView profileImage;
    private Button addFriendBtn;
    private RecyclerView recyclerView;
    private FirebaseUser user;

    String uid;
    //private RelativeLayout addFriendLayout;
    private LinearLayout countLayout;
    FirestoreRecyclerAdapter<PostImageActivity,PostImageHolder> adapter;
    boolean isMyProfile = true;
    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        if(isMyProfile)
        {
            addFriendBtn.setVisibility(view.GONE);
            countLayout.setVisibility(view.VISIBLE);
        }
        else
        {
            addFriendBtn.setVisibility(view.VISIBLE);
            countLayout.setVisibility(view.GONE);
        }
        loadBasicData();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3 ));

        loadPostImages();

        recyclerView.setAdapter(adapter);
    }

    private void init(View view){
        Toolbar toolbar = view.findViewById(R.id.profile_toolbar);
        assert getActivity()!=null;
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        nameTv = view.findViewById(R.id.profile_nameTv);
        volunteerStatusTv = view.findViewById(R.id.profile_volunteerTv);
        friendsCountTv = view.findViewById(R.id.profile_nameTv);
        postCountTv = view.findViewById(R.id.profile_nameTv);
        profileImage = view.findViewById(R.id.profile_image);
        addFriendBtn = view.findViewById(R.id.profile_addFriendBtn);
        recyclerView = view.findViewById(R.id.profile_recycle);
        volunteerPlacesTv = view.findViewById(R.id.profile_volunteer_placesTv);
        editProfileBtn = view.findViewById(R.id.profile_editImage);

        countLayout = view.findViewById(R.id.addFriend_layout);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }

    private void loadBasicData(){
        DocumentReference userRef = FirebaseFirestore.getInstance().collection("User").document(user.getUid());
        userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null)
                    return;
                assert value!=null;
                if(value.exists()){
                    String name = value.getString("name");
                    String volunteerStatus = value.getString("volunteerStatus");
                    int friends = value.getLong("friends").intValue();
                    int volunteeringPlaces = value.getLong("places").intValue();

                    String profileURL = value.getString("profileImage");


                    nameTv.setText(name);
                    volunteerStatusTv.setText(volunteerStatus);
                    friendsCountTv.setText(String.valueOf(friends));
                    volunteerPlacesTv.setText(String.valueOf(volunteeringPlaces));

                    Glide.with(getContext().getApplicationContext()).load(profileURL).placeholder(R.drawable.ic_person).timeout(6500).into(profileImage);


                }
            }
        });
    }

    private void loadPostImages(){

        if (isMyProfile) {
            uid = user.getUid();
        }else
        {

        }
        DocumentReference reference = FirebaseFirestore.getInstance().collection("Users").document(uid);
        Query query = reference.collection("Images");

        FirestoreRecyclerOptions<PostImageActivity> options = new FirestoreRecyclerOptions.Builder<PostImageActivity>().setQuery(query,PostImageActivity.class).build();
        adapter = new FirestoreRecyclerAdapter<PostImageActivity, PostImageHolder>(options) {
            @NonNull
            @Override
            public PostImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.profile_image_items,parent,false);
                return new PostImageHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull PostImageHolder holder, int position, @NonNull PostImageActivity model) {

                Glide.with(holder.itemView.getContext().getApplicationContext())
                        .load(model.getImageUrl())
                        .timeout(6500)
                        .into(holder.imageView);
            }
        };


    }

    private class PostImageHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;



        public PostImageHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}