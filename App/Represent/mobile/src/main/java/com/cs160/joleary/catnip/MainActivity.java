package com.cs160.joleary.catnip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
    public static ArrayList<Representative> reps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Representative r1 = new Representative();
        r1.setName("Nancy Pelosi");
        r1.setParty("Democratic");
        r1.setTweetMessage("Demo tweet message from Nancy Pelosi");
        r1.setEmailLink("xx@gmail.com");
        r1.setWebsiteLink("www.google.com");

        Representative r2 = new Representative();
        r2.setName("Kevin McCarthy");
        r2.setParty("Democratic");
        r2.setTweetMessage("Demo tweet message from ");
        r2.setEmailLink("xx@gmail.com");
        r2.setWebsiteLink("www.google.com");

        Representative r3 = new Representative();
        r3.setName("Nancy Pelosi");
        r3.setParty("Democratic");
        r3.setTweetMessage("Demo tweet message from Nancy Pelosi");
        r3.setEmailLink("xx@gmail.com");
        r3.setWebsiteLink("www.google.com");

        final Button button = (Button) findViewById(R.id.zipcodeButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                EditText zipCodeEdit =(EditText)findViewById(R.id.zipCode);
                final String zipCodeTxt = zipCodeEdit.getText().toString();

                //sending zip code to Main2Activity
                Intent sendActivity2Intent = new Intent(getBaseContext(), Main2Activity.class);
                sendActivity2Intent.putExtra("ZIP_CODE", zipCodeTxt);
                startActivity(sendActivity2Intent);

                //sending zip code to PhoneToWatchService
                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                sendIntent.putExtra("ZIP_CODE", zipCodeTxt);
                startService(sendIntent);
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
