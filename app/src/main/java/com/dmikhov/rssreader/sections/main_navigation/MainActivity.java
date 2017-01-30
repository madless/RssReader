package com.dmikhov.rssreader.sections.main_navigation;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dmikhov.rssreader.R;
import com.dmikhov.rssreader.models.RssFeed;
import com.dmikhov.rssreader.models.RssMenuItem;
import com.dmikhov.rssreader.mvp.PresenterCache;
import com.dmikhov.rssreader.mvp.PresenterFactory;
import com.dmikhov.rssreader.sections.DialogFragmentAddFeed;
import com.dmikhov.rssreader.sections.browser.InAppBrowserFragment;
import com.dmikhov.rssreader.sections.rss.OnLinkClickListener;
import com.dmikhov.rssreader.sections.rss.RssFragment;
import com.dmikhov.rssreader.utils.Const;

import java.util.List;

import static com.dmikhov.rssreader.utils.Const.TAG;

public class MainActivity extends AppCompatActivity implements OnRSSMenuClickListener, OnLinkClickListener, IMainActivityView {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RecyclerView rvNavigation;
    private TextView tvNoData;
    private ProgressBar progressBar;

    private RssPagerAdapter pagerAdapter;
    private NavigationAdapter navigationAdapter;
    private MainActivityPresenter presenter;

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
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        setupToolbar();
        setupDrawerToggle();
        initPager();
        initNavigation();

        presenter = PresenterCache.get().getPresenter(Const.MAIN_ACTIVITY_PRESENTER, getPresenterFactory());
        presenter.onStart(this);
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

    private void addFragmentToPager(RssFeed feed) {
        if(pagerAdapter.getCount() == 0) {
            showViewPager();
        }
        Bundle bundle = new Bundle();
        bundle.putString(Const.EXTRA_DATA_RSS_URL, feed.getUrl());
        RssFragment fragment = new RssFragment();
        fragment.setArguments(bundle);
        pagerAdapter.addFragment(fragment, feed.getName());
        pagerAdapter.notifyDataSetChanged();
    }

    private void addNavigationItem(String title) {
        navigationAdapter.addItem(new RssMenuItem(title));
    }

    private void removeFragment(int position) {
        Log.d(TAG, "removeFragment: " + position);
        pagerAdapter.removeFragment(position);
        pagerAdapter.notifyDataSetChanged();
        if(pagerAdapter.getCount() == 0) {
            hideViewPager();
        }
    }

    private void removeNavigationItem(int position) {
        navigationAdapter.removeItem(position);
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

    @Override
    protected void onStop() {
        super.onStop();
        if(isFinishing() && !isChangingConfigurations()) {
            PresenterCache.get().removePresenter(Const.MAIN_ACTIVITY_PRESENTER);
        }
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
        drawerToggle.syncState();
    }

    @Override
    public void onAddNewItemClick() {
        DialogFragmentAddFeed dialog = new DialogFragmentAddFeed();
        dialog.setOnAddButtonClickListener(new OnAddRssButtonClickListener() {
            @Override
            public void onAddRssButtonClick(String url) {
                progressBar.setVisibility(View.VISIBLE);
                tvNoData.setVisibility(View.GONE);
                presenter.addRssFeed(url);
                drawerLayout.closeDrawers();
            }
        });
        dialog.show(getSupportFragmentManager(), dialog.getClass().toString());

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
        removeNavigationItem(position);
        presenter.removeRssFeed(position);
    }

    @Override
    public void onLinkClicked(String url) {
        Log.d(TAG, "onLinkClicked: " + url);
        Fragment fragment = new InAppBrowserFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Const.EXTRA_DATA_LINK, url);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(fragment.getClass().toString())
                .commit();
    }

    @Override
    public void onCachedRssFeedsLoaded(List<RssFeed> feeds) {
        if(feeds != null && !feeds.isEmpty()) {
            showViewPager();
            for (int i = 0; i < feeds.size(); i++) {
                addFragmentToPager(feeds.get(i));
                addNavigationItem(feeds.get(i).getName());
            }
        }
    }

    @Override
    public void onFeedAdded(RssFeed feed) {
        if(feed != null) {
            progressBar.setVisibility(View.GONE);
            showViewPager();
            addNavigationItem(feed.getName());
            addFragmentToPager(feed);
        }
    }

    @Override
    public void onRssAlreadyExists() {
        progressBar.setVisibility(View.GONE);
        final AlertDialog.Builder adb = new AlertDialog.Builder(this)
                .setTitle("Error!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setMessage("Rss already exists!");
        adb.show();
    }

    @Override
    public void onRssLoadingError() {
        progressBar.setVisibility(View.GONE);
        final AlertDialog.Builder adb = new AlertDialog.Builder(this)
                .setTitle("Error!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setMessage("Rss loading error. Please check Internet connection and url");
        adb.show();
    }

    public PresenterFactory<MainActivityPresenter> getPresenterFactory() {
        return new PresenterFactory<MainActivityPresenter>() {
            @NonNull
            @Override
            public MainActivityPresenter createPresenter() {
                return new MainActivityPresenter();
            }
        };
    }
}
