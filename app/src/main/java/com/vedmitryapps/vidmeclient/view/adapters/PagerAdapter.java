package com.vedmitryapps.vidmeclient.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vedmitryapps.vidmeclient.view.fragments.FeaturedVideosFragment;
import com.vedmitryapps.vidmeclient.view.fragments.FeedVideosFragment;
import com.vedmitryapps.vidmeclient.view.fragments.NewVideosFragment;


public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FeaturedVideosFragment();
            case 1:
                return new NewVideosFragment();
            case 2:
                return new FeedVideosFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "FEATURED";
            case 1:
                return "NEW";
            case 2:
                return "FEED";
        }
        return null;
    }
}