package com.engineers.tubarrio.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.support.v4.app.FragmentManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.engineers.tubarrio.R;
import com.engineers.tubarrio.config.Config;
import com.engineers.tubarrio.entities.User;
import com.engineers.tubarrio.helpers.ExtraFunctions;
import com.engineers.tubarrio.dialogs.PhotoDialog;
import com.engineers.tubarrio.requests.SendUserInformation;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.engineers.tubarrio.fragments.ScrollableMapFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_publication);
        latLngSet = false;
        ((ScrollableMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);

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
    }

    public void onClickAdd(View view) {

        EditText publicationNameEditText = (EditText) findViewById(R.id.name_publication);
        EditText descriptionEditText = (EditText) findViewById(R.id.description_publication);
        //ImageView image = (ImageView) findViewById(R.id.add_image_button);
        //Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
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
//        String filename = "bitmap.png";
//        try{
//            FileOutputStream stream = this.openFileOutput(filename, Context.MODE_PRIVATE);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        }catch (FileNotFoundException ex){
//            Toast.makeText(this, "Error manejando imagen", Toast.LENGTH_LONG).show();
//            cancel = true;
//        }

        if (!cancel) {
            Intent intent = new Intent(this, ViewPublicationActivity.class);
            intent.putExtra("EVENT_NAME", publicationName);
            intent.putExtra("EVENT_DESCRIPTION", description);
            intent.putExtra("EVENT_LATITUDE", latitude);
            intent.putExtra("EVENT_LONGITUDE", longitude);
            //intent.putExtra("EVENT_IMAGE", filename);
            startActivity(intent);
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
            ImageView imageView = (ImageView) findViewById(R.id.add_image_button);
            imageView.setImageBitmap(imageBitmap);
        } else if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ImageView imageView = (ImageView) findViewById(R.id.add_image_button);
                imageView.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al cargar la foto", Toast.LENGTH_LONG).show();
            }
        }
    }




}
