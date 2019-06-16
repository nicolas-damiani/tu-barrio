package com.engineers.tubarrio.widgets;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.engineers.tubarrio.R;
import com.engineers.tubarrio.activities.LoginActivity;
import com.engineers.tubarrio.activities.MapsActivity;
import com.engineers.tubarrio.activities.ProfileActivity;
import com.engineers.tubarrio.activities.PublicationsActivity;
import com.engineers.tubarrio.config.Constants;

public class MenuBar {

    private Activity activity;
    private LinearLayout homeBtn;
    private ImageView mainBtn;
    private LinearLayout myFollowsBtn;
    private LinearLayout myProfileBtn;
    private LinearLayout menuSectionContainer;
    private boolean onMapActivity;


    public MenuBar(Activity activity) {
        this.activity = activity;
        onMapActivity = activity.getClass().getSimpleName().equals("MapsActivity");
        initializeViews();
        setClickActions();
    }

    private void initializeViews() {
        homeBtn = (LinearLayout) activity.findViewById(R.id.home_btn);
        myFollowsBtn = (LinearLayout) activity.findViewById(R.id.my_follows_btn);
        myProfileBtn = (LinearLayout) activity.findViewById(R.id.my_profile_btn);
        mainBtn = (ImageView) activity.findViewById(R.id.main_btn);
        menuSectionContainer = (LinearLayout) activity.findViewById(R.id.menu_section_container);
        if (!onMapActivity){
            TextView homeTV = (TextView) activity.findViewById(R.id.home_tv);
            homeTV.setText("Mapa");
        }

    }

    private void setClickActions() {
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onMapActivity) {
                    Intent goToNextActivity = new Intent(activity, PublicationsActivity.class);
                    goToNextActivity.putExtra("allPublications", Constants.ALL_PUBLICATIONS);
                    activity.startActivity(goToNextActivity);
                }else {
                    Intent goToNextActivity = new Intent(activity, MapsActivity.class);
                    goToNextActivity.putExtra("allPublications", Constants.ALL_PUBLICATIONS);
                    activity.startActivity(goToNextActivity);
                    goToNextActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                }
            }
        });
        myFollowsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToNextActivity = new Intent(activity, PublicationsActivity.class);
                goToNextActivity.putExtra("allPublications", Constants.FOLLOWED_PUBLICATIONS);
                activity.startActivity(goToNextActivity);
            }
        });
        myProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToNextActivity = new Intent(activity, ProfileActivity.class);
                activity.startActivity(goToNextActivity);
            }
        });
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menuSectionContainer.getVisibility() == View.GONE)
                    menuSectionContainer.setVisibility(View.VISIBLE);
                else
                    menuSectionContainer.setVisibility(View.GONE);
            }
        });
    }
}
