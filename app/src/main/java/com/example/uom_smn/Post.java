package com.example.uom_smn;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Post {
    private ImageView icon;
    private String photo;
    private String postText;
    private String userName;
    private Bitmap iconBitmap;
    private Bitmap photoBitmap;
    private long tweetId;

    @NonNull
    @Override
    public String toString() {
        return " Username:" + userName + " Post text" + postText;
    }
    //Getters
    public ImageView getIcon(){
        return icon;
    }
    public String getphoto(){
        return photo;
    }
    public String getPostText(){
        return postText;
    }
    public String getUserName(){
        return userName;
    }
    public Bitmap getIconBitmap(){ return iconBitmap;}
    public Bitmap getPhotoBitmap(){ return photoBitmap;}
    public long getTweetId(){return tweetId;}
    //Setters
    public void setIcon(ImageView icon){
        this.icon = icon;
    }
    public void setPhoto(String photo){
        this.photo = photo;
    }
    public void setphotoBitmap( Bitmap photoBitmap){ this.photoBitmap = photoBitmap; }
    public void setPostText(String postText){
        this.postText = postText;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setTweetId(long tweetId){ this.tweetId = tweetId;}
    public Bitmap getBitmapFromUrl(String src){
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }


}
