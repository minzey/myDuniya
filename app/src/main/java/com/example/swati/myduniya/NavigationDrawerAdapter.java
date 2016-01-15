package com.example.swati.myduniya;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Ravi Tamada on 12-03-2015.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    private String name,email;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.name=name;
        this.email=email;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.navdrawer_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavDrawerItem current = data.get(position);
        holder.title.setText(current.getTitle());
        if(current.getTitle().equals("NEWS") || current.getTitle().equals("BLOGS")){
           // holder.title.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.title.setTextColor(Color.parseColor("#009688"));
            holder.title.setGravity(Gravity.LEFT);
            holder.title.setAllCaps(true);
        }
        else if(current.getTitle().equals("Recommended for You") ||
                current.getTitle().equals("Talk of the Town") ||
                current.getTitle().equals("Pick Place") ||
                current.getTitle().equals("Add Topic") ||
                current.getTitle().equals("Logout") ||
                current.getTitle().equals("Preferences")){
            holder.title.setGravity(Gravity.RIGHT);
            holder.title.setTextColor(Color.parseColor("#686868"));
            holder.title.setAllCaps(true);
        }
        else{
            holder.title.setTextColor(Color.parseColor("#686868"));
            holder.title.setGravity(Gravity.LEFT);
            holder.title.setAllCaps(false);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}