package com.example.giveandtake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.NavigationUI;
;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.giveandtake.fragments.Search;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity implements Search.onDataPass {


    NavController navController;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    TextView logoutTv;
    onUserProfileUid onUserProfileUid;



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

       // clickListener();
    }

//    public void clickListener(){
//        logoutTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                finish();
//                Intent i = new Intent(getApplicationContext(),SplashActivity.class);
//                startActivity(i);
//            }
//        });
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onBackPressed() {
        //if (viewPager.setCurrentItem()==4)
            // viewPager.setCurrentItem(0);
        //else
        super.onBackPressed();
    }

    @Override
    public void onChange(String id) {
        onUserProfileUid.onReceiveUserUid(id);

    }

    public interface onUserProfileUid{
        void onReceiveUserUid(String id);

    }
    public void OnUserProfileUid(onUserProfileUid onUserProfileUid)
    {
        this.onUserProfileUid = onUserProfileUid;
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