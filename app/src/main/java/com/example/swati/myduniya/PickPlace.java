package com.example.swati.myduniya;

import android.content.Intent;
import android.content.IntentSender;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.w3c.dom.Text;

public class PickPlace extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    GoogleApiClient mGoogleApiClient;

    private static final int REQUEST_PLACE_PICKER = 1;
    private static final int RC_SIGN_IN = 0;
    private boolean mIntentInProgress;
    private int flag=1;
    //GoogleApiClient.ConnectionCallbacks listener;
    //GoogleApiClient.OnConnectionFailedListener failedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_pick_place);
        Button bt = (Button)findViewById(R.id.pbutton);
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .enableAutoManage(this,0,this)
                .addApi(Places.GEO_DATA_API)
                .addApi( Places.PLACE_DETECTION_API )
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

       bt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               displayPlacePicker();
           }
       });

        //displayPlacePicker();

        // END_INCLUDE(intent)

    }

    @Override
    protected void onStart() {

        super.onStart();
        if( mGoogleApiClient != null )
            mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
    }

    public void onConnected(Bundle connectionHint) {
        // We've resolved any connection errors.  mGoogleApiClient can be used to
        // access Google APIs on behalf of the user.

        //displayPlacePicker();
    }

    public void onConnectionFailed(ConnectionResult result) {
        TextView mName = (TextView)findViewById(R.id.txtName);
        mName.setText("connection failed!");
        if (!mIntentInProgress && result.hasResolution()) {
            try {
                mIntentInProgress = true;
                startIntentSenderForResult(result.getResolution().getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    private void displayPlacePicker() {
        if( mGoogleApiClient == null || !mGoogleApiClient.isConnected() )
            return;

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult( builder.build( getApplicationContext() ), REQUEST_PLACE_PICKER );
        } catch ( GooglePlayServicesRepairableException e ) {

            Log.d( "PlacesAPI Demo", "GooglePlayServicesRepairableException thrown" );
        } catch ( GooglePlayServicesNotAvailableException e ) {

            Log.d("PlacesAPI Demo", "GooglePlayServicesNotAvailableException thrown");
        }
    }

    protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
        if( requestCode == REQUEST_PLACE_PICKER && resultCode == RESULT_OK ) {

            displayPlace(PlacePicker.getPlace(data, this));
        }
    }

    private void displayPlace( Place place ) {
        TextView mName = (TextView)findViewById(R.id.txtName);
      //  mName.setText("inside display place!");
        if( place == null )
            return;

        //TextView mName = (TextView)findViewById(R.id.txtName);
        mName.setText(place.getName());

        TextView mAddress = (TextView)findViewById(R.id.txtAddress);
        mAddress.setText(place.getAddress());

        TextView mPhone = (TextView)findViewById(R.id.txtPhone);
        mPhone.setText(place.getPhoneNumber());
        //mGoogleApiClient.disconnect();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pick_place, menu);
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
