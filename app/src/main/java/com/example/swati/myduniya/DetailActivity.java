package com.example.swati.myduniya;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;

public class DetailActivity extends AppCompatActivity {
    String ntitle,ncategory,nimage,ndes,npubdate,nfullstory;
    TextView title,des,pdate;
    FeedImageView feedImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Full Story");

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        Bundle extras= getIntent().getExtras();
        ntitle = extras.getString("ntitle");
        ncategory = extras.getString("ncategory");
        nimage = extras.getString("nimage");
        ndes = extras.getString("ndes");
        npubdate = extras.getString("npubdate");
        nfullstory = extras.getString("nfullstory");

        title = (TextView)findViewById(R.id.txtTitle);
        des = (TextView)findViewById(R.id.txtDes);
        pdate = (TextView)findViewById(R.id.txtDate);
        feedImageView = (FeedImageView)findViewById(R.id.feedImage1);
        getSupportActionBar().setTitle(ncategory);
        title.setText(ntitle);
        des.setText(nfullstory);
        pdate.setText(npubdate);

        Log.d("DETAIL", nimage);

        if (nimage != null) {
            feedImageView.setImageUrl(nimage, imageLoader);

        } else {
            feedImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
