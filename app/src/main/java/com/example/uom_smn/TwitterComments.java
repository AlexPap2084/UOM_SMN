package com.example.uom_smn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import twitter4j.MediaEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TwitterComments extends AppCompatActivity {
    private Twitter twitter;
    private PostArrayAdapter postAdapter;
    private ArrayList<Post> commentPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_comments);
        Button btnBack = (Button)findViewById(R.id.back);
        TwitterConfig  tw = new TwitterConfig(twitter);
        twitter = tw.getTwitter();
        TwitterComments.makePosts make = new TwitterComments.makePosts();
        make.execute(twitter);
        ListView commentView =  findViewById(R.id.commentPosts);
        postAdapter = new PostArrayAdapter(this,R.layout.post_customization,new ArrayList<Post>(), commentView);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
    public class makePosts extends AsyncTask<Twitter , Integer , List<Post>> {

        @Override
        protected void onPostExecute(List<Post> posts) {
            super.onPostExecute(posts);
            postAdapter.setPostList(posts);

        }

        @Override
        protected List<Post> doInBackground(Twitter... twitters) {
            ArrayList<Post> postList = new ArrayList<>();
            ArrayList<twitter4j.Status> arrayList = new ArrayList<>();
            Intent i  = getIntent();
            String s = i.getStringExtra("selected username");
            long twitterId = 0;
            long id = i.getLongExtra("selected id" , twitterId);

            /*Query query = new Query(s);
            QueryResult result = null;
            try {
                result = twitter.search(query);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            for (twitter4j.Status status : result.getTweets()) {
            */


                arrayList = (ArrayList<twitter4j.Status>) getReplies(s,id);

                for(twitter4j.Status status1 : arrayList){
                    Post twittercoments = new Post();
                    twittercoments.setUserName(status1.getUser().getScreenName());
                    twittercoments.setPostText(status1.getText());
                    if (status1.getRetweetedStatus() != null) {
                        twittercoments.setPostText(status1.getRetweetedStatus().getText());
                    } else {
                        twittercoments.setPostText(status1.getText());
                    }
                    commentPost.add(twittercoments);
                }

                /*Post post = new Post();

                post.setUserName(status.getUser().getScreenName());

                post.setPostText(status.getText());

                if (status.getRetweetedStatus() != null) {
                    post.setPostText(status.getRetweetedStatus().getText());
                } else {
                    post.setPostText(status.getText());
                }

                MediaEntity[] media = status.getMediaEntities(); //get the media entities from the status
                //search trough your entities
                for (MediaEntity m : media) {
                    String url = m.getMediaURL();
                    Bitmap bit = post.getBitmapFromUrl(url);
                    post.setphotoBitmap(bit);
                    post.setPhoto(url);


                }

                postList.add(post);
                */

            //}
            return commentPost;
        }
    }
    public ArrayList<Status> getReplies(String screenName, long tweetID) {
        ArrayList<Status> replies = new ArrayList<>();

        try {
            Query query = new Query("to:" + screenName + " since_id:" + tweetID);
            QueryResult results;

            do {
                results = twitter.search(query);
                System.out.println("Results: " + results.getTweets().size());
                List<Status> tweets = results.getTweets();

                for (Status tweet : tweets)
                    if (tweet.getInReplyToStatusId() == tweetID)
                        replies.add(tweet);
            } while ((query = results.nextQuery()) != null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return replies;
    }
}
