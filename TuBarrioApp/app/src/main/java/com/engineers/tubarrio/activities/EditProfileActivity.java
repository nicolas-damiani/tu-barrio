package com.engineers.tubarrio.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.engineers.tubarrio.R;

public class EditProfileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initializeViews();
    }

    private void initializeViews(){
        EditText firstNameET = (EditText) findViewById(R.id.name_profile);
        EditText lastNameET = (EditText) findViewById(R.id.surname_profile);
        EditText emailET = (EditText) findViewById(R.id.email_profile);


    }
}
