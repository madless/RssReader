package com.dmikhov.rssreader.ui.rss;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmikhov.rssreader.R;
import com.dmikhov.rssreader.models.RssItem;
import com.dmikhov.rssreader.utils.ImageLoader;

import java.util.List;

import static com.dmikhov.rssreader.utils.Const.TAG;

/**
 * Created by dmikhov on 27.01.2017.
 */
public class RssAdapter extends RecyclerView.Adapter<RssAdapter.RssItemViewHolder> {

    private OnRssItemClickListener listener;
    private List<RssItem> rssItems;

    @Override
    public RssItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rss, parent, false);
        return new RssItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RssItemViewHolder holder, int position) {
        RssItem item = rssItems.get(position);
        holder.setData(item);
    }

    @Override
    public int getItemCount() {
        return rssItems != null ? rssItems.size() : 0;
    }

    public void setItems(List<RssItem> rssItems) {
        this.rssItems = rssItems;
    }

    public void setListener(OnRssItemClickListener listener) {
        this.listener = listener;
    }

    class RssItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivImage;
        private TextView tvDescription;
        private TextView tvReadMore;
        private TextView tvTitle;

        public RssItemViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            tvReadMore = (TextView) itemView.findViewById(R.id.tvReadMore);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }

        public void setData(RssItem item) {
            ImageLoader.loadImage(item.getImageUrl(), ivImage);
            tvTitle.setText(item.getTitle());
            tvDescription.setText(item.getDescription());
            tvReadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "on read more clicked ");
                    if (listener != null) {
                        listener.onReadMoreClicked(getAdapterPosition());
                    }
                }
            });
        }
    }
}
