package com.example.uom_smn;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import twitter4j.MediaEntity;
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
        TwitterConfig tw = new TwitterConfig(twitter);
        twitter = tw.getTwitter();
        //Αρχικοποίηση στα objects του xml
        EditText simpleSearchView = (EditText) findViewById(R.id.textHash);// inititate a search view
        Button searchBtn = (Button) findViewById(R.id.btnSearch);



        ListViewTrends trendingList = new ListViewTrends();

        try {

            ArrayList<Post> postList = new ArrayList<>();
            ArrayList<String> hastags = trendingList.ListViewTrends(twitter);

            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, hastags);

            ListView listView = (ListView) findViewById(R.id.listHashtag);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Επιλογή του hashtag , εύρεση των status και δημιουργία της PostList
                    int itemPosition = position;
                    String selected = (String) listView.getItemAtPosition(itemPosition);
                    Intent i = new Intent(TwitterSearch.this , ShowTwittePosts.class);
                    i.putExtra("selected" ,selected);
                    startActivity(i);

                }

            });
        //Αναζήτηση του χρήστη μέσω κουμπιού
            searchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String selected = simpleSearchView.getText().toString();
                    Intent i = new Intent(TwitterSearch.this , ShowTwittePosts.class);
                    i.putExtra("selected" ,selected);
                    startActivity(i);
                }
            });

        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    public void openShowTwitterPosts(){




    }


}