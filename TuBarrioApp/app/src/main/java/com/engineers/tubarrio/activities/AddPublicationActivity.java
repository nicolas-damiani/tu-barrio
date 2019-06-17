package com.engineers.tubarrio.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.engineers.tubarrio.R;
import com.engineers.tubarrio.config.Config;
import com.engineers.tubarrio.entities.Publication;
import com.engineers.tubarrio.helpers.ExtraFunctions;
import com.engineers.tubarrio.dialogs.PhotoDialog;
import com.engineers.tubarrio.requests.AddPublicationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.engineers.tubarrio.fragments.ScrollableMapFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class AddPublicationActivity extends AppCompatActivity implements OnMapReadyCallback, PhotoDialog.NoticeDialogListener {

    private GoogleMap googleMap;
    private double latitude;
    private double longitude;
    private boolean latLngSet;
    private ExtraFunctions extraFunctions;
    private final AddPublicationActivity activity = this;
    static int CAMERA_REQUEST = 1;
    static int GALLERY_REQUEST = 2;
    Publication publication;
    private boolean isEditing;
    EditText publicationNameEditText;
    EditText descriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_publication);
        publication = (Publication) getIntent().getSerializableExtra("publication");
        extraFunctions = new ExtraFunctions(this);
        if (publication == null){
            publication = new Publication();
            isEditing = false;
        }else{
            isEditing = true;
        }
        latLngSet = false;
        ((ScrollableMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);

        initializeViews();
        if (isEditing)
            setViews();

    }

    private void setViews() {
        publicationNameEditText.setText(publication.getTitle());
        descriptionEditText.setText(publication.getDescription());
        if (!publication.getImage().isEmpty()){
            TextView addImageTextView= (TextView) findViewById(R.id.add_image_tv);
            addImageTextView.setVisibility(View.GONE);
        }
    }

    private void initializeViews() {
        publicationNameEditText = (EditText) findViewById(R.id.name_publication);
        descriptionEditText = (EditText) findViewById(R.id.description_publication);
    }


    public void setLatLng(LatLng latLng){
        latLngSet = true;
        latitude = latLng.latitude;
        longitude = latLng.longitude;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        final ScrollView mScrollView = findViewById(R.id.scroll_view);

        ((ScrollableMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .setListener(new ScrollableMapFragment.OnTouchListener() {
                    @Override
                    public void onTouch() {
                        mScrollView.requestDisallowInterceptTouchEvent(true);
                    }
                });
        googleMap = map;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                activity.setLatLng(latLng);
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Ubicacion Evento"));
            }
        });
        if (isEditing){
            LatLng latLng= new LatLng(publication.getLatitude(),
                    publication.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));

            googleMap.addMarker(new MarkerOptions()
                    .position(latLng));
        }
    }

    public void onClickAdd(View view) {

        String publicationName = publicationNameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        boolean cancel = false;

        if (TextUtils.isEmpty(description)) {
            cancel = true;
            Toast.makeText(this, "Debe escibir una descripcion", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(publicationName)) {
            cancel = true;
            Toast.makeText(this, "Debe escribir un nombre ", Toast.LENGTH_LONG).show();
        }
        if(!latLngSet){
            cancel = true;
            Toast.makeText(this, "Seleccione un lugar para el evento manteniendo presionado sobre el mapa", Toast.LENGTH_LONG).show();
        }

        if (!cancel) {
            publication.setLongitude(longitude);
            publication.setLatitude(latitude);
            publication.setTitle(publicationName);
            publication.setDescription(description);
            publication.setCreator(Config.getLoggedUserInfo(activity));
            new AddPublicationRequest(this, publication, isEditing);
            //intent.putExtra("EVENT_IMAGE", filename);

        }
    }


    public void onClickAddImageButton(View view) {
        showPhotoDialog();
    }

    private void showPhotoDialog() {
        PhotoDialog dialog = new PhotoDialog();
        dialog.show(getSupportFragmentManager(),"PhotoDialog");
    }

    @Override
    public void onDialogCameraClick(DialogFragment dialog) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        CameraImage();
    }

    @Override
    public void onDialogGalleryClick(DialogFragment dialog) {
        GalleryImage();
    }

    private void CameraImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = extraFunctions.createImageFile();
                if (photoFile != null) {
                    Uri photoURI = Uri.fromFile(photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                }
            } catch (IOException ex) {
                Toast.makeText(this, "Error abriendo camara", Toast.LENGTH_LONG).show();
            }
        }
    }



    private void GalleryImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(extraFunctions.mCurrentPhotoPath);
            publication.setImage(extraFunctions.convertBitmapToBase64(imageBitmap));
            byte[] decodedString = Base64.decode( publication.getPublicationImage(), Base64.DEFAULT);
            imageBitmap  = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ImageView imageView = (ImageView) findViewById(R.id.user_image_profile);
            imageView.setImageBitmap(imageBitmap);
        } else if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                publication.setImage(extraFunctions.convertBitmapToBase64(selectedImage));
                byte[] decodedString = Base64.decode( publication.getPublicationImage(), Base64.DEFAULT);
                selectedImage  = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                ImageView imageView = (ImageView) findViewById(R.id.user_image_profile);
                imageView.setImageBitmap(selectedImage);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al cargar la foto", Toast.LENGTH_LONG).show();
            }
        }
    }




}
