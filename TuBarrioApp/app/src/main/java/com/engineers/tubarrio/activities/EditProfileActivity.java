package com.engineers.tubarrio.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.engineers.tubarrio.R;
import com.engineers.tubarrio.config.Config;
import com.engineers.tubarrio.entities.User;
import com.engineers.tubarrio.requests.SendUserInformation;

public class EditProfileActivity extends Activity {

    EditText firstNameET;
    EditText lastNameET;
    EditText emailET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initializeViews();
    }

    private void initializeViews(){
        firstNameET = (EditText) findViewById(R.id.name_profile);
        lastNameET = (EditText) findViewById(R.id.surname_profile);
        emailET = (EditText) findViewById(R.id.email_profile);
        Button continueBtn = (Button) findViewById(R.id.continue_btn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                continueActions();
            }
        });
        User user = Config.getLoggedUserInfo(this);
        if (!user.firstName.isEmpty())
            firstNameET.setText(user.firstName);
        if (!user.lastName.isEmpty())
            lastNameET.setText(user.lastName);
        if (!user.email.isEmpty())
            emailET.setText(user.email);
    }

    private void continueActions(){
        if (hasValidateFields()){
            User user = getUserFromFields();
            new SendUserInformation(this, user);
        }
    }
    // TODO
    private boolean hasValidateFields(){
        return true;
    }

    private User getUserFromFields(){
        User user = new User();
        user.firstName= firstNameET.getText().toString();
        user.lastName= lastNameET.getText().toString();
        user.email = emailET.getText().toString();
        return user;
    }

}
