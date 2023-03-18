package com.example.giveandtake.fragments;

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
import com.example.giveandtake.adapter.UserAdapter;
import com.example.giveandtake.model.User;
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

public class Search extends Fragment {


    SearchView searchView;
    RecyclerView recyclerView;
    UserAdapter adapter;
    private List<User> list;
    CollectionReference reference;

    onDataPass onDataPass;

    public interface onDataPass{

        void onChange(String id);
    }
    public Search() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onDataPass = (onDataPass)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        reference = FirebaseFirestore.getInstance().collection("User");
        loadUserDate();
        searchUser();
        clickListener();
    }

    private void clickListener()
    {
        adapter.OnUserClicked(new UserAdapter.OnUserClicked() {
            @Override
            public void onClick(String id) {
                onDataPass.onChange(id);
            }
        });
    }

    private void loadUserDate()
    {
        reference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null)
                    return;

                if (value == null)
                    return;
                list.clear();
                for (QueryDocumentSnapshot snapshot : value)
                {
                    User user = snapshot.toObject(User.class);
                    list.add(user);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }


    private void searchUser()
    {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                reference.orderBy("search").startAt(query).endAt(query+"\uf8ff")
                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful())
                                {
                                    list.clear();
                                    for (DocumentSnapshot snapshot: task.getResult())
                                    {
                                        if(!snapshot.exists())
                                            return;

                                        User user = snapshot.toObject(User.class);
                                        list.add(user);
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals(""))
                    loadUserDate();
                return false;
            }
        });
    }
    private void init(View view)
    {
        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<User>();
        adapter = new UserAdapter(list);
        recyclerView.setAdapter(adapter);

    }
}