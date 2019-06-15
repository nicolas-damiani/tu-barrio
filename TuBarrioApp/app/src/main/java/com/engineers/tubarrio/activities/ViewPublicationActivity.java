package com.engineers.tubarrio.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.engineers.tubarrio.R;
import com.engineers.tubarrio.config.Config;
import com.engineers.tubarrio.entities.Publication;
import com.engineers.tubarrio.entities.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

public class ViewPublicationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Publication publication;
    TextView pubTitle;
    TextView pubDescription;
    TextView pubUserPhone;
    User user;
    Boolean isAuthor = false;
    ImageView editIcon;
    ImageView publicationImage;
    Button viewComments;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_publication);
        user  = Config.getLoggedUserInfo(this);
        activity = this;
        publication = (Publication) getIntent().getSerializableExtra("publication");
        if(user.getEmail() == publication.getUsername()){
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
        pubTitle.setText(publication.getTitle());
        pubDescription.setText(publication.getDescription());
        pubUserPhone.setText(publication.getUserPhone());
        viewComments = (Button) findViewById(R.id.view_comments_btn);

        if(!isAuthor){
            editIcon.setVisibility(View.GONE);
        }

        //TODO: Poner la primer imagen como fondo del cosito


        //TODO: Si ya esta suscripto, boton diga dejar de seguir
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
    }
}
