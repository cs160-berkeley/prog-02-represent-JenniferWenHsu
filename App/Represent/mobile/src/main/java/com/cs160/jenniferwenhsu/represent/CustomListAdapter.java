package com.cs160.jenniferwenhsu.represent;

import android.app.Activity;
import android.graphics.Color;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs160.jenniferwenhsu.represent.R;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private ArrayList<Representative> reps= new ArrayList<>();
    private final String[] names;

    public CustomListAdapter(Activity context, String[] names, ArrayList<Representative> reps) {
        super(context, R.layout.activity_main2, names);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.names = names;
        this.reps = reps;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.my_list, null, true);

        TextView nameText = (TextView) rowView.findViewById(R.id.name);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView partyText = (TextView) rowView.findViewById(R.id.partyTextView);
        TextView websiteText = (TextView) rowView.findViewById(R.id.linkTextView);

        nameText.setText(names[position]);
        imageView.setImageResource(reps.get(position).getPicID());

        //add design to the partyText
        String party = reps.get(position).getParty();
        if(party.equals("D")){
            partyText.setText("Democratic");
            partyText.setTextColor(Color.BLUE);
        }
        else if(party.equals("R")){
            partyText.setText("Republican");
            partyText.setTextColor(Color.RED);
        }
        else{
            partyText.setText("Independent");
            partyText.setTextColor(Color.BLACK);
        }
        websiteText.setAutoLinkMask(Linkify.ALL);
        websiteText.setText("Personal Website:" + reps.get(position).getWebsiteLink() + System.getProperty("line.separator")
                + "Email: "+reps.get(position).getEmailLink());

        return rowView;
    };



}