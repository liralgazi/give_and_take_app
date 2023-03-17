package com.example.giveandtake.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.giveandtake.MyApplication;
import com.example.giveandtake.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Places extends Fragment {

    private TextView namePlace1,namePlace2,namePlace3;
    private CircleImageView  placeImg1,placeImg2,placeImg3;

    CollectionReference ref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_places, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);


        ref = FirebaseFirestore.getInstance().collection("Places");
        loadBasicData();
    }

    private void init(View view) {
        Toolbar toolbar = view.findViewById(R.id.profile_toolbar);
        assert getActivity() != null;
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        namePlace1 = view.findViewById(R.id.place_name_TV1);
        namePlace2 = view.findViewById(R.id.place_name_TV2);
        namePlace3 = view.findViewById(R.id.place_name_TV3);



    }
    private void loadBasicData(){
        DocumentReference reference1 = FirebaseFirestore.getInstance()
                .collection("Places").document("I0C1qpTHqk3XuUkEry4S");

        reference1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists())
                {
                    String name= value.getString("name");
                    namePlace1.setText(name);
                }
            }
        });

    }





    }
