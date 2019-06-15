package com.engineers.tubarrio.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.engineers.tubarrio.R;
import com.engineers.tubarrio.entities.Publication;

import java.util.List;

public class PublicationsAdapter extends BaseAdapter {
    private Context mContext;
    private List<Publication> mPublications;

    public PublicationsAdapter(Context mContext, List<Publication> mPublications) {
        this.mContext = mContext;
        this.mPublications = mPublications;
    }

    @Override
    public int getCount() {
        return mPublications.size();
    }

    @Override
    public Object getItem(int position) {
        return mPublications.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewFriend) {
        View v = View.inflate(mContext, R.layout.items_publication, null);
        TextView tvName = (TextView) v.findViewById(R.id.publication_title);
        TextView tvAuthor = (TextView) v.findViewById(R.id.publication_author);
        ImageView tvUsername = (ImageView) v.findViewById(R.id.publication_image);
        tvName.setText(mPublications.get(position).getTitle());
        tvAuthor.setText(mPublications.get(position).getCreator().getFirstName() + " " + mPublications.get(position).getCreator().getLastName());
        v.setTag(mPublications.get(position));
        return v;
    }
}
