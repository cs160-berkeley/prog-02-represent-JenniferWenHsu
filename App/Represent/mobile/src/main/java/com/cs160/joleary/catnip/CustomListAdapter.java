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

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] names;
    private final Integer[] imgid;
    private final String[] party;
    private final String[] tweet;
    private final String[] personalWebsite;
    private final String[] email;


    public CustomListAdapter(Activity context, String[] names, Integer[] images, String[] party,
                             String[] tweet, String[] personalWebsite, String[] email) {
        super(context, R.layout.activity_main2, names);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.names = names;
        this.imgid = images;
        this.party = party;
        this.tweet = tweet;
        this.personalWebsite = personalWebsite;
        this.email = email;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.my_list, null, true);

        TextView nameText = (TextView) rowView.findViewById(R.id.name);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView partyText = (TextView) rowView.findViewById(R.id.partyTextView);
        TextView websiteText = (TextView) rowView.findViewById(R.id.linkTextView);

        nameText.setText(names[position]);
        imageView.setImageResource(imgid[position]);
        partyText.setText(party[position]);
        websiteText.setAutoLinkMask(Linkify.ALL);
        websiteText.setText("Personal Website:" +personalWebsite[position] + System.getProperty("line.separator")
                + "Email: "+email[position]);

        return rowView;
    };



}