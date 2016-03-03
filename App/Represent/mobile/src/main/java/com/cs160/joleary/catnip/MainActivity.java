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

import java.util.ArrayList;

public class MainActivity extends Activity {
    //there's not much interesting happening. when the buttons are pressed, they start
    //the PhoneToWatchService with the cat name passed in.
    public static ArrayList<Representative> reps = new ArrayList<>();
    public String TAG="Representative";

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

        //clicking on Find button
        final Button button = (Button) findViewById(R.id.zipcodeButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                EditText zipCodeEdit =(EditText)findViewById(R.id.zipCode);
                final String zipCodeTxt = zipCodeEdit.getText().toString();

                //sending zip code to Phone Main2Activity - this is wrong, should sent to SingleListItem
                Intent sendActivity2Intent = new Intent(getBaseContext(), Main2Activity.class);
                sendActivity2Intent.putExtra("ZIP_CODE", zipCodeTxt);
                startActivity(sendActivity2Intent);

                //sending zip code to PhoneToWatchService
                Log.d(TAG, "Mobile MainActivity setOnClickListener sends: "+zipCodeTxt);
                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                sendIntent.putExtra("ZIP_CODE", zipCodeTxt);
                startService(sendIntent);
            }
        });

        //clicking on "Select Current Location" button

        final Button button2 = (Button) findViewById(R.id.currentLocationButton);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String randomZipCode = "12345";
                //sending zip code to Main2Activity
                Log.d(TAG, "setOnClickListener:"+randomZipCode);
                Intent i = new Intent(getBaseContext(), Main2Activity.class);
                i.putExtra("ZIP_CODE", randomZipCode);
                startActivity(i);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void sendMessage(View view){
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);

    }
}
