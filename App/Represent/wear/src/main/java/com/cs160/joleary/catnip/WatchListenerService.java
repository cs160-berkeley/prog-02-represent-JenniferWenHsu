package com.cs160.joleary.catnip;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

/**
 * Created by joleary and noon on 2/19/16 at very late in the night. (early in the morning?)
 */
public class WatchListenerService extends WearableListenerService {

    //   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.
    private String TAG = "Represent!";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d(TAG, "in PhoneListenerService, got: " + messageEvent.getPath());
        if( messageEvent.getPath().equalsIgnoreCase("/ZIPCODE") ) {
            String zip = new String(messageEvent.getData());
            Log.d(TAG, "WatchListenerService onMessageReceived zipcode: "+zip);
            Log.d(TAG, "Launching MainActivity");
            Intent intent = new Intent(this, MainActivity.class );
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("ZIP_CODE",zip);
            startActivity(intent);

        } else {
            Log.d(TAG, "Failure!");
        }

    }
}
