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
import android.widget.ArrayAdapter;
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
    private String url2 = "";
    public ArrayList<Map> list = new ArrayList<Map>();
    public ArrayList<String> committee_list = new ArrayList<String>();
    public String[] representative_names = new String[4];
    public static ArrayList<Representative> reps = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //receive location from MainActivity
        Intent intent = getIntent();
        String zipCode = intent.getStringExtra("ZIP_CODE");
        String mLongitude = intent.getStringExtra("LONGITUDE");
        String mLatitude = intent.getStringExtra("LATITUDE");

        //Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);

        //Request a legislators response from the provided URL
        Log.d(TAG, "zipCode value: "+zipCode);
        if(zipCode==null) {
            myUrl = "http://congress.api.sunlightfoundation.com/legislators/locate?" +
                    "latitude=" + mLatitude + "&longitude=" + mLongitude + "&apikey=" + Constants.SUNLIGHT_API;
        }
        else{
            myUrl = "http://congress.api.sunlightfoundation.com/legislators/locate?" +
                    "zip="+zipCode+"&apikey="+Constants.SUNLIGHT_API;
        }

        Log.d(TAG, "current URL: "+myUrl);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, myUrl,
                new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                list= saveData(response);
                //temporarily displaying all the gathered info
                int count = 0;
                while(count<list.size()) {
                    Log.d(TAG, "firstName:" + list.get(count).get("first_name"));
                    Log.d(TAG, "lastName:" + list.get(count).get("last_name"));
                    Log.d(TAG, "party:"+ list.get(count).get("party"));
                    count++;
                }

                /**
                 * Try to do all the work here because this is an asynchronized task
                 */

                //populate representative_names with dummy data
                int i=0;
                while(i<4){
                    representative_names[i]="dummy";
                    i++;
                }
                //clear all previous data stored in reps
                reps = new ArrayList<>();
                //store all the names in a String array and information in a Representative ArrayList
                count = 0;
                Log.d(TAG, "list.size: "+list.size());
                while(count<list.size()){
                    String firstName = (String)list.get(count).get("first_name");
                    String lastName = (String)list.get(count).get("last_name");
                    String partyTxt = (String)list.get(count).get("party");
                    String websiteTxt = (String)list.get(count).get("website");
                    String emailTxt = (String)list.get(count).get("oc_email");
                    String termStartTxt = (String)list.get(count).get("term_start");
                    String termEndTxt = (String)list.get(count).get("term_end");
                    String bioguideIDTxt = (String)list.get(count).get("bioguide_id");
                    representative_names[count] = firstName + " " + lastName;

                    Representative tempRep = new Representative();
                    tempRep.setName(firstName + " " + lastName);
                    tempRep.setParty(partyTxt);
                    tempRep.setWebsiteLink(websiteTxt);
                    tempRep.setEmailLink(emailTxt);
                    tempRep.setTermStart(termStartTxt);
                    tempRep.setTermEnd(termEndTxt);
                    tempRep.setMemberID(bioguideIDTxt);

                    reps.add(tempRep);
                    count ++;
                }

                //remove elements in representative_names that are dummy data
                String[] newNames = new String[count];
                int j = 0;
                while(j<representative_names.length){
                    if(representative_names[j]!="dummy"){
                        newNames[j] = representative_names[j];
                    }
                    j++;
                }

                //Request response from a URL
                int iteration = 0;
                String memberID = null;
                String url2 = null;

                while(iteration< reps.size()){
                    memberID = reps.get(iteration).getMemberID();
                    url2 = "http://congress.api.sunlightfoundation.com/committees?" +
                            "member_ids="+memberID+"&apikey="+Constants.SUNLIGHT_API;
                    iteration ++;
                }
                RequestQueue queue2 = Volley.newRequestQueue(Main2Activity.this);
                StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                committee_list = saveData2(response);
                                int count = 0;
                                while (count < reps.size()) {
                                    reps.get(count).setCommitteeNames(committee_list);
                                    Log.d(TAG, "committee names:" + reps.get(count).getCommitteeNames());
                                    count++;
                                }
                            }
                        }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.d(TAG, "That didn't work!");
                    }
                });

                queue2.add(stringRequest2);

                CustomListAdapter adapter =new CustomListAdapter(Main2Activity.this, newNames, reps);
                ListView lv = getListView();
                lv.setAdapter(adapter);

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

    }

    public ArrayList<Map> saveData(String result){
        ArrayList<Map> list = new ArrayList<Map>();
        try{
            JSONObject json = (JSONObject) new JSONTokener(result).nextValue();
            JSONArray json2 = json.getJSONArray("results");

            int count = 0;
            while(count < json2.length()){
                Map<String, String> map = new HashMap<String, String>();
                JSONObject json3 = json2.getJSONObject(count);
                map.put("first_name", (String) json3.get("first_name"));
                map.put("last_name", (String)json3.get("last_name"));
                map.put("party", (String)json3.get("party"));
                map.put("website", (String)json3.get("website"));
                map.put("oc_email", (String)json3.get("oc_email"));
                map.put("term_end", (String)json3.get("term_end"));
                map.put("term_start", (String)json3.get("term_start"));
                map.put("bioguide_id", (String)json3.get("bioguide_id"));
                list.add(map);
                count ++;
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<String> saveData2(String result){
        ArrayList<String> list = new ArrayList<String>();
        try{
            JSONObject json = (JSONObject) new JSONTokener(result).nextValue();
            JSONArray json2 = json.getJSONArray("results");

            int count = 0;
            while(count < json2.length()){
                JSONObject json3 = json2.getJSONObject(count);
                list.add((String)json3.get("name"));
                count ++;
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return list;
    }

}