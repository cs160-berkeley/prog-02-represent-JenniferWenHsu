package com.cs160.jenniferwenhsu.represent;

import java.util.ArrayList;

/**
 * Created by apple on 2/26/16.
 */
public class Representative
{

    private String name;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String firstName;
    private String lastName;
    private String party;
    private String tweetMessage;
    private String emailLink;
    private String websiteLink;
    private String termEnd;

    public ArrayList<String> getIntroducedDates() {
        return introducedDates;
    }

    public void setIntroducedDates(ArrayList<String> introducedDates) {
        this.introducedDates = introducedDates;
    }

    private ArrayList<String> introducedDates;
    public ArrayList<String> getBillNames() {
        return billNames;
    }

    public void setBillNames(ArrayList<String> billNames) {
        this.billNames = billNames;
    }



    private ArrayList<String> billNames = new ArrayList<String>();

    public ArrayList<String> getCommitteeNames() {
        return committeeNames;
    }

    public void setCommitteeNames(ArrayList<String> committeeNames) {
        this.committeeNames = committeeNames;
    }

    private ArrayList<String> committeeNames = new ArrayList<String>();

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    private String memberID;

    public String getTermStart() {
        return termStart;
    }

    public void setTermStart(String termStart) {
        this.termStart = termStart;
    }

    public String getTermEnd() {
        return termEnd;
    }

    public void setTermEnd(String termEnd) {
        this.termEnd = termEnd;
    }

    private String termStart;

    public int getPicID() {
        return picID;
    }

    public void setPicID(int picID) {
        this.picID = picID;
    }

    private int picID;


    public void setBill(String bill) {
        this.bill = bill;
    }


    private String bill;


    public String getBill() {
        return bill;
    }


    public Representative(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getTweetMessage() {
        return tweetMessage;
    }

    public void setTweetMessage(String tweetMessage) {
        this.tweetMessage = tweetMessage;
    }

    public String getEmailLink() {
        return emailLink;
    }

    public void setEmailLink(String emailLink) {
        this.emailLink = emailLink;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }



}
