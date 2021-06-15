package com.example.fallinlove.Activity.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;



public class HomePageAdapter extends FragmentPagerAdapter {

    int tabCount = 0;

    public HomePageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public HomePageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabCount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new LoveFragment();
            case 1: return new AnniversaryFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
