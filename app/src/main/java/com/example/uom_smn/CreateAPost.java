package com.example.uom_smn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.InputStream;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import static java.security.AccessController.getContext;

public class CreateAPost extends AppCompatActivity {
    private int RESULT_LOAD_IMAGE = 1;
    ImageView image_upload;
    private Twitter twitter;
    private StatusUpdate statusUp;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_a_post);
        //Creating the Views!!
        ImageView icon_twitt = (ImageView)findViewById(R.id.twitter_icon);
        ImageView icon_facebook = (ImageView)findViewById(R.id.facebook_icon);
        ImageView icon_insta = (ImageView)findViewById(R.id.insta_icon);
        EditText messageStatus = (EditText)findViewById(R.id.messageStatus);
        image_upload = (ImageView)findViewById(R.id.imageUpload);
        Button image_clear = (Button)findViewById(R.id.image_clear);
        Button uploadImg = (Button)findViewById(R.id.uploadImg);
        Button make_Post = (Button)findViewById(R.id.make_Post);
        CheckBox twitter_box = (CheckBox)findViewById(R.id.twitterBox);
        CheckBox facebook_box = (CheckBox)findViewById(R.id.facebookBox);
        CheckBox insta_box = (CheckBox)findViewById(R.id.instagramBox);
        //Setting the initial images and icons !!
        icon_twitt.setImageResource(R.drawable.icon_twitter);
        icon_facebook.setImageResource(R.drawable.icon_facebook);
        icon_insta.setImageResource(R.drawable.icon_instagram);
        image_upload.setImageResource(R.drawable.icon_image);


        image_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_upload.setImageResource(R.drawable.icon_image);
            }
        });

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        make_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TwitterConfig wt = new TwitterConfig(twitter);
                twitter = wt.getTwitter();

                String s  = messageStatus.getText().toString();

                if(twitter_box.isChecked()){
                    try {
                        Status status = twitter.updateStatus(s);
                        Log.d( "Success" ,status.getText());
                        //statusUp = new StatusUpdate(s);
                        //statusUp.setMedia(file);
                        //twitter.updateStatus(statusUp);
                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }
                }
            }
        });





    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            file = new File(String.valueOf(filePathColumn));
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            image_upload.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }


}