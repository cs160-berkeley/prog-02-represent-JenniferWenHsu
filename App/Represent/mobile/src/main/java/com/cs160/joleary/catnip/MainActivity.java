package com.cs160.joleary.catnip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    //there's not much interesting happening. when the buttons are pressed, they start
    //the PhoneToWatchService with the cat name passed in.
    public static ArrayList<Representative> reps = new ArrayList<>();
    public String TAG="Representative";
    private GoogleApiClient mApiClient;
    private List<Node> nodes = new ArrayList<>();
    public static ArrayList<String> nameList = new ArrayList<>();
    public static ArrayList<String> partyList = new ArrayList<>();

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
        r3.setTweetMessage("Demo tweet message from "+r3.getName());
        r3.setEmailLink("xx@gmail.com");
        r3.setWebsiteLink("www.google.com");
        r3.setTerm("xx/xx/xxxx - date for " + r3.getName());
        r3.setCommittee("Committee for "+r3.getName());
        r3.setBill("Recent bills for "+r3.getName());
        r3.setPicID(R.drawable.loretta_sanchez);

        reps.add(r1);
        reps.add(r2);
        reps.add(r3);

        //convert Represenative to String arrays for transmitting to watch
        for(int i=0; i<reps.size(); i++){
            nameList.add(reps.get(i).getName());
            partyList.add(reps.get(i).getParty());
        }
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

        final Button button2 = (Button) findViewById(R.id.currentLocationButton);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String randomZipCode = "12345";
                //sending zip code to Main2Activity
                Log.d(TAG, "setOnClickListener:" + randomZipCode);
                Intent i = new Intent(getBaseContext(), Main2Activity.class);
                i.putExtra("ZIP_CODE", randomZipCode);
                startActivity(i);

            }
        });


        mApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


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


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

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

    public void sendMessage(View view){
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);

    }
}
