package com.cs160.jenniferwenhsu.represent;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

/**
 * Created by joleary and noon on 2/19/16 at very late in the night. (early in the morning?)
 */
public class PhoneListenerService extends WearableListenerService {
    private String TAG = "Represent!";

//   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.

 /*   final String[] representative_names = getResources().getStringArray(R.array.representativeName);
    final String[] parties = getResources().getStringArray(R.array.party);
    final Integer[] pictures = {R.drawable.profile_holder, R.drawable.profile_holder, R.drawable.profile_holder};
    final String[] tweets = getResources().getStringArray(R.array.tweetMessage);
    final String[] websites = getResources().getStringArray(R.array.personalWebsite);
    final String[] emails = getResources().getStringArray(R.array.email);
    final String[] terms = getResources().getStringArray(R.array.term);
    final String[] committees = getResources().getStringArray(R.array.committee);
    final String[] bills = getResources().getStringArray(R.array.bill);*/


    int position = 0;

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d(TAG, "in PhoneListenerService, got: " + messageEvent.getPath());

        String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
        Log.d(TAG, "Value: " + value);
        try {
            position = Integer.parseInt(value);
        }
        catch(NumberFormatException e)
        {
            Log.d(TAG, "Number format exception!");
            position = 0;
        }
        Log.d(TAG, "PhoneListenerService Position in onMessageReceived: " + position);

        Intent i = new Intent (getApplicationContext(), SingleListItem.class);
        String posString = Integer.toString(position);
        i.putExtra("POSITION", posString);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

      /*  Log.d(TAG, "Launching SingleListItem");
        Intent i = new Intent(getApplicationContext(), SingleListItem.class);
        //sending data to new activity
        i.putExtra("RepresentativeName", representative_names[position]);
        i.putExtra("party", parties[position]);
        i.putExtra("tweet", tweets[position]);
        i.putExtra("picture", pictures[position]);
        i.putExtra("website", websites[position]);
        i.putExtra("email", emails[position]);
        i.putExtra("term", terms[position]);
        i.putExtra("committee", committees[position]);
        i.putExtra("bill", bills[position]);
        */
        startActivity(i);

    }
}
