package com.example.giveandtake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.giveandtake.fragments.Search;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements Search.onDataPass {


    NavController navController;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    ImageButton logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
       // addTabs();

        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.main_navhost);
        navController = navHostFragment.getNavController();
        //NavigationUI.setupActionBarWithNavController(this,navController);

        BottomNavigationView navView = findViewById(R.id.main_bottomNavigationView);
        NavigationUI.setupWithNavController(navView,navController);
    }


    public void init(){
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        logoutBtn = findViewById(R.id.main_logout);
        clickListener();
    }

    public void clickListener(){
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent i = new Intent(getApplicationContext(),SplashActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onChange(int pos) {
       // viewPager.setCurrentItem(pos);
    }

    @Override
    public void onBackPressed() {
        //if (viewPager.setCurrentItem()==4)
            // viewPager.setCurrentItem(0);
        //else
        super.onBackPressed();
    }

    //    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId())
//        {
//            case R.id.nav_home:
//                break;
//            case R.id.nav_profile:
//                break;
//            case R.id.nav_friends:
//                break;
//            case R.id.nav_find_friends:
//                break;
//            case R.id.nav_messages:
//                break;
//            case R.id.nav_settings:
//                break;
//            case R.id.nav_logout: {
//                FirebaseAuth.getInstance().signOut();
//                finish();
//                Intent i = new Intent(getApplicationContext(),SplashActivity.class);
//                startActivity(i);
//                break;
//            }
//            case R.id.nav_my_donations:
//                break;
//        }
//        return false;
//    }

}