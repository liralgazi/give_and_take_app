package com.example.giveandtake.model;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giveandtake.R;
import com.example.giveandtake.adapter.PlaceAdapter;
import com.example.giveandtake.adapter.UserAdapter;
import com.example.giveandtake.fragments.Search;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PlaceSearch extends Fragment {

    SearchView searchView;
    RecyclerView recyclerView;
    PlaceAdapter adapter;
    private List<Place> list;
    CollectionReference reference;

    Search.onDataPass onDataPass;

    public PlaceSearch(){}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onDataPass = (Search.onDataPass)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_place, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        reference = FirebaseFirestore.getInstance().collection("Places");
        loadPlaceData();
        searchPlace();
        //clickListener();


    }
/*
    private void clickListener()
    {
        adapter.OnPlaceClicked(new UserAdapter.OnUserClicked() {
            @Override
            public void onClick(String id) {
                onDataPass.onChange(id);
            }
        });
    }

 */


    private void searchPlace()
    {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                reference.orderBy("name").startAt(s).endAt(s+"\uf8ff")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful())
                                {
                                    for (DocumentSnapshot snapshot: task.getResult())
                                    {
                                            if (!snapshot.exists())
                                                return;
                                        Place place = snapshot.toObject(Place.class);
                                        list.add(place);
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                        return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
    private void loadPlaceData(){
        reference = FirebaseFirestore.getInstance().collection("Places");
        reference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                list.clear();
                for (QueryDocumentSnapshot snapshot: value)
                {
                    Place place = snapshot.toObject(Place.class);
                    list.add(place);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }


    private void init(View view)
    {
        searchView = view.findViewById(R.id.searchViewPlace);
        recyclerView = view.findViewById(R.id.recyclerViewPlace);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<Place>();
        adapter = new PlaceAdapter(list);
        recyclerView.setAdapter(adapter);
    }


}
