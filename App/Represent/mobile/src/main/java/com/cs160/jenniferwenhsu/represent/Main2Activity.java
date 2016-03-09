package com.cs160.jenniferwenhsu.represent;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;


public class Main2Activity extends ListActivity {
    private String TAG ="Represent!";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //receive location (zip code) from MainActivity
        Intent intent = getIntent();
        String zipCode = intent.getStringExtra("ZIP_CODE");

        if(zipCode!=null)
        Toast.makeText(Main2Activity.this, "Rendering information for "+ zipCode, Toast.LENGTH_LONG).show();

        //receive representPosition (which representative got clicked) from the watch
        String representPosition = intent.getStringExtra("POSITION");

        Log.d(TAG, "zipCode: "+zipCode + "  representPosition: "+representPosition);

        // storing string resources into Array

        final String[] representative_names = getResources().getStringArray(R.array.representativeName);


        // Binding resources Array to ListAdapter
        this.setListAdapter(new CustomListAdapter(this, representative_names, MainActivity.reps));

        ListView lv = getListView();

        // listening to single list item on click
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // Launching new Activity on selecting single List Item
                Intent intent2 = new Intent(getApplicationContext(), SingleListItem.class);
                String posString = Integer.toString(position);
                intent2.putExtra("POSITION", posString);
                startActivity(intent2);


            }
        });
    }
}