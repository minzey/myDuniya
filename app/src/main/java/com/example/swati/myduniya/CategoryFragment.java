package com.example.swati.myduniya;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.swati.myduniya.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CategoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String ARG_PARAM1 = "";
    //private GridView listView;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    private FeedListAdapter listAdapter;
    private View mView;
    private ArrayList<FeedItem> feedItems= new ArrayList<FeedItem>();
    //public ArrayList<SentiItems> sentimentalItems= new ArrayList<SentiItems>();
    private String URL_FEED = "http://prractice.herokuapp.com/";
    //private String URL_SENTI_FEED="http://prractice.herokuapp.com/senti/Top%20stories";
    private String mParam1,mParam2,mParam3,mParam4,mParam5,mParam6;
    private final String TAG="BREAKING_NEWS_SENTIMENT";
    private View view;
    private JSONArray feedArray;
    private OnFragmentInteractionListener mListener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int newsid;
    private String URL_POSTCLICK="http://prractice.herokuapp.com/on_click";
    private SessionManager session;
    //private ProgressDialog pDialog;

    public static CategoryFragment newInstance(String param1) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_news, container, false);
        RecyclerView recyclerView = (RecyclerView)mView.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        if(getResources().getConfiguration().orientation==getResources().getConfiguration().ORIENTATION_LANDSCAPE)
            gaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, 1);
        else
            gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
        session = new SessionManager(getActivity().getApplicationContext());

        //listView = (ListView) mView.findViewById(R.id.list);
        // listView = (GridView) mView.findViewById(R.id.list);
        //listAdapter = new FeedListAdapter(getContext(), feedItems,sentimentalItems);
        listAdapter = new FeedListAdapter(getActivity().getApplicationContext(), feedItems);
        URL_FEED="http://prractice.herokuapp.com/"+mParam1;

        recyclerView.setAdapter(listAdapter);
        // feedItems.clear();
        swipeRefreshLayout = (SwipeRefreshLayout)mView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.setColorSchemeColors(
                Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY);

        Log.d(TAG, "inside oncreateview");



        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL_FEED);
        if (entry != null) {
            // fetch the data from cache
            Log.d(TAG, "loading cached NEWS data");
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
        }
        else{
            Log.d(TAG,"No cached news data!");
            fetchNewsData();
        }


        recyclerView.addOnItemTouchListener(
                new RecyclerCategoryItemClickListener((getActivity().getApplicationContext()), new RecyclerCategoryItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
                        Log.d(TAG, "item clicked!");
                        FeedItem temp = new FeedItem(feedItems.get(position));
                        //temp.setCategory(fee);
                        postOnClick(temp.getId());
                        if(temp.getCategory().equals("The Logical Indian") || temp.getCategory().equals("TechCrunch") || temp.getCategory().equals("YourStory")){
                            Intent i = new Intent(getActivity().getApplicationContext(),SpecialDetail.class);
                            i.putExtra("nhtml",temp.getHtml());
                            startActivity(i);
                        }
                        else {
                            Intent i = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
                            i.putExtra("ntitle", temp.getTitle());
                            i.putExtra("ncategory", temp.getCategory());
                            i.putExtra("nimage", temp.getImge());
                            i.putExtra("ndes", temp.getDescription());
                            i.putExtra("npubdate", temp.getPubdate());
                            i.putExtra("nfullstory", temp.getFullstory());
                            Log.d(TAG, temp.getFullstory());
                            //Log.d(TAG,temp.getDescription());
                            // Toast.makeText(getContext(), temp.getPubdate(), Toast.LENGTH_SHORT).show();
                            startActivity(i);
                        }
                    }
                })
        );
        return mView;
    }


    public void postOnClick(int id){
        newsid=id;
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_POSTCLICK, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "post on click Response: " + response.toString());
                //Toast.makeText(getApplicationContext(),
                //       response.toString(), Toast.LENGTH_LONG).show();
                // hideDialog();
                //  showDialog();

                try {
                    //    hideDialog();

                } catch (Exception e) {
                    // JSON error
                    e.printStackTrace();
                    //  hideDialog();
                    Toast.makeText(getContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "special Error: " + error.getMessage());
                //hideDialog();
                Toast.makeText(getContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                //hideDialog();
            }
        }){

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("newsid",String.valueOf(newsid) );
                params.put("email",session.getKeyEmail() );

                return params;
            }

        };

        // Adding request to request queue
        //listAdapter.notifyDataSetChanged();
        AppController.getInstance().addToRequestQueue(strReq, "rec");
    }

    public void onRefresh(){
        // feedItems.clear();
        fetchNewsData();
        //fetchSentimentData();
        // listAdapter.notifyDataSetChanged();
    }

    public void fetchNewsData(){
        Log.d(TAG, "making fresh volley request for news data");
        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                URL_FEED, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG,"inside response");
                VolleyLog.d(TAG, "InitialResponse: " + response.toString());
                if (response != null) {
                    swipeRefreshLayout.setRefreshing(true);
                    parseJsonFeed(response);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d(TAG,"request error!" + error.getMessage());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReq);
    }


    private void parseJsonFeed(JSONObject response) {
        try {
            //feedItems.clear();
            feedArray = response.getJSONArray("tag");

            Log.d(TAG,"inside parseJsonfeed");
            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);
                FeedItem item = new FeedItem();
                Log.d(TAG, feedObj.getString("title"));
                item.setTitle(feedObj.getString("title"));
                item.setImge(feedObj.getString("image"));
                item.setCategory(feedObj.getString("category"));
                item.setDescription(feedObj.getString("description"));
                item.setFullstory(feedObj.getString("full_story"));
                item.setId(feedObj.getInt("id"));
                item.setHtml(feedObj.getString("html"));
                // item.setTitle(feedObj.getString("title"));
                item.setPubdate(feedObj.getString("pubdate"));
                // Log.d(TAG, feedObj.getString("title"));
                // feeds.add(item);

                feedItems.add(item);
            }

            listAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    // TODO: Rename method, update argument and hook method into UI event



    /*public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG,"item clicked!");
        FeedItem temp = new FeedItem(feedItems.get(position));
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
       // Toast.makeText(getContext(), temp.getPubdate(), Toast.LENGTH_SHORT).show();
        startActivity(i);
    }*/
    public void onNewsClick( View view, int position) {
        Log.d(TAG,"item clicked!");
        FeedItem temp = new FeedItem(feedItems.get(position));
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
        // Toast.makeText(getContext(), temp.getPubdate(), Toast.LENGTH_SHORT).show();
        startActivity(i);
    }

    /*@Override
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
        public void onFragmentInteraction(Uri uri);
    }

}

class RecyclerCategoryItemClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    GestureDetector mGestureDetector;

    public RecyclerCategoryItemClickListener(Context context, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildPosition(childView));
            return true;
        }
        return false;
    }

    @Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }

    @Override
    public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){}
}
