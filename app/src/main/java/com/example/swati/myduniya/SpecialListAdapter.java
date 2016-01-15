package com.example.swati.myduniya;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Swati on 29-11-2015.
 */
public class SpecialListAdapter extends BaseAdapter implements Filterable {
    private final String TAG = "FEED_LIST_ADAPTER";
    private Context context;
    private LayoutInflater inflater;
    public ArrayList<SpecialItem> specialItems;
    private ArrayList<SpecialItem> specialfilterItems;
    private ValueFilter valueFilter;

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public SpecialListAdapter(Context context, ArrayList<SpecialItem> specialItems) {
        this.context = context;
        this.specialItems = specialItems;
        this.specialfilterItems = specialItems;
        getFilter();
    }

    @Override
    public int getCount() {
        return specialItems.size();
    }

    @Override
    public Object getItem(int location) {
        return specialItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null) {
            inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            Log.d(TAG, "inflater updated!");
        }
        if (convertView == null) {
            Log.d(TAG, "inside convertView");
            convertView = inflater.inflate(R.layout.special_item, parent, false);
            Log.d(TAG, "convert view updated!");
        }

        if (imageLoader == null) {
            imageLoader = AppController.getInstance().getImageLoader();
            Log.d(TAG, "image loader updated!");
        }

        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView timestamp = (TextView) convertView.findViewById(R.id.timestamp);
        TextView des = (TextView) convertView.findViewById(R.id.des);
        ImageView img = (ImageView) convertView.findViewById(R.id.icon);
        FeedImageView fiv = (FeedImageView) convertView.findViewById(R.id.feedImage);


        String uri_ht = "@drawable/htlogo";
        int imageResourceHt = context.getResources().getIdentifier(uri_ht, null, context.getPackageName());
        Drawable resht = context.getResources().getDrawable(imageResourceHt);

        String uri_firstpost = "@drawable/firstpost";
        int imageResourceFp = context.getResources().getIdentifier(uri_firstpost, null, context.getPackageName());
        Drawable resfp = context.getResources().getDrawable(imageResourceFp);

        String uri_tli = "@drawable/thelogical";
        int imageResourceTli = context.getResources().getIdentifier(uri_tli, null, context.getPackageName());
        Drawable restli = context.getResources().getDrawable(imageResourceTli);

        String uri_ys = "@drawable/yourstorylogo";
        int imageResourceYs = context.getResources().getIdentifier(uri_ys, null, context.getPackageName());
        Drawable resys = context.getResources().getDrawable(imageResourceYs);

        String uri_tc = "@drawable/techcrunh";
        int imageResourceTc = context.getResources().getIdentifier(uri_tc, null, context.getPackageName());
        Drawable restc = context.getResources().getDrawable(imageResourceTc);

// img.setImageDrawable(resred);
        Boolean ht=false;
        SpecialItem item = specialItems.get(position);

        if (item.getCategory().equals("Firstpost"))
            img.setImageDrawable(resfp);
        else if (item.getCategory().equals("The Logical Indian"))
            img.setImageDrawable(restli);
        else if (item.getCategory().equals("YourStory"))
            img.setImageDrawable(resys);
        else if (item.getCategory().equals("TechCrunch"))
            img.setImageDrawable(restc);
        else {
            img.setImageDrawable(resht);
            ht=true;
        }
        /*if (!TextUtils.isEmpty(item.getTitle())) {
            statusMsg.setText(item.getTitle());
            statusMsg.setVisibility(View.VISIBLE);
        } else {
            // status is empty, remove from view
            statusMsg.setVisibility(View.GONE);
        }*/
        title.setText(item.getTitle());
        timestamp.setText(item.getPubdate());
        if(ht==false)
            name.setText(item.getCategory());
        else
            name.setText("Hindustan Times");
        des.setText(item.getDescription());
        String nimage = item.getImge();
        if (nimage != null) {
            fiv.setImageUrl(nimage, imageLoader);

        } else {
            fiv.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public Filter getFilter() {

        if (valueFilter == null) {

            valueFilter = new ValueFilter();
        }

        return valueFilter;
    }


    class ValueFilter extends Filter {
        //private ArrayList<SpecialItem> specialItems;
       // private SpecialListAdapter spl;

        public ValueFilter() {

        }

        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {

                ArrayList<SpecialItem> filterList = new ArrayList<SpecialItem>();

                for (int i = 0; i < specialfilterItems.size(); i++) {

                    if (specialfilterItems.get(i).getTitle().toLowerCase().contains(constraint)) {
                        Log.d("FILTER", specialfilterItems.get(i).getTitle());
                        filterList.add(specialfilterItems.get(i));

                    }
                }


                results.count = filterList.size();

                results.values = filterList;

            } else {

                results.count = specialItems.size();

                results.values = specialItems;

            }

            return results;
        }


        //Invoked in the UI thread to publish the filtering results in the user interface.
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      Filter.FilterResults results) {
            if (results.count > 0) {
                specialItems = (ArrayList<SpecialItem>) results.values;

                notifyDataSetChanged();
            }
            else if(results.count==0){
                Toast.makeText(context, "Your search "+constraint.toString()+" did not match any articles", Toast.LENGTH_LONG).show();
            }


        }


    }

}