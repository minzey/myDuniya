package com.example.swati.myduniya;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;

import java.net.URL;

public class testfacebooklogin extends AppCompatActivity {
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testfacebooklogin);
        Bundle extras= getIntent().getExtras();
        String res = extras.getString("response");
        String nimage=extras.getString("image");
        Toast.makeText(getApplicationContext(),
                nimage, Toast.LENGTH_LONG)
                .show();
       //FeedImageView feedImageView = (FeedImageView)findViewById(R.id.feedImage1);
       // ImageView img = (ImageView)findViewById(R.id.feedImage1);
       // Bitmap profilePic = getFacebookProfilePicture(image);
        TextView txt = (TextView)findViewById(R.id.hello);
        txt.setText(res);
        //img.setImageBitmap(profilePic);
        /*if (nimage != null) {
            try {
                feedImageView.setImageUrl(nimage, imageLoader);
            }catch(Exception e){Toast.makeText(getApplicationContext(),
                    "Exception "+e, Toast.LENGTH_LONG)
                    .show();}

        } else {
            feedImageView.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),
                    "image null", Toast.LENGTH_LONG)
                    .show();
        }*/
    }
    public Bitmap getFacebookProfilePicture(String strurl){
       Bitmap bitmap=null;
        try{
           URL url=new URL(strurl);

            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }
       catch(Exception e){
           Toast.makeText(getApplicationContext(),
                   "Exception "+e, Toast.LENGTH_LONG)
                   .show();
       }
        return bitmap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_testfacebooklogin, menu);
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
