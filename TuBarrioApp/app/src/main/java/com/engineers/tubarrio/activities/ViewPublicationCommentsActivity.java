package com.engineers.tubarrio.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.engineers.tubarrio.R;
import com.engineers.tubarrio.adapters.CommentsAdapter;
import com.engineers.tubarrio.adapters.PublicationsAdapter;
import com.engineers.tubarrio.entities.Comment;
import com.engineers.tubarrio.entities.Publication;
import com.engineers.tubarrio.requests.GetCommentsFromPublication;
import com.engineers.tubarrio.requests.GetPublications;
import com.engineers.tubarrio.widgets.MenuBar;

import java.util.ArrayList;
import java.util.List;

public class ViewPublicationCommentsActivity extends AppCompatActivity {

    List<Comment> mListComments;
    ListView commentsListView;
    Activity activity;
    Publication publication;
    Button addComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_publication_comments);
        initializeView();
        MenuBar menuBar = new MenuBar(this);
        addComment = (Button)findViewById(R.id.add_comment_btn);
        InitializeButtons();
        publication = (Publication) getIntent().getSerializableExtra("publication");
        activity = this;
        setComments();
    }

    private void initializeView(){
        commentsListView = (ListView) findViewById(R.id.comments_list);
    }

    private void setComments(){
        mListComments = new ArrayList<>();
        new GetCommentsFromPublication(this, publication) {
            @Override
            public void onFinished() {
                mListComments = this.comments;
                CommentsAdapter commentsAdapter = new CommentsAdapter(activity, mListComments);
                commentsListView.setAdapter(commentsAdapter);
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        setComments();
    }

    private void InitializeButtons(){
        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToNextActivity = new Intent(activity, AddCommentToPublicationActivity.class);
                goToNextActivity.putExtra("publication", publication);
                activity.startActivity(goToNextActivity);
            }
        });
    }
}
