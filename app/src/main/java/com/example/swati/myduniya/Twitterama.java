package com.example.swati.myduniya;

import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swati.myduniya.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TweetViewFetchAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Swati on 17-11-2015.
 */
public class Twitterama extends ListFragment {

    List<Long> tweetIds;
    private static final String ARG_PARAM="query";
    private String querystring="#ChennaiFloods";
    public  static Twitterama newInstance(String param1) {
        Twitterama fragment = new Twitterama();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            querystring = getArguments().getString(ARG_PARAM);

        }


      }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String TAG="TWITTER";
        Log.d(TAG, "inside twitterama");
        Log.d(TAG,querystring);
        if(querystring.equals("#ChennaiFloods")){
            List<Long> tweetIds = Arrays.asList(672267586885713920L,672267586168487938L,672267585283489792L,672082519580262400L,671988114127060992L,
                    672267585283489792L,672267582825742336L,672267575191986176L,672267566598004738L,672267565536800768L);
        }
        else if(querystring.equals("#ChennaiRains")){
            List<Long> tweetIds = Arrays.asList(672267588034932736L,672267588030754817L,672267585283489792L,671884064610246656L,671897553919610881L,
                    672267585283489792L,672267582825742336L,672267581940621313L,672267564383244289L,672267547606036480L);
        }

        final TweetViewFetchAdapter adapter =
                new TweetViewFetchAdapter<CompactTweetView>(getActivity().getApplicationContext(),tweetIds);

        // setContentView(R.layout.tweet_list);
        setListAdapter(adapter);
        adapter.setTweetIds(tweetIds, new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                // my custom actions
                Log.d("TWITTER", "SUCCESS");

            }

            @Override
            public void failure(TwitterException exception) {
                // Toast.makeText(...).show();
                Log.d("TWITTER", exception.getMessage());
            }
        });
        return inflater.inflate(R.layout.twitter_timeline, container, false);

    }
}


