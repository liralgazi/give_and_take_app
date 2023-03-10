package com.example.giveandtake.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.example.giveandtake.R;
import com.example.giveandtake.adapter.HomeAdapter;
import com.example.giveandtake.model.HomeModel;
//import com.example.giveandtake.model.Model;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {
    private RecyclerView recyclerView;
    HomeAdapter homeAdapter;
    private List<HomeModel>data;
    private FirebaseUser user;

    public static int LIST_SIZE =0;
    DocumentReference reference;


    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

//        Model.instance().getAllUsers((userList)->{
//            data= userList;
//        })
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

        //reference = FirebaseFirestore.getInstance().collection("Posts").document(user.getUid());

        data = new ArrayList<>();

        homeAdapter = new HomeAdapter(getContext(),data);
        recyclerView.setAdapter(homeAdapter);

        loadDataFromFireStore();
    }


    private void init(View view){
        Toolbar toolbar = view.findViewById(R.id.profile_toolbar);
        if(getActivity()!=null)
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }
    //TODO: change to room db - there is no timestamp (null)
    private void loadDataFromFireStore(){
        data.add(new HomeModel("shir", null,"","",12,"125", "hello", "hi everyone", "123"));
//
//
//        CollectionReference reference = FirebaseFirestore.getInstance().collection("Users").document(user.getUid()).collection("Post Images");
//        reference.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                if (error != null) {
//                    Log.e("Error", error.getMessage());
//                    return;
//                }
//                if(value ==null)
//                    return;
//                //assert value != null;
//                //list.clear();
//                for (QueryDocumentSnapshot snapshot : value) {
//                    if(!snapshot.exists())
//                        return;
//
//                    HomeModel model = snapshot.toObject(HomeModel.class);
//                    data.add(new HomeModel(
//                            model.getUserName(),
//                            model.getTimestamp(),
//                            model.getProfileImage(),
//                            model.getPostImage(),
//                            model.getLikeCount(),
//                            model.getUid(),
//                            model.getPostText(),
//                            model.getComments(),
//                            model.getPostId(),
//                            model.getDescription()
//                    ));
//                }
//                homeAdapter.notifyDataSetChanged();
//
//                //LIST_SIZE = list.size();
//
//            }
//        });
//
    }
}