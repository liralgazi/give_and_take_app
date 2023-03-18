package com.example.giveandtake.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.giveandtake.MainActivity;
import com.example.giveandtake.R;
import com.example.giveandtake.ReplacerActivity;
import com.example.giveandtake.fragments.Profile;
import com.example.giveandtake.fragments.auth.CreateAccountFragment;
import com.example.giveandtake.fragments.auth.LoginFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.HashMap;
import java.util.Map;

public class ProfileEdit extends Fragment {


    EditText ageET, addressET, workPlaceET, volunteerET, nameET, usernameET;
    Button updateBtn;
    TextView update;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;

    DocumentReference documentReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String currentId = user.getUid();
    private FirebaseAuth auth;
    ProgressBar progressBar;

    // creating a variable for
    // our Firebase Database.

    final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    // creating a variable for our
    // Database Reference for Firebase.
    //DatabaseReference ref = database.getReference("Users/");

    public ProfileEdit()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        clickListener();
    }

    private void init(View view) {
        Toolbar toolbar = view.findViewById(R.id.profile_toolbar_edit);
        assert getActivity()!=null;
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        ageET = view.findViewById(R.id.birthdayTv_edit);
        addressET = view.findViewById(R.id.addressTv_edit);
        workPlaceET = view.findViewById(R.id.workTv_edit);
        volunteerET = view.findViewById(R.id.profile_volunteerTv_edit);
        updateBtn = view.findViewById(R.id.profile_saveBtn);
        auth = FirebaseAuth.getInstance();
        //nameET = view.findViewById(R.id.nameTv_edit);
        progressBar = view.findViewById(R.id.update_progressBar);

        //username
        //usernameET = view.findViewById(R.id.nameTv);

    }





    private void clickListener()
    {
            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String age = ageET.getText().toString();
                    String work = workPlaceET.getText().toString();
                    String address = addressET.getText().toString();
                    String volunteer = volunteerET.getText().toString();
                    //String name = nameET.getText().toString();

                    progressBar.setVisibility(View.VISIBLE);
                    updateAccount(age, work, address, volunteer);


                }
            });


    }

    private void updateAccount( String age, String work, String address, String volunteer)
    {
        auth.updateCurrentUser(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(getContext(), "Profile Updated successfully", Toast.LENGTH_SHORT).show();
                }
                uploadUpdatedUser(user,age, work, address, volunteer);

            }
        });
    }

    private void uploadUpdatedUser(FirebaseUser user,String age,String work, String address, String volunteer ) {
        Map<String, Object> map = new HashMap<>();
        //CreateAccountFragment ca = new CreateAccountFragment();
        //Map<String, Object> mapCA =  ca.getMap();


        if(!age.equals(""))
            map.put("age", age);
        if(!work.equals(""))
            map.put("work", work);
        if(!address.equals(""))
            map.put("address", address);
        if(!volunteer.equals(""))
            map.put("volunteerStatus", volunteer);

        FirebaseFirestore.getInstance().collection("User").document(user.getUid()).update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    assert getActivity() != null;
                    progressBar.setVisibility(View.GONE);

                    sendUserToMainActivity();
                } else {
                    progressBar.setVisibility(View.GONE);
                    String exception = task.getException().getMessage();
                    Toast.makeText(getContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendUserToMainActivity() {
        if(getActivity() == null){
            return;
        }
        progressBar.setVisibility(View.GONE);
        startActivity(new Intent(getContext().getApplicationContext(), MainActivity.class));
        getActivity().finish();
    }


}
