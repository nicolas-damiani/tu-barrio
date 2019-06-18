package com.engineers.tubarrio.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.engineers.tubarrio.R;
import com.engineers.tubarrio.config.Config;
import com.engineers.tubarrio.config.Constants;
import com.engineers.tubarrio.entities.User;
import com.engineers.tubarrio.helpers.ImageHelper;
import com.engineers.tubarrio.requests.GetStatisticsFromUser;
import com.engineers.tubarrio.widgets.MenuBar;

public class ProfileActivity extends Activity {

    TextView nameTV;
    TextView emailTV;
    TextView publicationsTV;
    TextView commentsTV;
    TextView followedTV;
    ImageView userPicIV;
    Button viewPublications;
    Button newPublication;
    Activity activity;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        activity = this;
        MenuBar menuBar = new MenuBar(this);
        initializeViews();
        initializeButtons();
        getStatistics();
    }

    private void getStatistics() {
        new GetStatisticsFromUser(this) {
            @Override
            public void onFinished() {
                publicationsTV.setText(this.statisticsUser.getCantPublications()+"");
                commentsTV.setText(this.statisticsUser.getCantComments()+"");
                followedTV.setText(this.statisticsUser.getCantFollowedPublications()+"");
            }
        };
    }


    private void initializeViews() {
        nameTV = findViewById(R.id.user_name);
        emailTV = findViewById(R.id.user_email);
        publicationsTV = findViewById(R.id.publications);
        commentsTV = findViewById(R.id.comments);
        followedTV = findViewById(R.id.followed);
        userPicIV = findViewById(R.id.user_profile_picture);
        viewPublications = (Button) findViewById(R.id.btn_publications);
        newPublication = (Button) findViewById(R.id.btn_new_publication);

        user = Config.getLoggedUserInfo(this);
        nameTV.setText(user.getFirstName() + " " + user.getLastName());
        emailTV.setText(user.getEmail());

        if (!user.getProfileImage().isEmpty()) {
            byte[] decodedString = Base64.decode( user.getProfileImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            decodedByte = ImageHelper.getRoundedCornerBitmap(decodedByte);
            userPicIV.setImageBitmap(decodedByte);
        }

//        publicationsTV.setText(user.cantPublications);
//        commentsTV.setText(user.cantComments);
//        followedTV.setText(user.cantFollowed);

    }

//    public void newPublicationAction(View view) {
//        Intent intent = new Intent(ProfileActivity.this, nueva publicacion);
//        startActivity(intent);
//    }

    private void initializeButtons() {
        viewPublications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToNextActivity = new Intent(activity, PublicationsActivity.class);
                goToNextActivity.putExtra("allPublications", Constants.MY_PUBLICACTIONS);
                activity.startActivity(goToNextActivity);
                activity.finish();
            }
        });

         newPublication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToNextActivity = new Intent(activity, AddPublicationActivity.class);
                activity.startActivity(goToNextActivity);
                activity.finish();
            }
        });
    }

    public void editProfileClick(View view) {
        Intent goToNextActivity = new Intent(activity, EditProfileActivity.class);
        goToNextActivity.putExtra("initial", false);
        activity.startActivity(goToNextActivity);
    }
}
