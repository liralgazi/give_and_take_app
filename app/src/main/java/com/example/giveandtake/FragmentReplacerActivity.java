package com.example.giveandtake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.giveandtake.fragments.CreateAccountFragment;
import com.example.giveandtake.fragments.LoginFragment;
//import com.example.giveandtake.fragments.LoginFragment;

public class FragmentReplacerActivity extends AppCompatActivity {

    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_replacer);

        frameLayout = findViewById(R.id.frameLayout);

        setFragment(new LoginFragment());
    }

    public void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        fragmentTransaction.replace(frameLayout.getId(), fragment, null);
//        if(fragment instanceof CreateAccountFragment){
//           fragmentTransaction.addToBackStack(null);
//          }
                fragmentTransaction.commit();
    }
}