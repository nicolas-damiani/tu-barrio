package com.engineers.tubarrio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.engineers.tubarrio.activities.EditProfileActivity;
import com.engineers.tubarrio.activities.LoginActivity;
import com.engineers.tubarrio.activities.MapsActivity;
import com.engineers.tubarrio.config.Config;
import com.engineers.tubarrio.entities.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class LoadingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null && !Config.getToken(this).isEmpty()) {
            User user = Config.getLoggedUserInfo(this);
            if (user!=null && !user.getPhone().isEmpty()) {
                Intent loginIntent = new Intent(LoadingActivity.this, MapsActivity.class);
                startActivity(loginIntent);
                finish();
            }else{
                Intent loginIntent = new Intent(LoadingActivity.this, EditProfileActivity.class);
                startActivity(loginIntent);
                finish();
            }
        } else {
            Intent loginIntent = new Intent(LoadingActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
            //ir a home activity
        }
    }

}
