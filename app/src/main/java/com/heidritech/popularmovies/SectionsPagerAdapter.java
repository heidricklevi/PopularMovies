package com.heidritech.popularmovies;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    android.support.v4.app.Fragment fragment = null;
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                fragment = new FavoritesFragment();
                break;
            case 1:
                fragment = new gridFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {


        switch (position) {
            case 0:
                return ("Favorites");
            case 1:
                return ("Main");

        }

        return super.getPageTitle(position);


    }
}
