package com.example.giveandtake.fragments.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giveandtake.MainActivity;
import com.example.giveandtake.R;
import com.example.giveandtake.ReplacerActivity;
import com.example.giveandtake.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateAccountFragment extends Fragment {

    private EditText nameEt, emailEt, passwordEt, confirmPasswordEt, confirmEmailEt;
    //private EditText addressEt, ageEt, volunteerEt, workEt;
    private ProgressBar progressBar;
    private TextView loginTv;
    private Button signUpBtn;
    private FirebaseAuth auth;
    HashMap<String, Object> map = new HashMap<>();


    public static final String EMAIL_REGEX = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public CreateAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        clickListener();
    }

    private void init(View view) {
        nameEt = view.findViewById(R.id.signup_name);
        emailEt = view.findViewById(R.id.signup_email);
        confirmEmailEt = view.findViewById(R.id.signup_confirm_email);
        passwordEt = view.findViewById(R.id.signup_password);
        confirmPasswordEt = view.findViewById(R.id.signup_confirm_password);
        loginTv = view.findViewById(R.id.signup_tv);
        signUpBtn = view.findViewById(R.id.login_sign_up_btn);
        progressBar = view.findViewById(R.id.singup_progressBar);


        auth = FirebaseAuth.getInstance();
    }

    private void clickListener() {
        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ReplacerActivity) getActivity()).setFragment(new LoginFragment());
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEt.getText().toString();
                String email = emailEt.getText().toString();
                String confirmEmail = confirmEmailEt.getText().toString();
                String password = passwordEt.getText().toString();
                String confirmPassword = confirmPasswordEt.getText().toString();


                if (name.isEmpty() || name.equals(" ")) {
                    nameEt.setError("Please input valid name");
                    return;
                }
                if (email.isEmpty() || !email.matches(EMAIL_REGEX)) {
                    emailEt.setError("Please input valid email");
                    return;
                }
                if (!email.equals(confirmEmail)) {
                    confirmEmailEt.setError("Email not match");
                    return;
                }                if (password.isEmpty() || password.length() < 6) {
                    passwordEt.setError("Please input valid password");
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    confirmPasswordEt.setError("Password not match");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                createAccount(name, email, password);
            }
        });
    }

    public HashMap<String, Object> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Object> map) {
        this.map = map;
    }

    private void createAccount(String name, String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Email verification link send", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    uploadUser(user, name, email, "","","","");
                } else {
                    progressBar.setVisibility(View.GONE);
                    String exception = task.getException().getMessage();
                    Toast.makeText(getContext(), "Error: " + exception, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void uploadUser(FirebaseUser user, String name, String email, String age, String work, String address, String volunteer) {

        map.put("name", name);
        map.put("email", email);
        map.put("profileImage", "");
        map.put("uid", user.getUid());
        map.put("volunteerStatus", "");
        map.put("work","");
        map.put("age","");
        map.put("address","");
        FirebaseFirestore.getInstance().collection("User").document(user.getUid()).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    assert getActivity() != null;
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                    getActivity().finish();
                } else {
                    progressBar.setVisibility(View.GONE);
                    String exception = task.getException().getMessage();
                    Toast.makeText(getContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}