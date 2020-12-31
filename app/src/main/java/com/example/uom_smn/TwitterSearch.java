package com.example.uom_smn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

    private Twitter twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);
        TwitterConfig  tw = new TwitterConfig(twitter);
        twitter = tw.getTwitter();
        EditText simpleSearchView =  findViewById(R.id.textHash);// inititate a search view
        Button searchBtn  = (Button)findViewById(R.id.btnSearch);
        Button bckButton = (Button)findViewById(R.id.bckButton);
        ListViewTrends trendingList = new ListViewTrends();
        try {

            ArrayList<String> hashtags;
            ArrayList<String> hastags = trendingList.ListViewTrends(twitter);
            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,hastags);
            ListView listView = (ListView) findViewById(R.id.listHashtag);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selected = listView.getItemAtPosition(position).toString();
                    Intent i = new Intent(TwitterSearch.this , ShowTwittePosts.class);
                    i.putExtra("selected" , selected);
                    startActivity(i);
                }
            });
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String selected =  simpleSearchView.getText().toString();
                Intent i = new Intent(TwitterSearch.this , ShowTwittePosts.class);
                i.putExtra("selected" , selected);
                startActivity(i);
            }
        });

        bckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}