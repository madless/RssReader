package com.dmikhov.rssreader.sections.browser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.dmikhov.rssreader.R;
import com.dmikhov.rssreader.utils.Const;

/**
 * Created by dmikhov on 27.01.2017.
 */
public class InAppBrowserFragment extends Fragment {
    private WebView webView;
    private ProgressBar progressBar;

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inapp_browser, container, false);
        webView = (WebView) view.findViewById(R.id.webView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String url = bundle.getString(Const.EXTRA_DATA_LINK);
            if (!TextUtils.isEmpty(url)) {
                progressBar.setVisibility(View.VISIBLE);
                webView.loadUrl(url);
                webView.setWebViewClient(new WebViewClient() {
                    public void onPageFinished(WebView view, String url) {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }
    }
}