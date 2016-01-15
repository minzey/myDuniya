package com.example.swati.myduniya;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchResultsActivity extends ActionBarActivity {
    ArrayList<SearchItem> searchitems;
    ListView listView;
    SearchResultAdapter listAdapter;
    String URL_SEARCH="http://prractice.herokuapp.com/search_tag";
    final String TAG="SEARCH_RESULT";
    private ProgressDialog pDialog;
    private String query="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        listView = (ListView) findViewById(R.id.list);

        searchitems = new ArrayList<SearchItem>();

        listAdapter = new SearchResultAdapter(this, searchitems);
        listView.setAdapter(listAdapter);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        handleIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        Log.d(TAG, "handling search intent!");

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            URL_SEARCH="http://prractice.herokuapp.com/search_tag/"+query;
            URL_SEARCH=URL_SEARCH.replace(" ","%20");
            Log.d(TAG,URL_SEARCH);
            Log.d(TAG, "making fresh volley request");
            showpDialog();
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URL_SEARCH, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG,"inside response");
                    VolleyLog.d(TAG, "InitialResponse: " + response.toString());
                    if (response != null) {
                        //swipeRefreshLayout.setRefreshing(true);
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    Log.d(TAG,"request error!" + error.getMessage());
                    //swipeRefreshLayout.setRefreshing(false);
                    hidepDialog();
                }
            });
            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);
            //Log.d(TAG,query);
            //fetchSearchResult(query);

            // Adding request to volley request queue

        }
    }


    private void parseJsonFeed(JSONObject response) {
        try {
            searchitems.clear();
            JSONArray feedArray = response.getJSONArray("searched");

            Log.d(TAG, "inside parseJsonfeed");
            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);
                SearchItem item = new SearchItem();
                Log.d(TAG, feedObj.getString("title"));
                Toast.makeText(getApplicationContext(),
                        feedObj.getString("title"), Toast.LENGTH_SHORT).show();
                item.setTitle(feedObj.getString("title"));
                item.setImge(feedObj.getString("image"));
                item.setStory(feedObj.getString("story"));
                item.setDescription(feedObj.getString("description"));
                searchitems.add(item);
            }

            listAdapter.notifyDataSetChanged();
            hidepDialog();
            //swipeRefreshLayout.setRefreshing(false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void fetchSearchResult(String strquery){
        query=strquery;
        Log.d(TAG, "inside fetchSearchResult");
        showpDialog();

        //RequestQueue queue = Volley.newRequestQueue(this);
        //String url ="http://myserveraddress";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_SEARCH, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Search Response: " + response.toString());
                  showpDialog();

                try {
                    /*searchitems.clear();
                    JSONObject jObj = new JSONObject(response);
                        JSONArray arr = jObj.getJSONArray("searched");
                        for(int j=0;j<arr.length();j++){
                            JSONObject feedObj = arr.getJSONObject(j);
                            SearchItem item = new SearchItem();
                            Log.d(TAG, feedObj.getString("title"));
                            Toast.makeText(getApplicationContext(),
                                    feedObj.getString("title"), Toast.LENGTH_SHORT).show();
                            item.setTitle(feedObj.getString("title"));
                            item.setImge(feedObj.getString("image"));
                            item.setStory(feedObj.getString("story"));
                            item.setDescription(feedObj.getString("description"));
                            searchitems.add(item);
                        }
                    listAdapter.notifyDataSetChanged();*/
                    hidepDialog();
                    }
                catch(Exception e) {
                    e.printStackTrace();
                    //  hideDialog();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    hidepDialog();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "search volley Error: " + error.getMessage());
                //hideDialog();
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
               hidepDialog();
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
                params.put("search", query);
                return params;
            }

        };

        // Adding request to request queue
        //listAdapter.notifyDataSetChanged();
        AppController.getInstance().addToRequestQueue(strReq, "rec");

    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }




}