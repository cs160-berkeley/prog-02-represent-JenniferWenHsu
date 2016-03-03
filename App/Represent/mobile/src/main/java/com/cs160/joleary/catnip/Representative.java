package com.cs160.joleary.catnip;

/**
 * Created by apple on 2/26/16.
 */
public class Representative
{

    private String name;
    private String party;
    private String tweetMessage;
    private String emailLink;
    private String websiteLink;
    private String term;
    private String committee;

    public int getPicID() {
        return picID;
    }

    public void setPicID(int picID) {
        this.picID = picID;
    }

    private int picID;


    public void setTerm(String term) {
        this.term = term;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public void setCommittee(String committee) {
        this.committee = committee;
    }

    private String bill;

    public String getTerm() {
        return term;
    }

    public String getCommittee() {
        return committee;
    }

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
