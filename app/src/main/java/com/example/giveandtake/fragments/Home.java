package com.example.giveandtake.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.giveandtake.R;
import com.example.giveandtake.adapter.HomeAdapter;
import com.example.giveandtake.model.HomeModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {
    private RecyclerView recyclerView;
    HomeAdapter homeAdapter;
    private List<HomeModel>list;
    private FirebaseUser user;
    DocumentReference reference;


    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

        //reference = FirebaseFirestore.getInstance().collection("Posts").document(user.getUid());

        list = new ArrayList<>();

        homeAdapter = new HomeAdapter(getContext(),list);
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

    private void loadDataFromFireStore(){
        list.add(new HomeModel("shir","08/03/2023","","",12,"123456"));
        list.add(new HomeModel("shir","08/03/2023","","",12,"125896"));
        list.add(new HomeModel("shir","08/03/2023","","",12,"125874"));
        list.add(new HomeModel("shir","08/03/2023","","",12,"582163"));
        homeAdapter.notifyDataSetChanged();
    }
}