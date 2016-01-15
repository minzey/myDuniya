package com.example.swati.myduniya;

import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;
import com.twitter.sdk.android.tweetui.TweetViewFetchAdapter;

import java.util.Arrays;
import java.util.List;

public class TwitterActivity extends ListActivity  {
    TwitterDrawerFragment drawerFragment;



    List<Long> tweetIds = Arrays.asList(672267586885713920L,672267586168487938L,672267585283489792L,672082519580262400L,671988114127060992L,
            672267585283489792L,672267582825742336L,672267575191986176L,672267566598004738L,672267565536800768L,
            672267588034932736L,672267588030754817L,672267585283489792L,671884064610246656L,671897553919610881L,
            672267585283489792L,672267582825742336L,672267581940621313L,672267564383244289L,672267547606036480L);
    final TweetViewFetchAdapter adapter =
            new TweetViewFetchAdapter<CompactTweetView>(TwitterActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twitter_timeline);
        setListAdapter(adapter);
        adapter.setTweetIds(tweetIds, new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                // my custom actions
            }

            @Override
            public void failure(TwitterException exception) {
                // Toast.makeText(...).show();
            }
        });
    }    // Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

       // setSupportActionBar(mToolbar);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);


        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  getSupportActionBar().setHomeButtonEnabled(true);

        // getSupportActionBar().setDisplayShowHomeEnabled(true);




@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_twitter, menu);
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
