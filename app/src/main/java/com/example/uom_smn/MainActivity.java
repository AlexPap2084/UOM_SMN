package com.example.uom_smn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/*
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
*/
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_to_app);
        /*ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("BfXEUVTUWVG3uVrXIiy3xbJJt")
                .setOAuthConsumerSecret("vecPtkCzp33YaBue15UtG6OcH9esyPQuQLH2etHd6Gd5QAZdvb")
                .setOAuthAccessToken("1335954314259554304-w3gLieiF97QBUXIrcNyN3FNS3cyiH1")
                .setOAuthAccessTokenSecret("ClFS1vnCBHMpHoqbGhSpQP3NpTIfHwzGmL1Mg6w1M7V2R");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();*/
        Button btn = (Button) findViewById(R.id.btntwitt);
        btn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
               openTwitterSearch();
            }

        });



    }
    public void openTwitterSearch() {
        Intent myIntent = new Intent(MainActivity.this, Twitter.class);
        startActivity(myIntent);

    }

}