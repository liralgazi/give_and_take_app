package com.example.giveandtake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.giveandtake.adapter.ViewPagerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.function.ToLongBiFunction;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private RecyclerView postList;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        addTabs();

    }

    public void init(){

        viewPager = findViewById(R.id.viewPager);
        tabLayout =  findViewById(R.id.main_tabLayout);

        mToolbar =  findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Home");


        //defining the variables (drawable side menu)
        drawerLayout = findViewById(R.id.drawable_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView=  findViewById(R.id.navigation_view);
        //initiating the navigation header inside the layout
        View navView= navigationView.inflateHeaderView(R.layout.navigation_header);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                UserMenuSelector(item);

                return false;
            }
        });
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
        //if the user clicks on the toggle tool bar
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void UserMenuSelector(MenuItem item) {
        //switch-case for every user option from the menu
        switch (item.getItemId())
        {
            case R.id.nav_home:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_profile:
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_friends:
                Toast.makeText(this, "Friends List", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_find_friends:
                Toast.makeText(this, "Find Friends", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_messages:
                Toast.makeText(this, "Messages", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:{
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
            }
                break;
            case R.id.nav_my_donations:
                Toast.makeText(this, "My Donations", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}