package com.example.giveandtake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.NavigationUI;
;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity{


    NavController navController;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.main_navhost);
        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this,navController);

        BottomNavigationView navView = findViewById(R.id.main_bottomNavigationView);
        NavigationUI.setupWithNavController(navView,navController);
    }


    public void init(){
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    int fragmentMenuId = 0;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            navController.popBackStack();
        }else{
            if(item.getItemId() == R.id.nav_logout){
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent i = new Intent(getApplicationContext(),SplashActivity.class);
                startActivity(i);
            }
           // else if (item.getItemId() == R.id.nav_edit_profile){
             //       Intent i = new Intent(getApplicationContext(), ProfileEdit.class);
               //     startActivity(i);

       //     }
        else{
                fragmentMenuId = item.getItemId();
                return NavigationUI.onNavDestinationSelected(item,navController);
            }

        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        //if (viewPager.setCurrentItem()==4)
            // viewPager.setCurrentItem(0);
        //else
        IS_SEARCHED_USER  =false;
        super.onBackPressed();
    }

    public static String USER_ID;
    public static boolean IS_SEARCHED_USER=false;

}