package com.dmikhov.rssreader.sections.main_navigation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmikhov on 27.01.2017.
 */
public class RssPagerAdapter extends FragmentTagBasedPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    public RssPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public RssPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    public void addFragment(Fragment fragment, String title) {
        fragments.add(fragment);
        titles.add(title);
    }

    public void removeFragment(int position) {
        fragments.remove(position);
        titles.remove(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments != null ? fragments.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public String getTag(int position) {
        return titles.get(position);
    }
}
