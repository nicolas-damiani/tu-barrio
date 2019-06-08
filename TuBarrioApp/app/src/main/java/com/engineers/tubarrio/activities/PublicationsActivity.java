package com.engineers.tubarrio.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.engineers.tubarrio.R;
import com.engineers.tubarrio.adapters.PublicationsAdapter;
import com.engineers.tubarrio.config.Config;
import com.engineers.tubarrio.entities.Publication;
import com.engineers.tubarrio.requests.GetPublications;

import java.util.ArrayList;
import java.util.List;

public class PublicationsActivity extends Activity {

    List<Publication> mListPublications;
    ListView publicationsListView;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publications);
        activity = this;
        initializeView();
        setPublications();
    }

    private void initializeView(){
        publicationsListView = (ListView) findViewById(R.id.publications_list);

    }

    private void setPublications(){
        mListPublications = new ArrayList<>();
        new GetPublications(this) {
            @Override
            public void onFinished() {
                mListPublications = this.publications;
                PublicationsAdapter publicationsAdapter = new PublicationsAdapter(activity, mListPublications);
                publicationsListView.setAdapter(publicationsAdapter);
            }
        };
        publicationsListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Publication publication = (Publication) publicationsListView.getItemAtPosition(position);

                Intent goToNextActivity = new Intent(activity, ViewPublicationActivity.class);
                goToNextActivity.putExtra("publication", publication);
                activity.startActivity(goToNextActivity);
            }
        });

    }
}
