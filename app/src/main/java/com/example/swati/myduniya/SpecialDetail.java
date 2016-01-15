package com.example.swati.myduniya;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class SpecialDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras= getIntent().getExtras();
        String html = extras.getString("nhtml");
        Display display = getWindowManager().getDefaultDisplay();
        int width=display.getWidth();



        Log.d("DETAIL SPECIAL",html);
        //String html="\"<div class=\\\"entry-content clearfix\\\">\\n<p style=\\\"text-align: left;\\\"><img alt=\\\"b2-med\\\" class=\\\"aligncenter wp-image-16199\\\" height=\\\"455\\\" src=\\\"http://www.thelogicalindian.com/wp-content/uploads/2015/11/b2-med.jpg\\\" width=\\\"818\\\"/></p>\\n<pre style=\\\"text-align: center;\\\">Source: <a href=\\\"http://timesofindia.indiatimes.com/city/mumbai/Senior-citizen-loses-38000-to-card-fraud-within-20-mins/articleshow/49850607.cms?utm_source=facebook.com&amp;utm_medium=referral&amp;utm_campaign=TOI\\\">Times of India</a> | Image Courtesy: <a href=\\\"http://images.indianexpress.com/2014/02/b2-med.jpg\\\" target=\\\"_blank\\\">indianexpress</a></pre>\\n<p>Madhav Limaye, 72, was in for a rude shock when he was unsuspectingly robbed of \\u20b9 38,000 within 20 minutes of receiving a seemingly genuine call from his bank.</p>\\n<p>On November 12, Limaye, who lives in Andheri, Mumbai, with his wife, received a call at 10 am from a man who identified himself as an RBI official, seeking verification of his ATM cards. Not one to share his card details with anyone, Limaye agreed to answer his queries only after being convinced of the callers supposed identity.</p>\\n<p>\\u201cThe caller posed questions with confidence and gave me the impression that he knew about my accounts\\u201d, he said. Limaye has been operating accounts at the Bank of Baroda and the Bank of India for ten years now. He shared the card number and the CVV number of both his cards with the caller.</p>\\n<p>Over the next 20 minutes, he received a series of text messages regarding monetary amounts ranging from \\u20b9 200 to \\u20b9 999 being withdrawn from his accounts. Immediately, he called the bank customer care services and had both his cards blocked, but he had already lost a significant amount of \\u20b9 38,000 by then.</p>\\n<p>He reported to the Andheri police station. However, having been made using a private number, the call was not easy to trace. He was directed to the cyber cell, where he was informed action could be taken only after referral from the local police. Finally the FIR was registered after 2 days. So far, no arrests have been made. It is a shame that a senior citizen has been robbed and put through such distress.</p>\\n<p>The Logical Indian urges all its readers to be vigilant and not divulge crucial details over phone calls like these, no matter how apparently genuine they are.</p>\\n<p>\\u00a0</p>\\n<div style=\\\"text-align:center\\\"> </div> </div>\"";
        setContentView(R.layout.activity_special_detail);
        WebView webView= (WebView)findViewById(R.id.webView1);
        webView.loadDataWithBaseURL(null,"<style>img{display: inline;height: auto;max-width: 100%;}</style>"+html,"text/html","UTF-8","about:blank");
       // webView.loadDataWithBaseURL(null,data,"text/html","UTF-8","about:blank");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_special_detail, menu);
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
