package com.engineers.tubarrio.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.engineers.tubarrio.R;
import com.engineers.tubarrio.config.Config;
import com.engineers.tubarrio.entities.Publication;
import com.engineers.tubarrio.entities.User;
import com.engineers.tubarrio.widgets.MenuBar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.engineers.tubarrio.requests.FollowPublication;
import com.engineers.tubarrio.requests.UnfollowPublication;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ViewPublicationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Publication publication;
    TextView pubTitle;
    TextView pubDescription;
    TextView pubUserPhone;
    User user;
    Boolean isAuthor = false;
    Boolean isFollowing = false;
    ImageView editIcon;
    ImageView publicationImage;
    Button viewComments;
    Button followPublication;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_publication);
        user  = Config.getLoggedUserInfo(this);
        activity = this;
        MenuBar menuBar = new MenuBar(this);
        publication = (Publication) getIntent().getSerializableExtra("publication");
        isFollowing = getIntent().getBooleanExtra("isSuscribed",false);
        if(user.getEmail().equals(publication.getCreator().getEmail())){
            isAuthor = true;
        }

        initializeViews();
        InitializeButtons();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initializeViews() {
        pubTitle = (TextView) findViewById(R.id.publication_title);
        pubDescription = (TextView) findViewById(R.id.publication_description);
        pubUserPhone = (TextView) findViewById(R.id.publication_user_phone);
        editIcon = (ImageView) findViewById(R.id.edit_publication_icon);
        publicationImage = (ImageView) findViewById(R.id.publication_image);
        pubTitle.setText(publication.getTitle());
        pubDescription.setText(publication.getDescription());
        pubUserPhone.setText(publication.getCreator().getPhone());
        viewComments = (Button) findViewById(R.id.view_comments_btn);
        followPublication = (Button) findViewById(R.id.follow_publication_btn);

        if(!isAuthor){
            editIcon.setVisibility(View.GONE);
        }

        if (!publication.getPublicationImage().isEmpty()) {
            byte[] decodedString = Base64.decode( publication.getPublicationImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            publicationImage.setImageBitmap(decodedByte);
        }


        if(isFollowing){
            followPublication.setText("Dejar de Seguir");
            followPublication.setBackground(activity.getResources().getDrawable(R.drawable.rounded_button_selected));

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setMarkers();
    }

    private void setMarkers(){
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(publication.getLatitude(), publication.getLongitude()))
                .title(publication.getTitle()));
        LatLng mDefaultLocation = new LatLng(publication.getLatitude(), publication.getLongitude());
        int DEFAULT_ZOOM = 15;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
    }

    private void InitializeButtons(){
        viewComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goToNextActivity = new Intent(activity, ViewPublicationCommentsActivity.class);
                goToNextActivity.putExtra("publication", publication);
                activity.startActivity(goToNextActivity);
            }
        });


        followPublication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFollowing){
                    new UnfollowPublication(activity, publication) {
                        @Override
                        public void onFinished() {
                            isFollowing = false;
                            followPublication.setText("Seguir");
                            followPublication.setBackground(activity.getResources().getDrawable(R.drawable.rounded_button));
                        }
                    };
                }else{
                    new FollowPublication(activity, publication) {
                        @Override
                        public void onFinished() {
                            isFollowing = true;
                            followPublication.setText("Dejar de Seguir");
                            followPublication.setBackground(activity.getResources().getDrawable(R.drawable.rounded_button_selected));
                        }
                    };
                }
            }
        });

    }

    public void onClickEditPublication(View view) {
        Intent goToNextActivity = new Intent(activity, AddPublicationActivity.class);
        goToNextActivity.putExtra("publication", publication);
        activity.startActivity(goToNextActivity);
        activity.finish();
    }
}
