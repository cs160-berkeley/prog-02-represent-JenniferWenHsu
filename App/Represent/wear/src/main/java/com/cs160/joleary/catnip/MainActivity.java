package com.cs160.joleary.catnip;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    GridView gv;
    Context context;
    ArrayList prgmName;
    private GoogleApiClient mApiClient;
    private String TAG = "Represent!";
    private String pos = "empty";
    private List<Node> nodes = new ArrayList<>();


    public static String [] nameList={"Nancy Pelosi","Kevin McCarthy","Loretta Sanchez", "2012 Vote"};
    public static String [] partyList = {"Democratic", "Republican ", "Democratic", "Obama: 62.5%\nRomney: 37.5%"};
    public static int [] imageList={R.drawable.pelosi,R.drawable.kevinmccarthy,
            R.drawable.loretta_sanchez, R.drawable.blury_city};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gv=(GridView) findViewById(R.id.gridView1);
        CustomAdapter adapter = new CustomAdapter(this, nameList, partyList, imageList );
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                CharSequence text = "View " + nameList[position] + "on Phone";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                // Added code
                pos = String.valueOf(position);
                mApiClient.disconnect(); mApiClient.connect();


                /*
                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                sendIntent.putExtra("POSITION", position);
                startService(sendIntent);
                */
            }
        });

        mApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected called");

        Wearable.NodeApi.getConnectedNodes(mApiClient).setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
            @Override
            public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                nodes = getConnectedNodesResult.getNodes();

                sendMessage("/POSITION", pos);
                Log.d(TAG, "Sent Message: " + pos);
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
}