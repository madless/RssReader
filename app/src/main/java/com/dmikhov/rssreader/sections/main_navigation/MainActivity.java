package com.dmikhov.rssreader.sections.main_navigation;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dmikhov.rssreader.R;
import com.dmikhov.rssreader.models.RssMenuItem;
import com.dmikhov.rssreader.sections.rss.RssFragment;

import java.util.ArrayList;
import java.util.List;

import static com.dmikhov.rssreader.utils.Const.TAG;

public class MainActivity extends AppCompatActivity implements OnRSSMenuClickListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RecyclerView rvNavigation;
    private TextView tvNoData;

    private RssPagerAdapter pagerAdapter;
    private NavigationAdapter navigationAdapter;
    private int counter;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        rvNavigation = (RecyclerView) findViewById(R.id.rvNavigation);
        tvNoData = (TextView) findViewById(R.id.tvNoData);

        setupToolbar();
        setupDrawerToggle();
        initPager();
        initNavigation();
    }

    private void initNavigation() {
        navigationAdapter = new NavigationAdapter(this, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvNavigation.setLayoutManager(layoutManager);
        rvNavigation.setAdapter(navigationAdapter);
    }

    private void initPager() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        pagerAdapter = new RssPagerAdapter(fragmentManager);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        if(pagerAdapter.getCount() == 0) {
            hideViewPager();
        }
    }

    private void addFragmentToPager() {
        if(pagerAdapter.getCount() == 0) {
            showViewPager();
        }
        Fragment fragment = new RssFragment();
        String title = getNextTitle();
        pagerAdapter.addFragment(fragment, title);
        pagerAdapter.notifyDataSetChanged();
        counter++;
    }

    private void removeFragment(int position) {
        Log.d(TAG, "removeFragment: " + position);
        pagerAdapter.removeFragment(position);
        pagerAdapter.notifyDataSetChanged();
        if(pagerAdapter.getCount() == 0) {
            hideViewPager();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    void hideViewPager() {
        viewPager.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
        tvNoData.setVisibility(View.VISIBLE);
    }

    void showViewPager() {
        viewPager.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);
        tvNoData.setVisibility(View.GONE);
    }

    void setupToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    void setupDrawerToggle(){
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        drawerToggle.syncState();
    }

    private String getNextTitle() {
        return "Title: " + counter;
    }

    @Override
    public void onAddNewItemClick() {
        navigationAdapter.addItem(new RssMenuItem(getNextTitle()));
        addFragmentToPager();
        drawerLayout.closeDrawers();
    }

    @Override
    public void onRemoveItemClick(int position) {
        if(viewPager.getCurrentItem() == position) {
            if(position == 0) {
                //viewPager.setCurrentItem(position + 1);
            } else {
                viewPager.setCurrentItem(position - 1);
            }
        }
        removeFragment(position);
        navigationAdapter.removeItem(position);
    }
}
