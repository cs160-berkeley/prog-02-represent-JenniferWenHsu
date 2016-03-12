package com.cs160.jenniferwenhsu.represent;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

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

        Intent pos = getIntent();
        String posString = pos.getStringExtra("POSITION");
        int position = Integer.parseInt(posString);


        // displaying selected item information
        txtName.setText(Main2Activity.reps.get(position).getName());
        txtParty.setText(Main2Activity.reps.get(position).getParty());

        //add design to the partyText
        String party = Main2Activity.reps.get(position).getParty();
        if(party.equals("D")){
            txtParty.setText("Democratic");
            txtParty.setTextColor(Color.BLUE);
        }
        else if(party.equals("R")){
            txtParty.setText("Republican");
            txtParty.setTextColor(Color.RED);
        }
        else{
            txtParty.setText("Independent");
            txtParty.setTextColor(Color.BLACK);
        }
        imgPic.setImageResource(Main2Activity.reps.get(position).getPicID());
        txtContact.setText("Personal Website: " + Main2Activity.reps.get(position).getWebsiteLink() +
                "\nEmail: " + Main2Activity.reps.get(position).getEmailLink());
        String termStart = Main2Activity.reps.get(position).getTermStart();
        String termEnd = Main2Activity.reps.get(position).getTermEnd();
        txtTerm.setText(termStart + " ~ "+termEnd);

        /**
         * add design to the Committee
         */
        int count = 0;
        ArrayList<String> committee_list = Main2Activity.reps.get(position).getCommitteeNames();
        while(count< committee_list.size()){
            String result = committee_list.get(count)+ "\n";
            txtCommittee.setText(result);
            count++;
        }

        /**
         * displayig Bills
         */
        count = 0;
        ArrayList<String> bill_list = Main2Activity.reps.get(position).getBillNames();
        ArrayList<String> date_list = Main2Activity.reps.get(position).getIntroducedDates();
        while(count< bill_list.size()){
            String result = bill_list.get(count)+ "\n - "+date_list.get(count);
            txtBill.setText(result);
            count++;
        }
        //txtBill.setText(Main2Activity.reps.get(position).getBill());
    }
}