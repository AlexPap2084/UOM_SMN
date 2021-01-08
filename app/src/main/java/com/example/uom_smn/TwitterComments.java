package com.example.uom_smn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TwitterComments extends AppCompatActivity {
    private Twitter twitter;
    private PostArrayAdapter commentAdapter;
    //private ArrayList<Status> commnets;
    private ArrayList<Post> commentPost = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_comments);
        Button btnBack = (Button)findViewById(R.id.btnBck);
        TwitterConfig  tw = new TwitterConfig(twitter);
        twitter = tw.getTwitter();
        TwitterComments.make make = new TwitterComments.make();
        make.execute(twitter);
        ListView commentView =  findViewById(R.id.commentPosts);

        commentAdapter = new PostArrayAdapter(this,R.layout.post_customization,commentPost, commentView);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public class make extends AsyncTask<Twitter , Integer , List<Post>> {

        @Override
        protected void onPostExecute(List<Post> posts) {
            super.onPostExecute(posts);
            commentAdapter.setPostList(posts);

        }

        @Override
        protected List<Post> doInBackground(Twitter... twitters) {
            Intent i  = getIntent();
            String s = i.getStringExtra("selected username");
            long twitterId = 0;
            long id = i.getLongExtra("selected id" , twitterId);

            ArrayList<twitter4j.Status> arrayList = null;
            try {
                arrayList = (ArrayList<twitter4j.Status>) getReplies(s, id);
            } catch (TwitterException e) {
                e.printStackTrace();
            }

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

            return commentPost;
        }
    }
    public ArrayList<Status> getReplies(String screenName, long tweetID) throws TwitterException {
        ArrayList<Status> replies = new ArrayList<>();
        ArrayList<Status> all = null;
        try {
            Query query = new Query(screenName);
            query.setSinceId(tweetID);
            try {
                query.setCount(100);
            } catch (Throwable e) {
                query.setCount(30);
            }

            QueryResult results;


            results = twitter.search(query);
            System.out.println("Results: " + results.getTweets().size());
            do {
                List<twitter4j.Status> tweets = results.getTweets();

                for (Status tweet : tweets) {
                    if (tweet.getInReplyToStatusId() == tweetID)
                        replies.add(tweet);
                }
                if (all.size() > 0) {
                    for (int i = all.size() - 1; i >= 0; i--)
                        replies.add(all.get(i));
                    all.clear();
                }

                query = results.nextQuery();
                if (query != null)
                    results = twitter.search(query);

            } while (query != null);


        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return replies;
    }
    public ArrayList<Status> getComments(ArrayList<Status> comments){
        return comments;
    }

}
