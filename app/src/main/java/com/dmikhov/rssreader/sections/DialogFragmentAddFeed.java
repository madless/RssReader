package com.dmikhov.rssreader.sections;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dmikhov.rssreader.R;
import com.dmikhov.rssreader.sections.main_navigation.OnAddRssButtonClickListener;

/**
 * Created by madless on 28.01.2017.
 */
public class DialogFragmentAddFeed extends DialogFragment implements View.OnClickListener {
    private Button buttonDialogAdd;
    private Button buttonDialogCancel;
    private EditText etRSSFeedLink;
    private TextView tvDialogTitle;

    private OnAddRssButtonClickListener onAddButtonClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_add_rss_feed, container, false);
        buttonDialogAdd = (Button) view.findViewById(R.id.buttonDialogAdd);
        buttonDialogCancel = (Button) view.findViewById(R.id.buttonDialogCancel);
        etRSSFeedLink = (EditText) view.findViewById(R.id.etRSSFeedLink);
        tvDialogTitle = (TextView) view.findViewById(R.id.tvDialogTitle);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonDialogAdd.setOnClickListener(this);
        buttonDialogCancel.setOnClickListener(this);
    }

    public void setOnAddButtonClickListener(OnAddRssButtonClickListener onAddButtonClickListener) {
        this.onAddButtonClickListener = onAddButtonClickListener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonDialogAdd: {
                dismiss();
                if(onAddButtonClickListener != null) {
                    onAddButtonClickListener.onAddRssButtonClick(etRSSFeedLink.getText().toString());
                }
                break;
            }
            case R.id.buttonDialogCancel: {
                dismiss();
                break;
            }
        }
    }
}
