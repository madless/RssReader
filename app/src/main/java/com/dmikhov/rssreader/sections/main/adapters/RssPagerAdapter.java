package com.dmikhov.rssreader.sections.main.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dmikhov.rssreader.sections.rss_feed.RssFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmikhov on 27.01.2017.
 */
public class RssPagerAdapter extends FragmentStatePagerAdapter {
    private List<RssFragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    public RssPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(RssFragment fragment, String title) {
        fragments.add(fragment);
        titles.add(title);
    }

    public void removeFragment(int position) {
        RssFragment fragment = fragments.remove(position);
        fragment.destroy();
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
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
