package com.example.uom_smn;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import twitter4j.HttpParameter;
import twitter4j.MediaEntity;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterResponse;

public class ShowTwittePosts extends AppCompatActivity {

    private List<Post> PostList = new ArrayList<>();
    private Twitter twitter;
    private PostArrayAdapter postAdapter;
    private static HttpParameter WITH_TWITTER_USER_ID;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_twitte_posts);
        Button btnBack = (Button)findViewById(R.id.btnBack);
        TwitterConfig  tw = new TwitterConfig(twitter);
        twitter = tw.getTwitter();
        ListView PostView =  findViewById(R.id.twitterPosts);
        ShowTwittePosts.makePosts make = new ShowTwittePosts.makePosts();

        make.execute(twitter);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        postAdapter = new PostArrayAdapter(this,R.layout.post_customization,PostList, PostView);


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


                Post post = (Post)PostView.getItemAtPosition(position);

                String s = post.getUserName();
                long tweetId = post.getTweetId();


                int arr = 0;
                try {
                    arr = getReplies(s, tweetId).size();
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
                System.out.println("The size of the array is : " + arr);
                if(arr != 0){
                    Intent i = new Intent(ShowTwittePosts.this , TwitterComments.class);
                    i.putExtra("selected username" , s);
                    i.putExtra("selected id" ,tweetId);
                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Sorry , no comments for this post ! ",Toast.LENGTH_SHORT).show();
                }
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
            List<Post> postList = new ArrayList<>();

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
                            if(m.getType().equals("photo")){
                                String url = m.getMediaURL();
                                post.getBitmapFromURL(url);
                            }

                        }

                postList.add(post);

            }
            return postList;
        }
    }

    public ArrayList<Status> getReplies(String screenName, long tweetID) throws TwitterException {

        List<Status> replyList = twitter.getMentionsTimeline(new Paging(1, 800));

        ArrayList<Status> all = null;

        try {
            Query query = new Query(screenName);
            query.setSinceId(tweetID);

            System.out.println(screenName);
            System.out.println(query);
            try {
                query.setCount(100);
            } catch (Throwable e) {
                query.setCount(30);
            }

                QueryResult results;


                results = twitter.search(query);
                System.out.println("Results: " + results.getTweets().size());
                all = new ArrayList<Status>();

                do {
                    List<twitter4j.Status> tweets = results.getTweets();

                    for (Status tweet : tweets) {
                        if (tweet.getInReplyToStatusId() == tweetID)
                            replyList.add(tweet);
                    }
                    if (all.size() > 0) {
                        for (int i = all.size() - 1; i >= 0; i--)
                            replyList.add(all.get(i));
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
            return (ArrayList<Status>) replyList;
        }

    public ArrayList<Post> getPostList(ArrayList<Post> PostList){
        return PostList;
    }

}

