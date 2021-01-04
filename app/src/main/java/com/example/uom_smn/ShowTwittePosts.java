package com.example.uom_smn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
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

public class ShowTwittePosts extends AppCompatActivity {

    private ArrayList<Post> PostList = new ArrayList<Post>();
    private Twitter twitter;
    private PostArrayAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_twitte_posts);
        Button btnBack = (Button)findViewById(R.id.btnBack);
        TwitterConfig  tw = new TwitterConfig(twitter);
        twitter = tw.getTwitter();
        ListView PostView =  findViewById(R.id.commentPosts);
        makePosts make = new makePosts();
        make.execute(twitter);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        postAdapter = new PostArrayAdapter(this,R.layout.post_customization,PostList, PostView);
        PostView.setAdapter(postAdapter);

        //new ArrayList<Post>()
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        PostView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selected = PostView.getItemAtPosition(position).toString();
                long tweetId = postAdapter.getItem(position).getTweetId();
                Intent i = new Intent(ShowTwittePosts.this , TwitterComments.class);
                i.putExtra("selected username" , selected);
                i.putExtra("selected id" ,tweetId);
                startActivity(i);
            }
        });

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
                post.setTweetId(status.getId());
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

            }
            return postList;
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


    public ArrayList<Post> getPostList(ArrayList<Post> PostList){
        return PostList;
    }


}

