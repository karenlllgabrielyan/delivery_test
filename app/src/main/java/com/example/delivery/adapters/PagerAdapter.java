package com.example.delivery.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.delivery.fragments.MapFragment;
import com.example.delivery.fragments.StopsFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    int tabCount;


    public PagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                StopsFragment stops = new StopsFragment();
                return stops;
            case 1:
                MapFragment map = new MapFragment();
                return map;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
