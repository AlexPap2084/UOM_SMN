package com.example.uom_smn;

import twitter4j.MediaEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterConfig {
    private Twitter twitter;
    public TwitterConfig(Twitter twitter){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("BfXEUVTUWVG3uVrXIiy3xbJJt")
                .setOAuthConsumerSecret("vecPtkCzp33YaBue15UtG6OcH9esyPQuQLH2etHd6Gd5QAZdvb")
                .setOAuthAccessToken("1335954314259554304-Tauw0mFgtjwFQauztODF5yP3ISTXEf")
                .setOAuthAccessTokenSecret("mJfzpwLGf9YHJE8FuWpH9JI6t7suzYRC3Dou39Hf3UWeN");
        TwitterFactory tf = new TwitterFactory(cb.build());
        this.twitter = tf.getInstance();

    }
    public Twitter getTwitter(){
        return twitter;
    }
    public void setTwitter(Twitter twitter) {
        this.twitter = twitter;
    }
}
