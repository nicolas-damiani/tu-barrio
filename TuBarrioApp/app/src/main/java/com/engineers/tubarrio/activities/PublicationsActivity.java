package com.engineers.tubarrio.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.engineers.tubarrio.R;
import com.engineers.tubarrio.adapters.PublicationsAdapter;
import com.engineers.tubarrio.config.Config;
import com.engineers.tubarrio.entities.Publication;
import com.engineers.tubarrio.entities.User;
import com.engineers.tubarrio.requests.GetPublications;
import com.engineers.tubarrio.widgets.MenuBar;

import java.util.ArrayList;
import java.util.List;

public class PublicationsActivity extends Activity {

    List<Publication> mListPublications;
    ListView publicationsListView;
    Activity activity;
    int allPublications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publications);
        MenuBar menuBar = new MenuBar(this);
        activity = this;
        allPublications = getIntent().getIntExtra("allPublications", 0);
        initializeView();
        setPublications();
    }

    private void initializeView(){
        publicationsListView = (ListView) findViewById(R.id.publications_list);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setPublications();
    }

    private void setPublications(){
        mListPublications = new ArrayList<>();
        new GetPublications(this, allPublications) {
            @Override
            public void onFinished() {
                mListPublications = this.publications;

                TextView noPublicationsTv = (TextView) findViewById(R.id.noPublicationsText);
                noPublicationsTv.setVisibility(View.GONE);
                PublicationsAdapter publicationsAdapter = new PublicationsAdapter(activity, mListPublications);
                publicationsListView.setAdapter(publicationsAdapter);
                if (mListPublications.size() == 0) {
                    noPublicationsTv.setVisibility(View.VISIBLE);
                }
            }
        };
        publicationsListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Publication publication = (Publication) publicationsListView.getItemAtPosition(position);

                Intent goToNextActivity = new Intent(activity, ViewPublicationActivity.class);

                if (publication.getFollowers().contains(Config.getLoggedUserInfo(activity)))
                    goToNextActivity.putExtra("isSuscribed", true);
                else
                    goToNextActivity.putExtra("isSuscribed", false);
                publication.setFollowers(null);
                goToNextActivity.putExtra("publication", publication);

                activity.startActivity(goToNextActivity);
            }
        });

    }
}
