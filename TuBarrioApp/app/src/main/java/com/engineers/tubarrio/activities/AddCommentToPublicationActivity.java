package com.engineers.tubarrio.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.engineers.tubarrio.R;
import com.engineers.tubarrio.adapters.CommentsAdapter;
import com.engineers.tubarrio.entities.Comment;
import com.engineers.tubarrio.entities.Publication;
import com.engineers.tubarrio.requests.AddCommentToPublication;

import java.util.Date;

public class AddCommentToPublicationActivity extends AppCompatActivity {

    Publication publication;
    Comment comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment_to_publication);
        publication = (Publication) getIntent().getSerializableExtra("publication");
    }

    public void onClickAddComment(View view) {
        EditText commentEditText = (EditText) findViewById(R.id.comment_text);
        String commentText = commentEditText.getText().toString();
        comment = new Comment();
        comment.setText(commentText);
        comment.setCreatedOn(new Date());
        boolean cancel = false;

        if (TextUtils.isEmpty(commentText)) {
            cancel = true;
            Toast.makeText(this, "El comentario no puede estar vacío", Toast.LENGTH_LONG).show();
        }

        if(!cancel) {
            new AddCommentToPublication(this, publication, comment);
        }
    }
}
