package com.cs160.jenniferwenhsu.represent;

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
    private String committee;

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

    public void setCommittee(String committee) {
        this.committee = committee;
    }

    private String bill;


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
