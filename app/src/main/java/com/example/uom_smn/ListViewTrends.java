package com.example.uom_smn;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;



public class ListViewTrends {

    public ArrayList<String> ListViewTrends(Twitter twitter) throws TwitterException {
        ArrayList<String> trendList = new ArrayList<String>();
        Trends  trends = twitter.getPlaceTrends(23424833);
        for(Trend trend :trends.getTrends())
    {
        trendList.add(trend.getName());
    }
        return trendList;
}

}
