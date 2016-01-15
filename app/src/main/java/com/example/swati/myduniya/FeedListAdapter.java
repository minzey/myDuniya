package com.example.swati.myduniya;

/**
 * Created by Swati on 22-09-2015.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

public class FeedListAdapter extends RecyclerView.Adapter<FeedListAdapter.MyViewHolder> {
    private final String TAG = "FEED_LIST_ADAPTER";
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<FeedItem> feedItems;
    //private ArrayList<SentiItems> sitems;
    private ImageLoader imageLoader;

    public FeedListAdapter(Context context, ArrayList<FeedItem> feedItems) {
        this.context= context;
        inflater = LayoutInflater.from(context);
        this.feedItems = feedItems;
        //this.sitems=sitems;
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

   /* @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }*/

    @Override
    public long getItemId(int position) {
        return position;
    }
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.feed_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
         imageLoader = AppController.getInstance().getImageLoader();
        FeedItem current = feedItems.get(position);
        holder.title.setText(current.getTitle());
        holder.src.setText(current.getCategory());
        String nimage=current.getImge();
        if (nimage != null && nimage.equals("https://tctechcrunch2011.files.wordpress.com/2015/11/cukclkcuwaa0tuj-jpg-large.jpeg")==false
                && nimage.equals("https://tctechcrunch2011.files.wordpress.com/2015/11/industrycloud.jpg")==false) {
            Log.d(TAG,nimage);
            holder.img.setImageUrl(nimage, imageLoader);

        } else {
            holder.img.setVisibility(View.GONE);
        }
       /* if(current.getCategory().equals("Top stories")==true)
            holder.src.setText("The Times of India");
        else
            holder.src.setText("Hindustan Times");
    */}



    class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView title;
        TextView src;
        FeedImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);

             src = (TextView) itemView.findViewById(R.id.src);
             img = (FeedImageView)itemView.findViewById(R.id.icon);


        }

    }

}