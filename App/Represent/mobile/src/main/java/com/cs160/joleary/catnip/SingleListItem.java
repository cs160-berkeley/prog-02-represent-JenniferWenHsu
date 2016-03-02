package com.cs160.joleary.catnip;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SingleListItem extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.single_list_item_view);


        TextView txtName = (TextView) findViewById(R.id.name);
        TextView txtContact = (TextView) findViewById(R.id.txtContact);
        TextView txtParty = (TextView) findViewById(R.id.party);
        TextView txtTerm = (TextView) findViewById(R.id.txtTerm);
        TextView txtCommittee = (TextView) findViewById(R.id.txtCommittee);
        TextView txtBill = (TextView) findViewById(R.id.txtBill);
        ImageView imgPic = (ImageView) findViewById(R.id.picture);

        Bundle bundle=this.getIntent().getExtras();
        int pic=bundle.getInt("image");
        String name = bundle.getString("RepresentativeName");
        String party = bundle.getString("party");
        String website = bundle.getString("website");
        String email = bundle.getString("email");
        String term = bundle.getString("term");
        String committee = bundle.getString("committee");
        String bill = bundle.getString("bill");

        // displaying selected item information
        txtName.setText(name);
        txtParty.setText(party);
        imgPic.setImageResource(pic);
        txtContact.setText("Personal Website: "+website+"\nEmail: "+email);
        txtTerm.setText(term);
        txtCommittee.setText(committee);
        txtBill.setText(bill);
    }
}