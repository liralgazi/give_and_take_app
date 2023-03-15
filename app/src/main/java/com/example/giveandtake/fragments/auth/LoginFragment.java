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
import com.example.giveandtake.fragments.auth.CreateAccountFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    private EditText emailEt, passwordEt;
    private TextView signUpTv;
    private Button loginBtn, googleBtn;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    public static final String EMAIL_REGEX = "^(.+)@(.+)$";

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        clickListener();
    }

    private void init(View view) {
        emailEt = view.findViewById(R.id.login_email);
        passwordEt = view.findViewById(R.id.login_password);
        progressBar = view.findViewById(R.id.login_progressBar);
        loginBtn = view.findViewById(R.id.login_signin_btn);
        signUpTv = view.findViewById(R.id.signup_tv);
        auth = FirebaseAuth.getInstance();
    }

    private void clickListener() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();

                if (email.isEmpty() || !email.matches(EMAIL_REGEX)) {
                    emailEt.setError("Please input valid email");
                    return;
                }
                if (password.isEmpty() || password.length() < 6) {
                    passwordEt.setError("input 6 digit valid password");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            if (!user.isEmailVerified()) {
                                Toast.makeText(getContext(), "Please verify your email", Toast.LENGTH_SHORT).show();
                            }
                            sendUserToMainActivity();


                        } else {
                            String exception = task.getException().getMessage();
                            Toast.makeText(getContext(), "Error: " + exception, Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        signUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ReplacerActivity) getActivity()).setFragment(new CreateAccountFragment());
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


