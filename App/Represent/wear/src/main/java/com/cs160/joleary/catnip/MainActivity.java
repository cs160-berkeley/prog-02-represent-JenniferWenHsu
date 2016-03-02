package com.cs160.joleary.catnip;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends Activity {
    GridView gv;
    Context context;
    ArrayList prgmName;
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
                CharSequence text = "View "+nameList[position]+ "on Phone";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
                sendIntent.putExtra("POSITION", position);
                startService(sendIntent);
            }
        });

    }

}