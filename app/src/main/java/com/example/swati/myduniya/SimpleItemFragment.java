package com.example.swati.myduniya;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.swati.myduniya.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class SimpleItemFragment extends Fragment implements AbsListView.OnItemClickListener,SwipeRefreshLayout.OnRefreshListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final String TAG="SIMPLE_ITEM_FRAG";
    private String URL_FEED = "http://prractice.herokuapp.com/";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int previous_maxid=0;
    private int current_maxid=1;
    private OnFragmentInteractionListener mListener;
    private SwipeRefreshLayout swipeRefreshLayout;
    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;
    private View view;
    ArrayList<String> arr = new ArrayList<>();
    //ArrayList<FeedItem> arr = new ArrayList<>();
    ArrayList<FeedItem> feeds = new ArrayList<>();
    private ArrayAdapter mAdapter;


    public static SimpleItemFragment newInstance(String param1) {
        SimpleItemFragment fragment = new SimpleItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }


    public SimpleItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
          //  mParam2 = getArguments().getString(ARG_PARAM2);
        }

        Log.d(TAG,URL_FEED);
        mAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, arr);
        //mAdapter = new ArrayAdapter<String>(getActivity(),
          //        android.R.layout.simple_list_item_1, android.R.id.text1, arr);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_item, container, false);
        URL_FEED="http://prractice.herokuapp.com/"+mParam1;
        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.setColorSchemeColors(
                Color.GRAY,Color.GRAY,Color.GRAY,Color.GRAY );


        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        /*swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        fetchNewData();
                                    }
                                }
        );*/




       // mAdapter.clear();


        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL_FEED);
        if (entry != null) {
            // fetch the data from cache
            Log.d(TAG,"loading cached data");
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }



        } else {
            Log.d(TAG, "making fresh volley request");
        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                URL_FEED, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG, "InitialResponse: " + response.toString());
                if (response != null) {
                    parseJsonFeed(response);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d(TAG, "request error!" + error.getMessage());
                setEmptyText("Connection error!");
            }
        });
            AppController.getInstance().addToRequestQueue(jsonReq);
        }



       // String one = new String("one");
       // mAdapter.add(one);
       // String two = new String("two");
       // mAdapter.add(two);

        mListView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void onRefresh(){
        fetchNewData();
    }

    public void fetchNewData(){
        Log.d(TAG, "making fresh volley request");
        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                URL_FEED, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG, "InitialResponse: " + response.toString());
                if (response != null) {
                    parseJsonFeed(response);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d(TAG, "request error!" + error.getMessage());
                setEmptyText("Connection error!");
            }
        });
        AppController.getInstance().addToRequestQueue(jsonReq);
    }


    private void parseJsonFeed(JSONObject response) {
        try {
            boolean skip = false;
            JSONArray feedArray = response.getJSONArray("tag");
            Log.d(TAG,"inside parseJsonfeed");
            JSONObject feedObjzc = (JSONObject) feedArray.get(0);
            current_maxid=feedObjzc.getInt("id");
            /*if(current_maxid==previous_maxid){
                skip=true;
            }*/
            Log.d(TAG,"current max id = "+current_maxid);
            Log.d(TAG,"previous max id = "+previous_maxid);
            Log.d(TAG,"Value of skip is = "+skip);
            if(skip == false) {
                mAdapter.clear();
                feeds.clear();
                for (int i = 0; i < feedArray.length(); i++) {
                    JSONObject feedObj = (JSONObject) feedArray.get(i);
                    //String item = new String(feedObj.getString("title"));
                    FeedItem item = new FeedItem();
                    item.setImge(feedObj.getString("image"));
                    item.setCategory(feedObj.getString("category"));
                    item.setDescription(feedObj.getString("description"));
                    item.setFullstory(feedObj.getString("full_story"));
                    item.setId(feedObj.getInt("id"));
                    item.setTitle(feedObj.getString("title"));
                    item.setPubdate(feedObj.getString("pubdate"));
                    Log.d(TAG, feedObj.getString("title"));
                    feeds.add(item);
                    mAdapter.add(item.getTitle());

                }
                JSONObject feedObjzp = (JSONObject) feedArray.get(0);
                //previous_maxid = feedArray.get(0).getInt("id");
                previous_maxid = feedObjzp.getInt("id");

                // notify data changes to list adapter
                mAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
        FeedItem temp = new FeedItem(feeds.get(position));
        //temp.setCategory(fee);
        Intent i =new Intent(getContext(), DetailActivity.class);
        i.putExtra("ntitle",temp.getTitle());
        i.putExtra("ncategory",temp.getCategory());
        i.putExtra("nimage",temp.getImge());
        i.putExtra("ndes",temp.getDescription());
        i.putExtra("npubdate",temp.getPubdate());
        i.putExtra("nfullstory",temp.getFullstory());
        Log.d(TAG,temp.getFullstory());
        //Log.d(TAG,temp.getDescription());
        //Toast.makeText(getContext(), temp.getPubdate(), Toast.LENGTH_SHORT).show();
        startActivity(i);
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
