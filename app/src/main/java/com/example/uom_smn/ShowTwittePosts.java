package com.example.uom_smn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class ShowTwittePosts extends AppCompatActivity {

    private ArrayList<Post> PostList = new ArrayList<Post>();
    private Twitter twitter;
    private PostArrayAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_twitte_posts);
        TwitterConfig  tw = new TwitterConfig(twitter);
        twitter = tw.getTwitter();
        makePosts make = new makePosts();
        make.execute(twitter);
        ListView PostView =  findViewById(R.id.postTweets);
        postAdapter = new PostArrayAdapter(this,R.layout.post_customization,new ArrayList<Post>(), PostView);


    }
    public class makePosts extends AsyncTask<Twitter , Integer , List<Post>>{

        @Override
        protected void onPostExecute(List<Post> posts) {
            super.onPostExecute(posts);
            postAdapter.setPostList(posts);


        }

        @Override
        protected List<Post> doInBackground(Twitter... twitters) {
            ArrayList<Post> postList = new ArrayList<>();
            Intent i  = getIntent();
            String s = i.getStringExtra("selected");
            Query query = new Query(s);
            QueryResult result = null;
            try {
                result = twitter.search(query);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            for (twitter4j.Status status : result.getTweets()) {

                Post post = new Post();
                post.setUserName(status.getUser().getScreenName());

                post.setPostText(status.getText());

                if (status.getRetweetedStatus() != null) {
                    post.setPostText(status.getRetweetedStatus().getText());
                } else if (status.getRetweetedStatus() == null) {
                    post.setPostText(status.getText());
                }

                        MediaEntity[] media = status.getMediaEntities(); //get the media entities from the status
                        //search trough your entities
                        for (MediaEntity m : media) {
                            String url = m.getMediaURL();
                            Bitmap bit = post.getBitmapFromUrl(url);
                            post.setphotoBitmap(bit);
                            ImageView photo = new ImageView(ShowTwittePosts.this);
                            post.setPhoto(photo);
                            

                        }

                postList.add(post);
            }
            return postList;
        }
    }

    public ArrayList<Post> getPostList(ArrayList<Post> PostList){
        return PostList;
    }


}

