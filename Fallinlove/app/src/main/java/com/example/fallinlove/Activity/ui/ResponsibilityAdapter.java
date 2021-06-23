package com.example.fallinlove.Activity.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ResponsibilityAdapter extends FragmentPagerAdapter {

    int tabCount = 0;

    public ResponsibilityAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public ResponsibilityAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabCount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new DailyFragment();
            case 1: return new ResponsibilityFragment();
            case 2: return new AddResponsibilityFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

}
