package com.example.uom_smn;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class ListViewTrends {
    public ListViewTrends(){

    }
    public ArrayList<String> ViewTrends(Twitter twitter) throws TwitterException {
        ArrayList<String> trendList = new ArrayList<String>();
        Trends trends = twitter.getPlaceTrends(23424833);
        int counter = 0;

            for (Trend trend : trends.getTrends()) {
                if (counter < 20) {
                    trendList.add(trend.getName());
                    counter=counter+1;
                }

            }

        return trendList;

        }

}


