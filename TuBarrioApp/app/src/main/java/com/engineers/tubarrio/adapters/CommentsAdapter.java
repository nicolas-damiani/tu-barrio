package com.engineers.tubarrio.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.engineers.tubarrio.R;
import com.engineers.tubarrio.entities.Comment;


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
        TextView tvName = (TextView) v.findViewById(R.id.comment_name);
        TextView tvText = (TextView) v.findViewById(R.id.comment_text);
        TextView tvTime = (TextView) v.findViewById(R.id.comment_time);
        ImageView ivImage = (ImageView) v.findViewById(R.id.comment_image);
        tvName.setText(mComments.get(position).getCreator().getFirstName() + " " + mComments.get(position).getCreator().getLastName());
        tvText.setText(mComments.get(position).getText());
        v.setTag(mComments.get(position));
        return v;
    }
}
