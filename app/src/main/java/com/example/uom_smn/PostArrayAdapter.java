package com.example.uom_smn;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostArrayAdapter extends ArrayAdapter<Post> {

    private List<Post> postList;
    private final LayoutInflater inflater;
    private final int layoutResource;

    private ListView postListView;

    public PostArrayAdapter(@NonNull Context context, int resource, @NonNull List<Post> objects, ListView listView){
        super(context, resource, objects);
        postList = objects;
        layoutResource = resource;
        inflater = LayoutInflater.from(context);
        postListView = listView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView ==null){

            convertView = inflater.inflate(layoutResource , parent , false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        }
        else{
        viewHolder = (ViewHolder)convertView.getTag();

        }
        Post currentPost = postList.get(position);

        viewHolder.icon.setImageResource(R.drawable.icon_twitter);
        viewHolder.photo.setImageBitmap(currentPost.getPhotoBitmap());
        viewHolder.postText.setText(currentPost.getPostText()+ "");
        viewHolder.userName.setText(currentPost.getUserName()+ "");

        return convertView;
    }

    private class ViewHolder {
        final ImageView icon;
        final ImageView photo;
        final TextView postText;
        final TextView userName;

        ViewHolder(View view) {
            icon = view.findViewById(R.id.icon);
            photo = view.findViewById(R.id.photo);
            postText = view.findViewById(R.id.postText);
            userName = view.findViewById(R.id.userName);

        }
    }
    @Override
    public int getCount(){
        return postList.size();
    }

    public void setPostList(List<Post> posts){
        this.postList = posts;
        postListView.setAdapter(this);
    }
}
