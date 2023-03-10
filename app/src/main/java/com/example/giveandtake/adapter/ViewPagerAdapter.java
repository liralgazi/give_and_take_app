package com.example.giveandtake.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.giveandtake.R;
import com.example.giveandtake.fragments.Add;
import com.example.giveandtake.fragments.Home;
import com.example.giveandtake.fragments.Notification;
import com.example.giveandtake.fragments.Profile;
import com.example.giveandtake.fragments.Search;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fm, int numOfTabs) {
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new Home();

            case 1:
                return new Search();

            case 2:
                return new Add();

            case 3:
                return new Notification();

            case 4:
                return new Profile();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return 0;
    }
}
