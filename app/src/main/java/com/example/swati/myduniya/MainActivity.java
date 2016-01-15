package com.example.swati.myduniya;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.astuetz.PagerSlidingTabStrip;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private  SessionManager session;
    private FragmentDrawer drawerFragment;
    private String twitteraccount;
    private final String TAG="MAIN ACTIVITY";
    private String URL_TWITTER="http://prractice.herokuapp.com/twitter_handle";
    private String sessemail,sessname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        SampleFragmentPagerAdapter mSampleFragmentPagerAdapter = new SampleFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mSampleFragmentPagerAdapter);
        mSampleFragmentPagerAdapter.notifyDataSetChanged();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        session = new SessionManager(getApplicationContext());
        sessemail=session.getKeyEmail();
        sessname=session.getKeyName();

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar, session.getKeyEmail(), session.getKeyName());
        drawerFragment.setDrawerListener(this);



        Log.d("MAIN ACTIVITY SESSION", session.getKeyEmail());
        Toast.makeText(getApplicationContext(), session.getKeyEmail(), Toast.LENGTH_LONG).show();
        if (!session.isLoggedIn()) {
            logoutUser();
        }
        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabsStrip.setShouldExpand(true);
        tabsStrip.setViewPager(viewPager);
        viewPager.setCurrentItem(5);
        tabsStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });

    }


    private void addDrawerItems() {
        String[] navArray = {  "Profile", "Pick Place", "Talk of the Town","LogOut","Recommended" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navArray);
        mDrawerList.setAdapter(mAdapter);
    }

    private void setupDrawer() {

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

    }

        public void onDrawerItemSelected(View view, int position){
        if (position == 0) {
            Intent intent = new Intent(getApplicationContext(), SpecialActivity.class);
            startActivity(intent);


        } else if (position == 1) {
            Intent intent = new Intent(getApplicationContext(), TwitterActivity.class);
            startActivity(intent);
        } else if (position == 2) {
            Intent intent = new Intent(getApplicationContext(), PickPlace.class);
            startActivity(intent);

            //logoutUser();
            /// /Toast.makeText(MainActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
        }
        else if (position == 3) {
            Intent intent = new Intent(getApplicationContext(), PreferencesActivity.class);
            startActivity(intent);
        }
        else if (position == 4) {
            Toast.makeText(MainActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
        }

        else if(position==5){
            logoutUser();
        }
        else if (position == 7) {
            Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
            intent.putExtra("category","india");
            startActivity(intent);
        }
        else if (position == 8) {
            Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
            intent.putExtra("category","world");
            startActivity(intent);
        }
        else if (position == 9) {
            Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
            intent.putExtra("category","tech-reviews");
            startActivity(intent);
        }
        else if (position == 10) {
            Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
            intent.putExtra("category","apps");
            startActivity(intent);
        }
        else if (position == 11) {
            Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
            intent.putExtra("category","autos");
            startActivity(intent);
        }
        else if (position == 12) {
            Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
            intent.putExtra("category","gadgets");
            startActivity(intent);
        }
        else if (position == 13) {
            Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
            intent.putExtra("category","sex-and-relationships");
            startActivity(intent);
        }
        else if (position == 14) {
            Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
            intent.putExtra("category","fashion-and-trends");
            startActivity(intent);
        }else if (position == 15) {
            Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
            intent.putExtra("category","lifestyle");
            startActivity(intent);
        }else if (position == 16) {
            Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
            intent.putExtra("category","books");
            startActivity(intent);
        }else if (position == 17) {
            Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
            intent.putExtra("category","travel");
            startActivity(intent);
        }else if (position == 18) {
            Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
            intent.putExtra("category","entertainment");
            startActivity(intent);
        }else if (position == 19) {
            Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
            intent.putExtra("category","bollywood");
            startActivity(intent);
        }else if (position == 20) {
            Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
            intent.putExtra("category","hollywood");
            startActivity(intent);
        }else if (position == 21) {
            Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
            intent.putExtra("category","television");
            startActivity(intent);
        }else if (position == 22) {
            Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
            intent.putExtra("category","sports");
            startActivity(intent);
        }else if (position == 23) {
            Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
            intent.putExtra("category","business");
            startActivity(intent);
        }else if (position == 24) {
            Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
            intent.putExtra("category","education");
            startActivity(intent);
        }else if (position == 25) {
            Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
            intent.putExtra("category","art-and-culture");
            startActivity(intent);
        }else if (position == 26) {
            Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
            intent.putExtra("category","social-media");
            startActivity(intent);
        }else if (position == 27) {
            Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
            intent.putExtra("category","opinion");
            startActivity(intent);
        }else if (position == 29) {
            Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
            intent.putExtra("category","The%20Logical%20Indian");
            startActivity(intent);
        }else if (position == 30) {
            Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
            intent.putExtra("category","YourStory");
            startActivity(intent);
        }else if (position == 31) {
            Intent intent = new Intent(getApplicationContext(), CategoryNews.class);
            intent.putExtra("category","TechCrunch");
            startActivity(intent);
        }

    }

        /*mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    Intent intent = new Intent(getApplicationContext(), PickPlace.class);
                    startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(getApplicationContext(), TwitterActivity.class);
                    startActivity(intent);
                } else if (position == 3) {
                    logoutUser();
                    /// /Toast.makeText(MainActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
                }
                else if (position == 4) {
                    Intent intent = new Intent(getApplicationContext(), RecommendedDetail.class);
                    startActivity(intent);
                    /// /Toast.makeText(MainActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Coming Soon!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }*/

    private void logoutUser() {
        session.setLogin(false);
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /*public static void callFacebookLogout(Context context) {
        Session session = Session.getActiveSession();
        if (session != null) {

            if (!session.isClosed()) {
                session.closeAndClearTokenInformation();
                //clear your preferences if saved
            }
        } else {

            session = new Session(context);
            Session.setActiveSession(session);

            session.closeAndClearTokenInformation();
            //clear your preferences if saved

        }

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle("Set Twitter Handle");
            alertDialog.setMessage("Share your twitter screen name with us for personalised results.");

            final EditText input = new EditText(MainActivity.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            alertDialog.setView(input);
            //alertDialog.setIcon(R.drawable.key);

            alertDialog.setPositiveButton("Done",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String twit = input.getText().toString();
                            Log.d("twitter handle is ", twit);
                            if (twit == null) {
                                Toast.makeText(getApplicationContext(), "Please enter valid twitter screen name!", Toast.LENGTH_SHORT).show();
                            } else if (twit != null) {
                                saveTwitter(twit);
                                Toast.makeText(getApplicationContext(), "Thankyou!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                    });

            alertDialog.setNegativeButton("Later",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            alertDialog.show();
            return true;
        }

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveTwitter(String twit){
        Log.d(TAG,"inside save twitter handle");
        twitteraccount=twit;
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_TWITTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "TWITTER Response: " + response.toString());
                //hideDialog();

                try {

                } catch (Exception e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "twitter Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
              //  hideDialog();
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
                params.put("account", twitteraccount);
                params.put("email",session.getKeyEmail());


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "twitter account");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
       // mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
