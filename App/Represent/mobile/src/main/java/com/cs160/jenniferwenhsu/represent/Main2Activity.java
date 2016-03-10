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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class Main2Activity extends ListActivity {
    private String TAG ="Main2Activity";
    private String url = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //receive location (zip code) from MainActivity
        Intent intent = getIntent();
        String zipCode = intent.getStringExtra("ZIP_CODE");
        String mLongitude = intent.getStringExtra("LONGITUDE");
        String mLatitude = intent.getStringExtra("LATITUDE");

        url = "http://congress.api.sunlightfoundation.com/legislators/locate?" +
                "latitude="+mLatitude+"&longitude="+mLongitude+"&apikey="+Constants.SUNLIGHT_API;

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            //fetch data
            new DownloadWebpageTask().execute(url);
        }
        else{
            //display error
            Log.d(TAG, "No network connection available.");
        }


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

    /**
     * Uses AsyncTask to create a task away from the main UI thread. This task takes a
     * URL string and uses it to create an HttpUrlConnection. Once the connection
     * has been established, the AsyncTask downloads the contents of the webpage as
     * an InputStream. Finally, the InputStream is converted into a string, which is
     * displayed in the UI by the AsyncTask's onPostExecute method.
     * The details should be changed afterwards
     */

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url){
            //params comes from the execute() call: params[0] is the url.
            try{
                //this is where we parse it
                return downloadUrl(url[0]);
            }
            catch(IOException e){
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String result){
            Log.d(TAG, result);
        }
    }

    /**
     * Given a URL, establishes an HttpUrlConnection and retrieves
     * the web page content as a InputStream, which it returns as
     * a string
     */

    private String downloadUrl(String myUrl) throws  IOException{
        InputStream is = null;
        /**
         * Only display the first 50 characters of the retrieved
         * web page content.
         */

        int len = 50;

        try{
            URL url = new URL(myUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            //Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(TAG, "The response is: "+response);
            is = conn.getInputStream();


            //convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;
            /**
             * Makes sure that the InputStream is closed after the app is
             * finished using it.
             */
        }finally{
            if(is!=null){
                is.close();
            }
        }
    }

    /**
     * Reads an InputStream and converts it to a String
     */
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException{
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
/*
    public Representative readJsonStream(InputStream in) throws IOException{
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try{
            return readFromJson(reader);
        }
        finally{
            reader.close();
        }
    }

    public static Representative readFromJson( JsonReader reader ) throws IOException
    {
        Representative rep = new Representative();

        reader.beginObject();
        while( reader.hasNext() )
        {
            String name = reader.nextName();
            if( name.equals("first_name") )
            {
                rep.setFirstName(name);
            }
            else if( name.equals("last_name") )
            {
                rep.setLastName(name);
            }
            else if( name.equals("party"))
            {
                rep.setParty(name);
            }
            else
            {
                reader.skipValue();
            }
        }
        reader.endObject();

        return rep;
    }
*/


}