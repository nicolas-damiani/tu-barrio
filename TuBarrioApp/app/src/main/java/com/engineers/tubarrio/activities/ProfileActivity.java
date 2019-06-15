package com.engineers.tubarrio.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.engineers.tubarrio.R;
import com.engineers.tubarrio.config.Config;
import com.engineers.tubarrio.entities.User;
import com.engineers.tubarrio.requests.SendUserInformation;

public class ProfileActivity extends Activity {

    TextView nameTV;
    TextView emailTV;
    TextView publicationsTV;
    TextView commentsTV;
    TextView followedTV;
    ImageView userPicIV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initializeViews();
    }


    private void initializeViews(){
        nameTV = findViewById(R.id.user_name);
        emailTV = findViewById(R.id.user_email);
        publicationsTV = findViewById(R.id.publications);
        commentsTV = findViewById(R.id.comments);
        followedTV = findViewById(R.id.followed);
        userPicIV = findViewById(R.id.user_profile_picture);
  /*      User user = Config.getLoggedUserInfo(this);
        if (!user.firstName.isEmpty())
            nameTV.setText(user.firstName);*/
//        publicationsTV.setText(user.cantPublications);
//        commentsTV.setText(user.cantComments);
//        followedTV.setText(user.cantFollowed);

    }

//    public void newPublicationAction(View view) {
//        Intent intent = new Intent(ProfileActivity.this, nueva publicacion);
//        startActivity(intent);
//    }

 //   public void seeAllPublicationsAction(View view){
 //       Intent intent = new Intent(ProfileActivity.this, lista de publicaciones);
//        startActivity(intent);
  //  }

}
