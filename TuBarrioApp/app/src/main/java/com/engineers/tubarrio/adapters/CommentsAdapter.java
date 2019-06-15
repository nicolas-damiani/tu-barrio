package com.engineers.tubarrio.adapters;

import android.content.Context;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.engineers.tubarrio.R;
import com.engineers.tubarrio.entities.Comment;
import com.engineers.tubarrio.helpers.LoadImageThread;


import java.util.List;

public class CommentsAdapter extends BaseAdapter {
    private Context mContext;
    private List<Comment> mComments;

    public CommentsAdapter(Context mContext, List<Comment> mComments) {
        this.mContext = mContext;
        this.mComments = mComments;
    }

    @Override
    public int getCount() {
        return mComments.size();
    }

    @Override
    public Object getItem(int position) {
        return mComments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewFriend) {
        View v = View.inflate(mContext, R.layout.items_comment, null);
        Comment comment = mComments.get(position);
        TextView tvName = (TextView) v.findViewById(R.id.comment_name);
        TextView tvText = (TextView) v.findViewById(R.id.comment_text);
        TextView tvTime = (TextView) v.findViewById(R.id.comment_time);
        ImageView ivImage = (ImageView) v.findViewById(R.id.comment_image);
        tvName.setText(comment.getCreator().getFirstName() + " " + comment.getCreator().getLastName());
        tvText.setText(comment.getText());
        if(!comment.getCreator().getProfileImage().isEmpty()) {
//            byte[] decodedString = Base64.decode(comment.getCreator().getProfileImage(), Base64.DEFAULT);
//            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//            Bitmap newBitmap = getResizedBitmap(decodedByte, 100, 100);
//            ivImage.setImageBitmap();
           // ivImage.setTag(decodedByte);
            Bundle b = new Bundle();
            b.putString("imageBit", comment.getCreator().getProfileImage());
            new LoadImageThread(mContext, ivImage).execute(b);


        }else{
            ivImage.setImageResource(R.drawable.default_avata);
        }
        v.setTag(mComments.get(position));
        return v;
    }




}


