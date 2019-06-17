package com.engineers.tubarrio.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.engineers.tubarrio.R;
import com.engineers.tubarrio.config.Config;
import com.engineers.tubarrio.dialogs.PhotoDialog;
import com.engineers.tubarrio.entities.User;
import com.engineers.tubarrio.helpers.ExtraFunctions;
import com.engineers.tubarrio.helpers.ImageHelper;
import com.engineers.tubarrio.requests.SaveUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static com.engineers.tubarrio.activities.AddPublicationActivity.CAMERA_REQUEST;
import static com.engineers.tubarrio.activities.AddPublicationActivity.GALLERY_REQUEST;

public class EditProfileActivity extends AppCompatActivity implements PhotoDialog.NoticeDialogListener {

    EditText firstNameET;
    EditText lastNameET;
    EditText phoneET;
    private ExtraFunctions extraFunctions;
    String imageString;
    boolean initialActivity;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        extraFunctions = new ExtraFunctions(this);
        initialActivity = getIntent().getBooleanExtra("initial", true);
        activity = this;
        initializeViews();
    }

    private void initializeViews(){
        firstNameET = (EditText) findViewById(R.id.name_profile);
        lastNameET = (EditText) findViewById(R.id.surname_profile);
        phoneET = (EditText) findViewById(R.id.phone_profile);
        Button continueBtn = (Button) findViewById(R.id.continue_btn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                continueActions();
            }
        });
        User user = Config.getLoggedUserInfo(this);
        if (!user.getFirstName().isEmpty())
            firstNameET.setText(user.getFirstName());
        if (!user.getLastName().isEmpty())
            lastNameET.setText(user.getLastName());
        if (!user.getPhone().isEmpty())
            phoneET.setText(user.getPhone());
    }

    private void continueActions(){
        if (hasValidateFields()){
            User user = getUserFromFields();
            new SaveUser(this, user) {
                @Override
                public void onFinished() {
                    if (initialActivity){

                        Intent loginIntent = new Intent(activity, MapsActivity.class);
                        activity.startActivity(loginIntent);
                        activity.finish();
                    }else{
                        finish();
                    }
                }
            };
        }
    }

    // TODO hacer validaciones de usuario
    private boolean hasValidateFields(){
        return true;
    }

    private User getUserFromFields(){
        User user = Config.getLoggedUserInfo(this);
        user.setFirstName( firstNameET.getText().toString());
        user.setLastName(lastNameET.getText().toString());
        user.setPhone(phoneET.getText().toString());
        user.setProfileImage(imageString);
        return user;
    }


    public void onClickAddImageButton(View view) {
        showPhotoDialog();
    }

    private void showPhotoDialog() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
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
            try {
                imageBitmap = extraFunctions.rotateImage(imageBitmap);
                ImageView imageView = (ImageView) findViewById(R.id.user_image_profile);
                imageView.setImageBitmap(ImageHelper.getRoundedCornerBitmap(imageBitmap));
                imageString = extraFunctions.convertBitmapToBase64(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ImageView imageView = (ImageView) findViewById(R.id.user_image_profile);
                imageView.setImageBitmap(ImageHelper.getRoundedCornerBitmap(selectedImage));
                imageString = extraFunctions.convertBitmapToBase64(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al cargar la foto", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }
}
