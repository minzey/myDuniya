package com.example.swati.myduniya;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Swati on 09-10-2015.
 */
public class SearchResultAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<SearchItem> searchItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public SearchResultAdapter(Activity activity, ArrayList<SearchItem> searchItems) {
        this.activity = activity;
        this.searchItems = searchItems;
    }

    @Override
    public int getCount() {
        return searchItems.size();
    }

    @Override
    public Object getItem(int location) {
        return searchItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.search_result_item, null);
        Log.d("SEARCH_ADAPTER","adapterview updating");

        TextView head = (TextView) convertView
                .findViewById(R.id.title);
        TextView content = (TextView)convertView.findViewById(R.id.story);
        FeedImageView feedImageView = (FeedImageView)convertView.findViewById(R.id.feedImage);
        SearchItem item = searchItems.get(position);
        String nimage = item.getImge();

            head.setText(item.getTitle());
            content.setText(item.getStory());
        if (nimage != null) {
            feedImageView.setImageUrl(nimage, imageLoader);

        } else {
            feedImageView.setVisibility(View.GONE);
        }


        return convertView;
    }

}