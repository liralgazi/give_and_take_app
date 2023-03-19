package com.example.giveandtake.fragments.profile;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.giveandtake.R;
import com.example.giveandtake.databinding.FragmentProfileBinding;
import com.example.giveandtake.fragments.home.HomeListFragmentViewModel;
import com.example.giveandtake.fragments.home.HomeRecyclerAdapter;
import com.example.giveandtake.model.Post;
import com.example.giveandtake.model.PostModel;
import com.example.giveandtake.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends Fragment {

    private TextView volunteerStatusTv;
    private TextView ageTv, workTv, addressTv, nameTv;
    private CircleImageView profileImageView;
    private FirebaseUser fireUser;
    String profileUserId;
    FragmentProfileBinding binding;
    HomeRecyclerAdapter adapter;
    HomeListFragmentViewModel viewModel;

    public Profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        init(view);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        fireUser = auth.getCurrentUser();
        profileUserId = fireUser.getUid();
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("User").document(profileUserId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                nameTv.setText(user.getName());
                volunteerStatusTv.setText("Volunteering: "+ user.getVolunteerStatus());
                //profileImage.setImageURI(Uri.parse(documentSnapshot.getString("profileImageURL")));
                if(documentSnapshot.getString("workAt") == null)
                    workTv.setText("Work At: ");
                else
                     workTv.setText("Work At: "+user.getWorkAt());
                addressTv.setText("Address: "+user.getAddress());
                ageTv.setText("Age: " +user.getAge());
                if (user.getProfileImage() != "" && user.getProfileImage().length() > 5) {
                    Picasso.get().load(user.getProfileImage()).placeholder(R.drawable.ic_person).into(profileImageView);
                }else{
                    profileImageView.setImageResource(R.drawable.ic_person);
                }
            }
        });
        View editProfile = view.findViewById(R.id.editProfileBtn);
        NavDirections action = ProfileDirections.actionProfileFragmentToEditProfileFragment();
        editProfile.setOnClickListener(Navigation.createNavigateOnClickListener(action));

        binding.profileRecycle.setHasFixedSize(true);
        binding.profileRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HomeRecyclerAdapter(getLayoutInflater(),viewModel.getData().getValue());
        binding.profileRecycle.setAdapter(adapter);

        List<Post> userPosts = new LinkedList<>();
        viewModel.getData().observe(getViewLifecycleOwner(),list->{
            if(list.size() != 0){
                for(int i=list.size()-1;i>=0;i--)
                {
                    if(list.get(i).userId.equals(profileUserId))
                        userPosts.add(list.get(i));
                }
                adapter.setData(userPosts);
            }
        });

        return view;
    }

    public void init(View view){
        nameTv = view.findViewById(R.id.profile_nameTv);
        volunteerStatusTv = view.findViewById(R.id.profile_volunteerTv);
        profileImageView = view.findViewById(R.id.profile_profileImage);
        workTv = view.findViewById(R.id.workTv);
        addressTv = view.findViewById(R.id.addressTv);
        ageTv = view.findViewById(R.id.birthdayTv);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(HomeListFragmentViewModel.class);
    }

    void reloadData(){
        PostModel.instance().refreshAllPosts();
    }
}