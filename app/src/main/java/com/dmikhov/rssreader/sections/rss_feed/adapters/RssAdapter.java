package com.dmikhov.rssreader.sections.rss_feed.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dmikhov.rssreader.R;
import com.dmikhov.rssreader.entities.RssItem;
import com.dmikhov.rssreader.sections.rss_feed.listeners.OnLinkClickListener;
import com.dmikhov.rssreader.utils.ImageLoader;

import java.util.List;

/**
 * Created by dmikhov on 27.01.2017.
 */
public class RssAdapter extends RecyclerView.Adapter<RssAdapter.RssItemViewHolder> {

    private OnLinkClickListener onLinkClickListener;
    private List<RssItem> rssItems;

    public RssAdapter(OnLinkClickListener onLinkClickListener) {
        this.onLinkClickListener = onLinkClickListener;
    }

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

    class RssItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivImage;
        private TextView tvDescription;
        private TextView tvTitle;
        private ProgressBar progressBar;

        public RssItemViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }

        public void setData(final RssItem item) {
            if(item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
                progressBar.setVisibility(View.VISIBLE);
                ivImage.setVisibility(View.VISIBLE);
                ImageLoader.loadImage(item.getImageUrl(), ivImage, new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        ivImage.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        ivImage.setVisibility(View.VISIBLE);
                        return false;
                    }
                });
            } else {
                ivImage.setVisibility(View.GONE);
            }
            tvTitle.setText(item.getTitle());
            setTextViewHTML(tvDescription, item.getDescription());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onLinkClickListener.onLinkClicked(item.getLink());
                }
            });
        }
    }

    protected void makeLinkClickable(SpannableStringBuilder strBuilder, final URLSpan span) {
        int start = strBuilder.getSpanStart(span);
        int end = strBuilder.getSpanEnd(span);
        int flags = strBuilder.getSpanFlags(span);
        ClickableSpan clickable = new ClickableSpan() {
            public void onClick(View view) {
                onLinkClickListener.onLinkClicked(span.getURL());
            }
        };
        strBuilder.setSpan(clickable, start, end, flags);
        strBuilder.removeSpan(span);
    }

    protected void setTextViewHTML(TextView text, String html) {
        CharSequence sequence = Html.fromHtml(html);
        SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
        URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
        for(URLSpan span : urls) {
            makeLinkClickable(strBuilder, span);
        }
        text.setText(strBuilder);
        text.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
