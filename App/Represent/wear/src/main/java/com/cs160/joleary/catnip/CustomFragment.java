package com.cs160.joleary.catnip;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by apple on 3/7/16.
 */
public class CustomFragment extends CardFragment{

    public static CustomFragment create(CharSequence name, CharSequence party) {
        CustomFragment fragment = new CustomFragment();
        Bundle args = new Bundle();
        if (name != null) {
            args.putCharSequence("CustomFragment_name", name);
        }

        if (party != null) {
            args.putCharSequence("CustomFragment_party", party);
        }
        fragment.setArguments(args);
        return fragment;
    }

    //overwrite onCreateContentView
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.my_custom_card, container, false);
        Bundle args = this.getArguments();
        if (args != null) {
            TextView name = (TextView) view.findViewById(R.id.name);
            if (args.containsKey("CustomFragment_name") && name != null) {
                name.setText(args.getCharSequence("CustomFragment_name"));
            }

            if (args.containsKey("CustomFragment_party")) {
                TextView party = (TextView) view.findViewById(R.id.party);
                if (party != null) {
                    party.setText(args.getCharSequence("CustomFragment_party"));
                }
            }
        }


        return view;
    }


}
