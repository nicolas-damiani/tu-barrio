package com.engineers.tubarrio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.engineers.tubarrio.activities.LoginActivity;
import com.engineers.tubarrio.activities.MapsActivity;
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
        if (account == null) {
            Intent loginIntent = new Intent(LoadingActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        } else {
            //ir a home activity
        }
    }

}
