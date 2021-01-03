package com.example.uom_smn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private static final String EMAIL = "email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_to_app);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Button btn = (Button) findViewById(R.id.btntwitt);
        Button btnPost =  (Button)findViewById(R.id.btnPost);


        btn.setOnClickListener(new View.OnClickListener()
        {
            
            @Override
            public void onClick(View v)
            {
               openTwitterSearch();
            }

        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateAPost();
            }
        });


    }
    public void openTwitterSearch() {

        startActivity(new Intent(MainActivity.this, TwitterSearch.class));

    }
    public void openCreateAPost(){
        startActivity(new Intent(MainActivity.this , CreateAPost.class));
    }
    /*public void openFacebook(){
        startActivity(new Intent(MainActivity.this , FacebookActivity.class));
    }*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }

}