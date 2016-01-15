package com.example.swati.myduniya;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.Picasso;

public class RecommendedDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_detail);
        String image="http://www.thelogicalindian.com/wp-content/uploads/2015/11/paris-attack-750x500.jpg";
        String title="Paris Ambush: Its An Attack On Humanity, Let’s Isolate The Inhuman From Human";
        String s="<div class=\"entry-content clearfix\">\n" +
                "<pre style=\"text-align: center;\">Image Courtesy: <img src=\"http://gulfelitemag.com/wp-content/uploads/2015/05/RTR3ACYK.jpg\" target=\"_blank\">www.gulfelitemag.com</a></pre>\n" +
                "<p>Â </p>\n" +
                "<blockquote><p>â€œEverybody is a Genius. But If You Judge a Fish by Its Ability to Climb a Tree, It Will Live Its Whole Life Believing that It is Stupid â€œ</p></blockquote>\n" +
                "<p>A final year student of Information Technology at Delhi Technological University has made headlines by bagging a Rs 1.27 Crore offer from Google. Indeed, this is a tremendous feat in terms of the sheer enormity of the amount involved here.</p>\n" +
                "<p>But there is something really wrong with the way this thing has been reported in the media. It is good to see talent getting rewarded by future employers, but has anyone else noticed how the focus seems to be solely on the humongous salary offered and not on the role and responsibilities of the candidate? The media and all those who are sharing this news widely are influencing million young minds who will measure success with their salary rather than following their interest and passion.<br/>\n" +
                "Every year, many students across colleges opt out of placements to pursue their own business ideas, but has the media done enough to highlight such stories? Nope.</p>\n" +
                "<p>By flashing the figure of Rs 1.27 crore over and over, the media is directly creating a situation wherein a studentâ€™s academic success will end up getting measured in terms of his salary alone. This could impact the way newer students set their goals, and preference for job profile could give way to the salary involved. This would really defeat the whole purpose of education.</p>\n" +
                "<p>The Logical Indian community congratulates every student who gets his due, but sincerely hopes that the focus shifts away from money to a studentâ€™s interest.</p>\n" +
                "<p>Â </p>\n" +
                "<h3>Also Read: Â <img src=\"http:www.thelogicalindian.com/news/odisha-school-girl-wins-award-at-google-science-fair-for-developing-water-purifying-agent/\" target=\"_blank\">Odisha School Girl Wins Award At Google Science Fair For DevelopingÂ Low Cost Bio-Absorbent Based Water Purifier</a></h3>\n" +
                "<p>Â </p>\n" +
                "<p>Â </p>\n" +
                "<div style=\"text-align:center\">\n";
      final  TextView htmlTextView = (TextView)findViewById(R.id.txt);
        TextView titleview = (TextView)findViewById(R.id.title);
        titleview.setText(title);
       // Spanned htmlSpan = Html.fromHtml(s);
        htmlTextView.setText(Html.fromHtml(s, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                LevelListDrawable d = new LevelListDrawable();
                Drawable empty = getResources().getDrawable(R.drawable.abc_btn_check_material);;
                d.addLevel(0, 0, empty);
                d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());
                new ImageGetterAsyncTask(getApplicationContext(), source, d).execute(htmlTextView);

                return d;
            }
        }, null));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recommended_detail, menu);
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

class ImageGetterAsyncTask extends AsyncTask<TextView, Void, Bitmap> {


    private LevelListDrawable levelListDrawable;
    private Context context;
    private String source;
    private TextView t;

    public ImageGetterAsyncTask(Context context, String source, LevelListDrawable levelListDrawable) {
        this.context = context;
        this.source = source;
        this.levelListDrawable = levelListDrawable;
    }

    @Override
    protected Bitmap doInBackground(TextView... params) {
        t = params[0];
        try {
            Log.d("RECOMMENDED", "Downloading the image from: " + source);
            return Picasso.with(context).load(source).get();
        } catch (Exception e) {
           // String message = getStackTrace(e);
            Log.d("RECOMMENDED","exception returning null");
            return null;
        }
    }

    @Override
    protected void onPostExecute(final Bitmap bitmap) {
        try {
            Drawable d = new BitmapDrawable(context.getResources(), bitmap);
            Point size = new Point();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
            // Lets calculate the ratio according to the screen width in px
            int new_height = size.x * bitmap.getHeight() / bitmap.getWidth();
            Log.d("RECOMMENDED", "adjusted bitmap width: " + String.valueOf(size.x));
            Log.d("RECOMMENDED", "adjusted bitmap height: " + String.valueOf(new_height));

            //float multiplier = size.x / bitmap.getWidth();
            //Log.d("RECOMMENDED", "multiplier: " + multiplier);
            levelListDrawable.addLevel(1, 1, d);
            // Set bounds width  and height according to the bitmap resized size
            //levelListDrawable.setBounds(0, 0, bitmap.getWidth() * multiplier, bitmap.getHeight() * multiplier);
            levelListDrawable.setBounds(0, 0, size.x, new_height);
            //levelListDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
            levelListDrawable.setLevel(1);
            t.setText(t.getText()); // invalidate() doesn't work correctly...
        } catch (Exception e) { /* Like a null bitmap, etc. */
        Log.d("RECOMMENDED","caught null bitmap in post execute");
        }
    }
}