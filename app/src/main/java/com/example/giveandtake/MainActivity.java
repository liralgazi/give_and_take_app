package com.example.giveandtake;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.giveandtake.adapter.HomeAdapter;
import com.example.giveandtake.adapter.ViewPagerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private RecyclerView postList;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;

    ViewPagerAdapter viewPagerAdapter;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        addTabs();
    }


    public void init(){
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.main_viewPager);
        tabLayout =  findViewById(R.id.main_tabLayout);
    }


    public void addTabs(){
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home));

        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()){
                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
                    case 1:
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_home);
                    case 2:
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_home);
                    case 3:
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_home);
                    case 4:
                        tabLayout.getTabAt(4).setIcon(R.drawable.ic_home);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
                    case 1:
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_home);
                    case 2:
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_home);
                    case 3:
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_home);
                    case 4:
                        tabLayout.getTabAt(4).setIcon(R.drawable.ic_home);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
                    case 1:
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_home);
                    case 2:
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_home);
                    case 3:
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_home);
                    case 4:
                        tabLayout.getTabAt(4).setIcon(R.drawable.ic_home);
                }
            }
        });
    }

    int fragmentMenuId = 0;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        if (fragmentMenuId != 0){
            menu.removeItem(fragmentMenuId);
        }
        fragmentMenuId = 0;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nav_home:
                break;
            case R.id.nav_profile:
                break;
            case R.id.nav_friends:
                break;
            case R.id.nav_find_friends:
                break;
            case R.id.nav_messages:
                break;
            case R.id.nav_settings:
                break;
            case R.id.nav_logout: {
                FirebaseAuth.getInstance().signOut();
                break;
            }
            case R.id.nav_my_donations:
                break;
        }
        return false;
    }
}