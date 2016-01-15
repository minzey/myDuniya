package com.example.swati.myduniya;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PreferencesActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    private PreferencesAdapter preferencesAdapter;
    private ArrayList<String> feedItems= new ArrayList<String>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private String URL_FEED="http://prractice.herokuapp.com/keywords";
    private final String TAG ="PREFERENCES";
    private JSONArray feedArray;
    private EditText mSearchEdt;
    private TextWatcher mSearchTw;
    private ArrayList<String> keywords;
    private SessionManager session;
    private String URL_KEYWORD="http://prractice.herokuapp.com/android_receive";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);


        // if(getResources().getConfiguration().orientation==getResources().getConfiguration().ORIENTATION_LANDSCAPE)
         //   gaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, 1);
        //else
            gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
        preferencesAdapter = new PreferencesAdapter(getApplicationContext(), feedItems);
         URL_FEED="http://prractice.herokuapp.com/keywords";
        recyclerView.setAdapter(preferencesAdapter);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.setColorSchemeColors(
                Color.GRAY, Color.GRAY, Color.GRAY, Color.GRAY);
        fetchkeywords();
        keywords = new ArrayList<String>();
        mSearchEdt=(EditText) findViewById(R.id.filter);
        session = new SessionManager(getApplication());
        mSearchTw=new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG,s.toString());
                preferencesAdapter.getFilter().filter(s);

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //listAdapter.notifyDataSetChanged();

            }
        };
        mSearchEdt.addTextChangedListener(mSearchTw);
        Button bt = (Button)findViewById(R.id.btdone);
        /*feedItems.add("srk");
        feedItems.add("hello");
        feedItems.add("paris attacks");
        feedItems.add("tamasha");
        feedItems.add("narendra modi");
        feedItems.add("relationshps");
        feedItems.add("srk");
        feedItems.add("hello");
        feedItems.add("paris attacks");
        feedItems.add("tamasha");
        feedItems.add("narendra modi");
        feedItems.add("relationshps");
        feedItems.add("srk");
        feedItems.add("hello");
        feedItems.add("paris attacks");
        feedItems.add("tamasha");
        feedItems.add("narendra modi");
        feedItems.add("relationshps");
        feedItems.add("srk");
        feedItems.add("hello");
        feedItems.add("paris attacks");
        feedItems.add("tamasha");
        feedItems.add("narendra modi");
        feedItems.add("relationshps");
        feedItems.add("srk");
        feedItems.add("hello");
        feedItems.add("paris attacks");
        feedItems.add("tamasha");
        feedItems.add("narendra modi");
        feedItems.add("relationshps");
        feedItems.add("srk");
        feedItems.add("hello");
        feedItems.add("paris attacks");
        feedItems.add("tamasha");
        feedItems.add("narendra modi");
        feedItems.add("relationshps");
        feedItems.add("srk");
        feedItems.add("hello");
        feedItems.add("paris attacks");
        feedItems.add("tamasha");
        feedItems.add("narendra modi");
        feedItems.add("relationshps");
        feedItems.add("srk");
        feedItems.add("hello");
        feedItems.add("paris attacks");
        feedItems.add("tamasha");
        feedItems.add("narendra modi");
        feedItems.add("relationshps");
*/
        preferencesAdapter.notifyDataSetChanged();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keywords = preferencesAdapter.get_selected_keywords();
                for(int i=0;i<keywords.size();i++)
                    Log.d(TAG,keywords.get(i));
                JSONObject jsonObject=new JSONObject();
                try{
                    jsonObject.put("key",keywords);
                    jsonObject.put("email",session.getKeyEmail());
                /*for(int i=1;i<=keywords.size();i++)
                {   try {
                    String arr = keywords.get(i);
                    jsonObject.put("key_" + i, arr);
                    }catch(Exception e){

                    }
                }*/posttoserver(jsonObject);
                    Log.d(TAG,jsonObject.toString());
                    Toast.makeText(getApplicationContext(),"Saved preferences!",Toast.LENGTH_SHORT).show();
                    finish();
                }
                catch(Exception e){
                    Log.d(TAG,e.getMessage());
                }


                //HashMap<String ,String> params=new HashMap<String, String>();
                //params.put("keys",jsonObject.toString());
            }
        });

    }

    public void posttoserver(JSONObject obj){
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,URL_KEYWORD,obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };
        AppController.getInstance().addToRequestQueue(req, "post keys");
    }

    public void onRefresh(){
        fetchkeywords();
    }

    public void fetchkeywords(){
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
            feedItems.clear();
            feedArray = response.getJSONArray("keywords");

            Log.d(TAG,"inside parseJsonfeed");
            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);
                String item = new String();
                Log.d(TAG, feedObj.getString("key_name"));
                feedItems.add(feedObj.getString("key_name"));
            }

            preferencesAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preferences, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
