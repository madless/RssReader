package com.dmikhov.rssreader.sections.main.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmikhov.rssreader.R;
import com.dmikhov.rssreader.entities.RssMenuItem;
import com.dmikhov.rssreader.sections.main.listeners.OnRSSMenuClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by madless on 28.01.2017.
 */
public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.NavigationHolder> {

    private List<RssMenuItem> items = new ArrayList<>();
    private OnRSSMenuClickListener listener;

    public NavigationAdapter(Context context, OnRSSMenuClickListener listener) {
        this.listener = listener;
        RssMenuItem addRssItem = new RssMenuItem("Add RSS Feed", false);
        items.add(addRssItem);
    }

    public void setItems(List<RssMenuItem> items) {
        this.items = items;
    }

    public void addItem(RssMenuItem item) {
        items.add(items.size() - 1, item);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public NavigationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_rss, parent, false);
        return new NavigationHolder(view);
    }

    @Override
    public void onBindViewHolder(NavigationHolder holder, int position) {
        RssMenuItem item = items.get(position);
        holder.setData(item);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    class NavigationHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvTitle;

        public NavigationHolder(View itemView) {
            super(itemView);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }

        public void setData(RssMenuItem item) {
            tvTitle.setText(item.getTitle());
            if(item.isRemovable()) {
                ivIcon.setImageResource(R.drawable.ic_remove_white);
                ivIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onRemoveItemClick(getAdapterPosition());
                    }
                });
            } else {
                ivIcon.setImageResource(R.drawable.ic_add_white);
                ivIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onAddNewItemClick();
                    }
                });
            }
        }
    }
}
