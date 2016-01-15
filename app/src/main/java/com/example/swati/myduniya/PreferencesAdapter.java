package com.example.swati.myduniya;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Swati on 02-12-2015.
 */
public class PreferencesAdapter extends RecyclerView.Adapter<PreferencesAdapter.MyViewHolder> implements Filterable{
    private final String TAG = "PREFERENCES_ADAPTER";
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<String> feedItems;
    private ArrayList<String> feedfilterItems;
    private static ArrayList<String> selected=new ArrayList<>();
    //private ArrayList<SentiItems> sitems;
    //private ImageLoader imageLoader;
    private ValueFilter valueFilter;

    public PreferencesAdapter(Context context, ArrayList<String> feedItems) {
        this.context= context;
        inflater = LayoutInflater.from(context);
        this.feedItems = feedItems;
        this.feedfilterItems=feedItems;
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
        View view = inflater.inflate(R.layout.preferences_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //imageLoader = AppController.getInstance().getImageLoader();

        String current = feedItems.get(position);
        Log.d("pref adapter", current);
        holder.title.setText(current);
        if(selected.contains(feedItems.get(position))){
           // holder.itemView.getBackground().setColorFilter(Color.parseColor("#ccffe6"));
            //holder.itemView.setBackgroundColor(Color.parseColor("#ccffe6"));
            holder.itemView.setSelected(true);

        }
        else
            //holder.itemView.setBackgroundColor(Color.WHITE);
        holder.itemView.setSelected(false);
    }



    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public MyViewHolder(View itemView) {

            super(itemView);

            itemView.setClickable(true);
            title = (TextView) itemView.findViewById(R.id.title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selected.contains(feedItems.get(getLayoutPosition()))) {
                        selected.remove(feedItems.get(getLayoutPosition()));

                        Toast.makeText(context, feedItems.get(getLayoutPosition()) + " removed", Toast.LENGTH_SHORT).show();
                    } else{
                        selected.add(feedItems.get(getLayoutPosition()));
                    Toast.makeText(context, feedItems.get(getLayoutPosition()) + " selected", Toast.LENGTH_SHORT).show();
                    }
                    notifyItemChanged(getLayoutPosition());
                }
            });

        }

    }

    public ArrayList<String> get_selected_keywords(){
        return selected;
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

                ArrayList<String> filterList = new ArrayList<String>();

                for (int i = 0; i < feedfilterItems.size(); i++) {

                    if (feedfilterItems.get(i).toLowerCase().contains(constraint)) {
                        Log.d("FILTER", feedfilterItems.get(i));
                        filterList.add(feedfilterItems.get(i));

                    }
                }


                results.count = filterList.size();

                results.values = filterList;

            } else {

                results.count = feedItems.size();

                results.values = feedItems;

            }

            return results;
        }


        //Invoked in the UI thread to publish the filtering results in the user interface.
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      Filter.FilterResults results) {
            if (results.count > 0) {
                feedItems = (ArrayList<String>) results.values;

                notifyDataSetChanged();
            }
            else if(results.count==0){
                Toast.makeText(context, "Your search "+constraint.toString()+" did not match any articles", Toast.LENGTH_LONG).show();
            }


        }


    }


}