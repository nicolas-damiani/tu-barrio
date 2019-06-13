package com.engineers.tubarrio.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.engineers.tubarrio.R;
import com.engineers.tubarrio.config.Config;
import com.engineers.tubarrio.entities.User;
import com.engineers.tubarrio.requests.SaveUser;
import com.engineers.tubarrio.requests.SendUserInformation;

public class EditProfileActivity extends Activity {

    EditText firstNameET;
    EditText lastNameET;
    EditText phoneET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
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
            new SaveUser(this, user);
        }
    }

    // TODO hacer validaciones de usuario
    private boolean hasValidateFields(){
        return true;
    }

    private User getUserFromFields(){
        User user = new User();
        user.setFirstName( firstNameET.getText().toString());
        user.setLastName(lastNameET.getText().toString());
        user.setPhone(phoneET.getText().toString());
        return user;
    }

}
