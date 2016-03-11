package com.cs160.jenniferwenhsu.represent;

import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.renderscript.ScriptGroup;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main2Activity extends ListActivity {
    private String TAG ="Main2Activity";
    private String myUrl = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //receive location (zip code) from MainActivity
        Intent intent = getIntent();
        String zipCode = intent.getStringExtra("ZIP_CODE");
        String mLongitude = intent.getStringExtra("LONGITUDE");
        String mLatitude = intent.getStringExtra("LATITUDE");

        myUrl = "http://congress.api.sunlightfoundation.com/legislators/locate?" +
                "latitude="+mLatitude+"&longitude="+mLongitude+"&apikey="+Constants.SUNLIGHT_API;

        //Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);

        //Request a string response from the provided URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, myUrl,
                new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                //Display the first 500 characters of the response string.
                //Log.d(TAG, "RESPONSE:"+response.substring(0,500));
                String first_name = saveData(response);
                Log.d(TAG, "First Name:"+first_name);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.d(TAG, "That didn't work!");
            }
        });
        //Add the request to the RequestQueue.
        queue.add(stringRequest);




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

    private String saveData(String result){
        Map<String, String> map = new HashMap<String, String>();
        try{
            JSONObject json = (JSONObject) new JSONTokener(result).nextValue();
            JSONArray json2 = json.getJSONArray("results");

            //access the first representative
            JSONObject json_0 = json2.getJSONObject(0);
            map.put("first_name0", (String)json_0.get("first_name"));
            map.put("last_name0", (String)json_0.get("last_name"));
            map.put("party0", (String)json_0.get("party"));
            
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return first_name;
    }


}