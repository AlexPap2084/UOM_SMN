package com.example.uom_smn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterSearch extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("BfXEUVTUWVG3uVrXIiy3xbJJt")
                .setOAuthConsumerSecret("vecPtkCzp33YaBue15UtG6OcH9esyPQuQLH2etHd6Gd5QAZdvb")
                .setOAuthAccessToken("1335954314259554304-w3gLieiF97QBUXIrcNyN3FNS3cyiH1")
                .setOAuthAccessTokenSecret("ClFS1vnCBHMpHoqbGhSpQP3NpTIfHwzGmL1Mg6w1M7V2R");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter;
        twitter = tf.getInstance();
        TextView simpleSearchView = (TextView) findViewById(R.id.searchView);// inititate a search view
        Button searchBtn  = (Button)findViewById(R.id.btnSearch);

        ListViewTrends trendingList = new ListViewTrends();
        try {

            ArrayList<String> hashtags;
            ArrayList<String> hastags = trendingList.ListViewTrends(twitter);
            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,hastags);
            ListView listView = (ListView) findViewById(R.id.listHashtag);
            listView.setAdapter(adapter);
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        //CharSequence tweetHashSearch = simpleSearchView.getQuery(); // get the query string currently in the text field

        /*
        Query query = new Query("Tsipras");
        QueryResult result = null;
        try {
            result = twitter.search(query);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        for (Status status : result.getTweets()) {
            System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
        }

        */


    }
}