package com.example.swati.myduniya;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpecialActivity extends AppCompatActivity implements AbsListView.OnItemClickListener{
    private ListView listView;
    private SpecialListAdapter listAdapter;
    private ArrayList<SpecialItem> specialItems;
    private String URL_SPECIAL = "http://prractice.herokuapp.com/recommended_news";
    private final String TAG="SPECIAL_ACTVITY";
    private EditText mSearchEdt;
    private TextWatcher mSearchTw;
    private SessionManager session;
    private ProgressDialog progressDialog;
    private int newsid;
    private String URL_POSTCLICK="http://prractice.herokuapp.com/on_click";
    //private ValueAdapter valueAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mSearchEdt=(EditText) findViewById(R.id.filter);
        session = new SessionManager(getApplicationContext());
    //    progressDialog.setMessage("Fetch personalised results..");
        mSearchTw=new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG,s.toString());
                listAdapter.getFilter().filter(s);

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
      //  progressDialog = new ProgressDialog(this);
      //  progressDialog.setCancelable(false);

        mSearchEdt.addTextChangedListener(mSearchTw);
        listView = (ListView) findViewById(R.id.cardList);

        specialItems = new ArrayList<SpecialItem>();

        listAdapter = new SpecialListAdapter(this, specialItems);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
        fetcharticles();


        /*SpecialItem item1 = new SpecialItem();
        item1.setCategory("Firstpost");
        item1.setTitle("Rajan meets Jaitley ahead of RBI\u2019s monetary policy announcement");
        item1.setDescription("The benchmark repurchase (repo) rate has subsequently come down from 7.25 percent to 6.75 percent, the lowest in four-and-a-half years.");
        item1.setPubdate("2015-11-27T21:57:26+05:30");
        item1.setImge("http://s4.firstpost.in/wp-content/uploads/2015/11/Arun_Raghuram380PIB.jpg");
        specialItems.add(item1);

        SpecialItem item2 = new SpecialItem();
        item2.setCategory("Firstpost");
        item2.setTitle("Rajan meets Jaitley ahead of RBI\u2019s monetary policy announcement");
        item2.setDescription("The benchmark repurchase (repo) rate has subsequently come down from 7.25 percent to 6.75 percent, the lowest in four-and-a-half years.");
        item2.setPubdate("2015-11-27T21:57:26+05:30");
        item2.setImge("http://s4.firstpost.in/wp-content/uploads/2015/11/Arun_Raghuram380PIB.jpg");
        specialItems.add(item2);

        listAdapter.notifyDataSetChanged();*/

    }


    /*private void parseJsonSentiFeed(JSONObject response) {
        try {
            sentimentalItems.clear();
            JSONObject sentifeed = response.getJSONObject("sentiment");
            Log.d(TAG,"inside parseJsonsentifeed");
            for (int i = 0; i < sentifeed.length(); i++) {
                SentiItems si = new SentiItems();
                String temp=String.valueOf(i);
                si.setSentiment(sentifeed.getString(temp));
                Log.d("SENTIMENT",sentifeed.getString(temp));
                sentimentalItems.add(si);
            }
            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/

    public void fetcharticles(){
        Log.d(TAG, "making fresh volley request for articles data");
        StringRequest strReq = new StringRequest(Request.Method.POST,
               URL_SPECIAL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                //Toast.makeText(getApplicationContext(),
                 //       response.toString(), Toast.LENGTH_LONG).show();
               // hideDialog();
              //  showDialog();

                try {
                    specialItems.clear();
                    JSONObject jObj = new JSONObject(response);
                    //String validation = jObj.getString("validation");
                    JSONObject newsarray = jObj.getJSONObject("news");
                    Toast.makeText(getApplicationContext(),
                                  "Getting personalised feeds", Toast.LENGTH_LONG).show();
                    // Check for error node in json
                    int inci=0;
                    for(int i=0;i<newsarray.length();i++){
                        inci++;
                        //Toast.makeText(getApplicationContext(),
                          //      "inside news getting key"+inci, Toast.LENGTH_LONG).show();

                                JSONArray arr = newsarray.getJSONArray("key"+inci);
                        for(int j=0;j<arr.length();j++){
                            JSONObject feedObj = arr.getJSONObject(j);
                            SpecialItem item = new SpecialItem();
                            Log.d(TAG, feedObj.getString("title"));
                   //         Toast.makeText(getApplicationContext(),
                     //              feedObj.getString("title"), Toast.LENGTH_SHORT).show();
                            item.setTitle(feedObj.getString("title"));
                            item.setImge(feedObj.getString("image"));
                            item.setCategory(feedObj.getString("category"));
                            item.setDescription(feedObj.getString("description"));
                            item.setFullstory(feedObj.getString("full_story"));
                            item.setHtml(feedObj.getString("html"));
                            item.setId(feedObj.getInt("id"));
                            item.setPubdate(feedObj.getString("pubdate"));
                            if(i==0){
                                item.setPriority(5);
                            }
                            else if(i==1){
                                item.setPriority(4);
                            }
                            else if(i==2){
                                item.setPriority(3);
                            }
                            else
                                item.setPriority(1);
                            specialItems.add(item);
                        }
                    }
                    removeduplicate();
                    listAdapter.notifyDataSetChanged();
                //    hideDialog();

                } catch (Exception e) {
                    // JSON error
                    e.printStackTrace();
                  //  hideDialog();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "special Error: " + error.getMessage());
                //hideDialog();
                Toast.makeText(getApplicationContext(),
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
                params.put("email", session.getKeyEmail());
                //params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        //listAdapter.notifyDataSetChanged();
        AppController.getInstance().addToRequestQueue(strReq, "rec");
    }

    public void removeduplicate(){
        for(int i=0;i<specialItems.size();i++){
            SpecialItem temp = specialItems.get(i);
            for(int j=i+1;j<specialItems.size();j++){
                if(temp.getId()==specialItems.get(j).getId()){
                    specialItems.remove(specialItems.get(j));
                    j--;
                }
            }
        }
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    /*private void parseJsonFeed(JSONObject response) {
        try {
            specialItems.clear();
            JSONArray feedArray = response.getJSONArray("tag");

            Log.d(TAG,"inside parseJsonfeed");
            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);
                SpecialItem item = new SpecialItem();
                Log.d(TAG, feedObj.getString("title"));
                item.setTitle(feedObj.getString("title"));
                item.setImge(feedObj.getString("image"));
                item.setCategory(feedObj.getString("category"));
                item.setDescription(feedObj.getString("description"));
                item.setFullstory(feedObj.getString("full_story"));
                item.setHtml(feedObj.getString("html"));
                item.setId(feedObj.getInt("id"));
                // item.setTitle(feedObj.getString("title"));
                item.setPubdate(feedObj.getString("pubdate"));
                // Log.d(TAG, feedObj.getString("title"));
                // feeds.add(item);

                specialItems.add(item);
            }

            listAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_special, menu);
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
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "special Error: " + error.getMessage());
                //hideDialog();
                Toast.makeText(getApplicationContext(),
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
                params.put("email", session.getKeyEmail());

                return params;
            }

        };

        // Adding request to request queue
        //listAdapter.notifyDataSetChanged();
        AppController.getInstance().addToRequestQueue(strReq, "rec");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        SpecialItem temp = new SpecialItem(specialItems.get(position));
        //postOnClick(temp.getId());
        //temp.setCategory(fee);
        if(temp.getCategory().equals("The Logical Indian") || temp.getCategory().equals("YourStory") || temp.getCategory().equals("TechCrunch")){
            Intent i =new Intent(this, SpecialDetail.class);
            i.putExtra("nhtml",temp.getHtml());
            startActivity(i);
        }
        else{
            Intent i = new Intent(this, DetailActivity.class);
            i.putExtra("ntitle", temp.getTitle());
            i.putExtra("ncategory", temp.getCategory());
            i.putExtra("nimage", temp.getImge());
            i.putExtra("ndes", temp.getDescription());
            i.putExtra("npubdate", temp.getPubdate());
            i.putExtra("nfullstory", temp.getFullstory());
            //Log.d(TAG, temp.getFullstory());
            //Log.d(TAG,temp.getDescription());
            // Toast.makeText(getContext(), temp.getPubdate(), Toast.LENGTH_SHORT).show();
            startActivity(i);
        }



    }

}
