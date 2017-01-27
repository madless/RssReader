package com.dmikhov.rssreader.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by dmikhov on 27.01.2017.
 */
public class RssPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> views = new ArrayList<>();

    public RssPagerAdapter(FragmentManager fm, ArrayList<Fragment> views) {
        super(fm);
        this.views = views;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
