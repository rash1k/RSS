package com.example.rash1k.rss;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rash1k.rss.model.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class RssArrayAdapter extends ArrayAdapter<NewsItem> {


    private List<NewsItem> mNewsItems = new ArrayList<>();

    public RssArrayAdapter(@NonNull Context context, @NonNull List<NewsItem> items) {
        super(context, -1, items);
        mNewsItems = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NewsItem newsItem = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);

            viewHolder.mTitleTextView =
                    (TextView) convertView.findViewById(R.id.titleTextView);
            viewHolder.mAuthorIdTextView =
                    (TextView) convertView.findViewById(R.id.authorIdTextView);
            viewHolder.mAuthorNameTextView =
                    (TextView) convertView.findViewById(R.id.authorNameTextView);
            viewHolder.mDatePublishedTextView =
                    (TextView) convertView.findViewById(R.id.datePublishedTextView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (newsItem != null) {
            viewHolder.mTitleTextView.setText(newsItem.getTitle());
            viewHolder.mAuthorIdTextView.setText(newsItem.getAuthorId());
            viewHolder.mAuthorNameTextView.setText(newsItem.getAuthorName());
            viewHolder.mDatePublishedTextView.setText(newsItem.getDatePublished());
        }

        return convertView;
    }


    public void notifyChangeData() {
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        private TextView mTitleTextView;
        private TextView mAuthorIdTextView;
        private TextView mAuthorNameTextView;
        private TextView mDatePublishedTextView;
    }

}
