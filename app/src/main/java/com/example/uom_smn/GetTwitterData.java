package com.example.uom_smn;

import android.os.AsyncTask;

import java.util.List;

import twitter4j.Twitter;

public class GetTwitterData  {
    private Twitter twitter;
    private String selectedString;
    public GetTwitterData(Twitter twitter , String selectedString){
        TwitterConfig tw = new TwitterConfig(twitter);
        this.twitter = tw.getTwitter();
        this.selectedString = selectedString;

    }


}
