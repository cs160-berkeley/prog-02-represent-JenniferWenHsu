package com.cs160.joleary.catnip;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

        nameText.setText(reps.get(position).getName());
        imageView.setImageResource(reps.get(position).getPicID());
        partyText.setText(reps.get(position).getParty());
        websiteText.setAutoLinkMask(Linkify.ALL);
        websiteText.setText("Personal Website:" + reps.get(position).getWebsiteLink() + System.getProperty("line.separator")
                + "Email: "+reps.get(position).getEmailLink());

        return rowView;
    };



}