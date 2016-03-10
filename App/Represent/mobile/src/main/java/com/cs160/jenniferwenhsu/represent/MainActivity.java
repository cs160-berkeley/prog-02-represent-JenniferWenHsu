package com.cs160.jenniferwenhsu.represent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static ArrayList<Representative> reps = new ArrayList<>();
    public String TAG="Representative";
    private List<Node> nodes = new ArrayList<>();

    //provides the entry point to Google Play services
    protected GoogleApiClient mApiClient;

    protected static final String ADDRESS_REQUESTED_KEY = "address-request-pending";
    protected static final String LOCATION_ADDRESS_KEY = "location-address";

    //Represents a geographical location
    protected Location mLastLocation;

    /**
     * Tracks whether the user has requested an address. Becomes true when the user requests an
     * address and false when the address(or an error message) is delivered.
     * The user requests an address by pressing the Select Current Location button. This may happen
     * before GoogleApiClient connects. This activity uses this boolean to keep track of the user's
     * intent. If the value is true, the activity tries to fech the address as soon as
     * GoogleApiClient connects
     */
    protected boolean mAddressRequested;

    //The formatted location address
    protected String mAddressOutput;

    //Receiver registered with this activity to get the response from FetchAddressIntentService
    private AddressResultReceiver mResultReceiver;

    //Displays the location address.
    protected TextView mLocationAddressTextView;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Representative r1 = new Representative();
        r1.setName("Nancy Pelosi");
        r1.setParty("Democratic");
        r1.setTweetMessage("Demo tweet message from "+r1.getName());
        r1.setEmailLink("xx@gmail.com");
        r1.setWebsiteLink("www.google.com");
        r1.setTerm("xx/xx/xxxx - date for " + r1.getName());
        r1.setCommittee("Committee for " + r1.getName());
        r1.setBill("Recent bills for " + r1.getName());
        r1.setPicID(R.drawable.pelosi);

        Representative r2 = new Representative();
        r2.setName("Kevin McCarthy");
        r2.setParty("Republican");
        r2.setTweetMessage("Demo tweet message from "+r2.getName());
        r2.setEmailLink("xx@gmail.com");
        r2.setWebsiteLink("www.google.com");
        r2.setTerm("xx/xx/xxxx - date for " + r2.getName());
        r2.setCommittee("Committee for "+r2.getName());
        r2.setBill("Recent bills for "+r2.getName());
        r2.setPicID(R.drawable.kevinmccarthy);

        Representative r3 = new Representative();
        r3.setName("Loretta Sanchez");
        r3.setParty("Democratic");
        r3.setTweetMessage("Demo tweet message from " + r3.getName());
        r3.setEmailLink("xx@gmail.com");
        r3.setWebsiteLink("www.google.com");
        r3.setTerm("xx/xx/xxxx - date for " + r3.getName());
        r3.setCommittee("Committee for " + r3.getName());
        r3.setBill("Recent bills for " + r3.getName());
        r3.setPicID(R.drawable.loretta_sanchez);

        reps.add(r1);
        reps.add(r2);
        reps.add(r3);

        mLocationAddressTextView = (TextView)findViewById(R.id.location_address_view);
        //clicking on Find button
        final Button button = (Button) findViewById(R.id.zipcodeButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                EditText zipCodeEdit =(EditText)findViewById(R.id.zipCode);
                final String zipCodeTxt = zipCodeEdit.getText().toString();

                //sending zip code to Phone Main2Activity
                Intent sendActivity2Intent = new Intent(getBaseContext(), Main2Activity.class);
                sendActivity2Intent.putExtra("ZIP_CODE", zipCodeTxt);
                startActivity(sendActivity2Intent);


                mApiClient.disconnect(); mApiClient.connect();
            }
        });

        //clicking on "Select Current Location" button

        mResultReceiver = new AddressResultReceiver(new Handler());
        mAddressRequested= false;
        mAddressOutput = "";

        updateValuesFromBundle(savedInstanceState);


        final Button button2 = (Button) findViewById(R.id.currentLocationButton);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //final String randomZipCode = "12345";
                //sending zip code to Main2Activity

                Intent i = new Intent(getBaseContext(), Main2Activity.class);
                //i.putExtra("ZIP_CODE", randomZipCode);

                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                        mApiClient);
                if (mLastLocation != null) {
                    Log.d(TAG, "Latitude: " + String.valueOf(mLastLocation.getLatitude()));
                    Log.d(TAG, "Longitude: " + String.valueOf(mLastLocation.getLongitude()));
                    i.putExtra("LONGITUDE", String.valueOf(mLastLocation.getLongitude()));
                    i.putExtra("LATITUDE", String.valueOf(mLastLocation.getLatitude()));
                }

                startActivity(i);

                //We only start the service to fetch the address if GoogleApiClient is connected
                if(mApiClient.isConnected() && mLastLocation!=null){
                    startIntentService();
                }
                //If GoogleApiClient isn't connected, we process the user's request by setting
                //mAddressRequested to be true. Later, when GoogleApiClient connects, we launch the service to
                //fetch the address.
                mAddressRequested = true;
            }
        });

        buildGoogleApiClient();

    }

    private void updateValuesFromBundle(Bundle savedInstanceState){
        if(savedInstanceState!=null){
            //Check savedInstanceState to see if the address was previously requested.
            if(savedInstanceState.keySet().contains(ADDRESS_REQUESTED_KEY)){
                mAddressRequested = savedInstanceState.getBoolean(ADDRESS_REQUESTED_KEY);
            }
            //Check savedInstanceState to see if the location address string was previously found
            //and stored in the Bundle. If it was found, display the address string in the UI.
            if(savedInstanceState.keySet().contains(LOCATION_ADDRESS_KEY)){
                mAddressOutput = savedInstanceState.getString(LOCATION_ADDRESS_KEY);
                displayAddressOutput();
            }
        }
    }

    protected synchronized void buildGoogleApiClient(){
        mApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    public void button2Handler(View view){
        //We only start the service to fetch the address if GoogleApiClient is connected
        if(mApiClient.isConnected() && mLastLocation!=null){
            startIntentService();
        }
        //If GoogleApiClient isn't connected, we process the user's request by setting
        //mAddressRequested to be true. Later, when GoogleApiClient connects, we launch the service to
        //fetch the address.
        mAddressRequested = true;

    }

    @Override
    protected void onStart(){
        super.onStart();
        mApiClient.connect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected called");
        EditText zipCodeEdit =(EditText)findViewById(R.id.zipCode);
        final String zipCodeTxt = zipCodeEdit.getText().toString();
        Wearable.NodeApi.getConnectedNodes(mApiClient).setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
            @Override
            public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                nodes = getConnectedNodesResult.getNodes();

                sendMessage("/ZIPCODE", zipCodeTxt);
                Log.d(TAG, "Sent Message: " + zipCodeTxt);
            }
        });

        //Gets the best and most recent location currently available, which may be null
        //in rare cases when a location is not available
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mApiClient);
        if(mLastLocation!=null){
            //Determine whether a Geocoder is available.
            if(!Geocoder.isPresent()){
                Toast.makeText(this, R.string.no_geocoder_available, Toast.LENGTH_LONG).show();
                return;
            }

            //It is possible that the user presses the button to get the address before the
            // GoogleApiClient object successfully connects. In such a case, mAddressRequested
            //is set to be true, but no attempts is made to fetch the address. Instead, we start the
            //intent service here if the user has requested an address, since

            if(mAddressRequested){
                startIntentService();
            }
        }

    }

    /**
     * Creates an intent, adds location data to it as an extra, and starts the intent service for
     * fetching an address.
     */

    protected void startIntentService(){
        Log.d(TAG, "Staring startIntentService");
        //Create an intent for passing to the intent service responsible for fetching the address.
        Intent intent = new Intent(this, FetchAddressIntentService.class);

        //Pass the result receiver as an extra to the service.
        intent.putExtra(Constants.RECEIVER, mResultReceiver);

        //Pass the location data as an extra to the service.
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);

        //Start the service.
        startService(intent);
        Log.d(TAG, "end of startIntentService");
    }
    @Override
    protected void onResume(){
        super.onResume();
        mApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mApiClient.disconnect();
    }
    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
        mApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed! ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    private void sendMessage( final String path, final String text ) {
        Log.d(TAG, "Watch to Phone sendMessage called");

        for(Node node: nodes)
        {
            PendingResult result = Wearable.MessageApi.sendMessage(mApiClient,
                    node.getId(), path, text.getBytes());

            Log.d(TAG, " Sent message. Result: " + result.toString());
        }
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

    protected void displayAddressOutput(){
        Log.d(TAG, "mAddressOutput: "+mAddressOutput);
        mLocationAddressTextView.setText(mAddressOutput);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        //Save whether the address has been requested.
        savedInstanceState.putBoolean(ADDRESS_REQUESTED_KEY, mAddressRequested);

        //SAve the address String
        savedInstanceState.putString(LOCATION_ADDRESS_KEY, mAddressOutput);
        super.onSaveInstanceState(savedInstanceState);
    }
    /**
     * Receiver for data sent from FetchAddressIntentService
     */

    @SuppressLint("ParcelCreator")
    class AddressResultReceiver extends ResultReceiver{

        public AddressResultReceiver(Handler handler){
            super(handler);
        }
        /**
         * Receives data sent from FetchAddressIntentService
         */

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData){
            //Display the address string or an error message sent from the intent service.
            Log.d(TAG, "mAddressOutput in onReceiveResult: "+mAddressOutput);
            mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
            displayAddressOutput();
        }
    }
}
