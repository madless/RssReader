package com.dmikhov.rssreader.sections.rss_feed;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dmikhov.rssreader.R;
import com.dmikhov.rssreader.entities.RssItem;
import com.dmikhov.rssreader.mvp.PresenterCache;
import com.dmikhov.rssreader.mvp.PresenterFactory;
import com.dmikhov.rssreader.sections.rss_feed.abs.IRssView;
import com.dmikhov.rssreader.sections.rss_feed.adapters.RssAdapter;
import com.dmikhov.rssreader.sections.rss_feed.listeners.OnLinkClickListener;
import com.dmikhov.rssreader.sections.rss_feed.listeners.OnRssItemClickListener;
import com.dmikhov.rssreader.utils.Const;

import java.util.List;

import static com.dmikhov.rssreader.utils.Const.TAG;

/**
 * Created by dmikhov on 27.01.2017.
 */
public class RssFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnRssItemClickListener, IRssView {

    private ProgressBar progressBar;
    private RecyclerView rvRss;
    private SwipeRefreshLayout swipeRefreshLayout;

    private RssAdapter adapter;
    private RssFragmentPresenter presenter;
    private OnLinkClickListener onLinkClickListener;

    private String rssUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rss, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        rvRss = (RecyclerView) view.findViewById(R.id.rvRss);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            onLinkClickListener = (OnLinkClickListener) getActivity();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        Bundle bundle = getArguments();
        if(bundle != null) {
            rssUrl = bundle.getString(Const.EXTRA_DATA_RSS_URL);
        }
        swipeRefreshLayout.setOnRefreshListener(this);
        initRecyclerView();
        initPresenter(rssUrl);
    }

    private void initPresenter(String url) {
        presenter = PresenterCache.get().getPresenter(Const.RSS_FRAGMENT_PRESENTER + url, getPresenterFactory());
        presenter.setView(this);
        presenter.onStart(url);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new RssAdapter(onLinkClickListener);
        rvRss.setLayoutManager(layoutManager);
        rvRss.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh");
        presenter.updateData();
    }

    @Override
    public void onReadMoreClicked(int pos) {
        Log.d(TAG, "onReadMoreClicked: ");
        Toast.makeText(getContext(), "On read more clicked: " + pos, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRssLoadingStarted() {
        Log.d(TAG, "onRssLoadingStarted: ");
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRssDataLoaded(List<RssItem> items) {
        Log.d(TAG, "onRssDataLoaded items: " + items);
        adapter.setItems(items);
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onRssRefreshingStarted() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onLoadingError() {
        final AlertDialog.Builder adb = new AlertDialog.Builder(getContext())
                .setTitle("Error!").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setMessage("Rss loading error. Please check Internet connection and url");
        adb.show();
    }

    @Override
    public void onRssDataRefreshed(List<RssItem> items) {
        adapter.setItems(items);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_sort_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sort_date_asc: {
                presenter.sortByDateAsc();
                break;
            }
            case R.id.menu_sort_date_desc: {
                presenter.sortByDateDesc();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void destroy(){
        PresenterCache.get().removePresenter(Const.RSS_FRAGMENT_PRESENTER + rssUrl);
    }

    public PresenterFactory<RssFragmentPresenter> getPresenterFactory() {
        return new PresenterFactory<RssFragmentPresenter>() {
            @NonNull
            @Override
            public RssFragmentPresenter createPresenter() {
                return new RssFragmentPresenter();
            }
        };
    }

    public String getRssUrl() {
        return rssUrl;
    }
}
