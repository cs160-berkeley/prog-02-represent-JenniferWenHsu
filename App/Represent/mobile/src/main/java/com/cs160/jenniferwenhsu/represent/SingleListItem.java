package com.cs160.jenniferwenhsu.represent;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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
        imgPic.setImageResource(Main2Activity.reps.get(position).getPicID());
        txtContact.setText("Personal Website: " + Main2Activity.reps.get(position).getWebsiteLink() +
                "\nEmail: " + Main2Activity.reps.get(position).getEmailLink());
        String termStart = Main2Activity.reps.get(position).getTermStart();
        String termEnd = Main2Activity.reps.get(position).getTermEnd();
        txtTerm.setText(termStart + " ~ "+termEnd);
        txtCommittee.setText(Main2Activity.reps.get(position).getCommittee());
        txtBill.setText(Main2Activity.reps.get(position).getBill());
    }
}