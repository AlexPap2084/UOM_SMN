package com.example.uom_smn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.xml.transform.Result;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import static java.security.AccessController.getContext;

public class CreateAPost extends AppCompatActivity {
    private int RESULT_LOAD_IMAGE = 1;
    private Twitter twitter;
    private StatusUpdate statusUp;
    private File file;
    private File imgFile;
    private boolean isClear = true;
    private Bitmap imgBitmap;


    //Facebook
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private static final String EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_a_post);

        //Creating the Views!!
        ShareDialog shareDialog;
        ImageView icon_twitt = (ImageView) findViewById(R.id.twitter_icon);
        ImageView icon_facebook = (ImageView) findViewById(R.id.facebook_icon);
        ImageView icon_insta = (ImageView) findViewById(R.id.insta_icon);
        EditText messageStatus = (EditText) findViewById(R.id.messageStatus);
        ImageView image_upload = (ImageView) findViewById(R.id.imageUpload);
        Button image_clear = (Button) findViewById(R.id.image_clear);
        Button uploadImg = (Button) findViewById(R.id.uploadImg);
        Button make_Post = (Button) findViewById(R.id.make_Post);
        Button finshBtn = (Button) findViewById(R.id.finishBtn);

        CheckBox twitter_box = (CheckBox) findViewById(R.id.twitterBox);
        CheckBox facebook_box = (CheckBox) findViewById(R.id.facebookBox);
        CheckBox insta_box = (CheckBox) findViewById(R.id.instagramBox);

        //Setting the initial images and icons !!
        icon_twitt.setImageResource(R.drawable.icon_twitter);
        icon_facebook.setImageResource(R.drawable.icon_facebook);
        icon_insta.setImageResource(R.drawable.icon_instagram);
        image_upload.setImageResource(R.drawable.icon_image);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        loginButton = (LoginButton) findViewById(R.id.login_button);
        shareDialog = new ShareDialog(this);
        loginButton.setPermissions(Arrays.asList(EMAIL));
        callbackManager = CallbackManager.Factory.create();
        //Facebook login
        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Successful login !");
                    }

                    @Override
                    public void onCancel() {
                        Log.d("Canceling", "Canceled login !");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d("Error", "Error in login !");
                    }
                });

        //Clearing the image that would have been uploaded to default image
        image_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_upload.setImageResource(R.drawable.icon_image);
                isClear = true;
            }
        });

        //Returning to previous activity
        finshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Granting access to image files in the AVD
        ActivityCompat.requestPermissions(CreateAPost.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        //Selecting the image to be uploaded from image gallery
        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                isClear = false;
            }
        });

        //Making the post with the POST button !! With and without a photo
        make_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TwitterConfig wt = new TwitterConfig(twitter);
                twitter = wt.getTwitter();
                String s = messageStatus.getText().toString();

                if (twitter_box.isChecked()) {
                    try {
                        if (isClear) {
                            Status status = twitter.updateStatus(s);
                            Log.d("Success", status.getText());
                        } else {
                            statusUp = new StatusUpdate(s);
                            System.out.println(imgFile.getAbsolutePath());
                            statusUp.setMedia(imgFile);
                            twitter.updateStatus(statusUp);
                        }
                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }
                }
                if (facebook_box.isChecked()) {
                    SharePhoto sharePhoto = new SharePhoto.Builder()
                            .setBitmap(imgBitmap)
                            .build();

                    SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder()
                            .addPhoto(sharePhoto)
                            .build();


                        shareDialog.show(sharePhotoContent);
                }
                if(insta_box.isChecked()){
                    String type = "image/*";
                    createInstagramIntent(type,imgFile);
                }
            }
        });

    }
    //Overriding methods
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                callbackManager.onActivityResult(requestCode, resultCode , data);

               if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    file = new File(String.valueOf(filePathColumn));
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    imgFile = new File(picturePath);
                    cursor.close();
                    ImageView image_upload = findViewById(R.id.imageUpload);
                    imgBitmap = BitmapFactory.decodeFile(picturePath);
                    image_upload.setImageBitmap(BitmapFactory.decodeFile(picturePath));


                }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Success" , "Permission granted");
                }

                else
                    Toast.makeText(CreateAPost.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
    }
    private void createInstagramIntent(String type, File imgFile){

        // Create the new Intent using the 'Send' action.
        Intent share = new Intent(Intent.ACTION_SEND);

        // Set the MIME type
        share.setType(type);

        // Create the URI from the media
        Uri uri = Uri.fromFile(imgFile);

        // Add the URI to the Intent.
        share.putExtra(Intent.EXTRA_STREAM, uri);

        // Broadcast the Intent.
        startActivity(Intent.createChooser(share, "Share to"));
    }

}

