package com.cs160.joleary.catnip;

import android.app.Activity;
import android.app.ListActivity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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

        Toast.makeText(Main2Activity.this, "Rendering information for "+ zipCode, Toast.LENGTH_LONG).show();

        //receive representPosition (which representative got clicked)
        String representPosition = intent.getStringExtra("POSITION");

/*        if(zipCode == null)
            zipCode = "empty";
        if(representPosition==null)
            representPosition = "empty";*/
        Log.d(TAG, "zipCode: "+zipCode + "  representPosition: "+representPosition);

        // storing string resources into Array
        final String[] representative_names = getResources().getStringArray(R.array.representativeName);
        final String[] parties = getResources().getStringArray(R.array.party);
        final Integer[] pictures = {R.drawable.pelosi, R.drawable.kevinmccarthy, R.drawable.loretta_sanchez};
        final String[] tweets = getResources().getStringArray(R.array.tweetMessage);
        final String[] websites = getResources().getStringArray(R.array.personalWebsite);
        final String[] emails = getResources().getStringArray(R.array.email);
        final String[] terms = getResources().getStringArray(R.array.term);
        final String[] committees = getResources().getStringArray(R.array.committee);
        final String[] bills = getResources().getStringArray(R.array.bill);

        // Binding resources Array to ListAdapter
        this.setListAdapter(new CustomListAdapter(this, representative_names, pictures, parties, tweets, websites, emails));

        ListView lv = getListView();

        // listening to single list item on click
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // Launching new Activity on selecting single List Item

                Intent intent=new Intent(getApplicationContext(), SingleListItem.class);
                Bundle bundle=new Bundle();
                bundle.putInt("image", pictures[position]);
                bundle.putString("RepresentativeName", representative_names[position]);
                bundle.putString("party", parties[position]);
                bundle.putString("tweet", tweets[position]);
                bundle.putString("website", websites[position]);
                bundle.putString("email", emails[position]);
                bundle.putString("term", terms[position]);
                bundle.putString("committee", committees[position]);
                bundle.putString("bill", bills[position]);
                intent.putExtras(bundle);
                startActivity(intent);


/*              Intent i = new Intent(getApplicationContext(), SingleListItem.class);
                // sending data to new activity
                i.putExtra("RepresentativeName", representative_names[position]);
                i.putExtra("party", parties[position]);
                i.putExtra("tweet", tweets[position]);
                i.putExtra("picture", pictures[position]);
                i.putExtra("website", websites[position]);
                i.putExtra("email", emails[position]);
                i.putExtra("term", terms[position]);
                i.putExtra("committee", committees[position]);
                i.putExtra("bill", bills[position]);*/

                //startActivity(i);
            }
        });
    }
}