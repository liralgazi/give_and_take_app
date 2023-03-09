package com.example.giveandtake.fragments;

import android.graphics.drawable.Icon;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.giveandtake.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.protobuf.StringValue;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends Fragment {

    private TextView nameTv, volunteerStatusTv, friendsCountTv, postCountTv , volunteerPlacesTv;
    private CircleImageView profileImage;
    private Button addFriendBtn;
    private RecyclerView recyclerView;
    private FirebaseUser user;

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
        loadBasicData();
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

                    //TODO: to change to ic_home to ic_person  dont forget to change in thr fragment also
                    Glide.with(getContext().getApplicationContext()).load(profileURL).placeholder(R.drawable.ic_home).timeout(6500).into(profileImage);


                }
            }
        });
    }
}