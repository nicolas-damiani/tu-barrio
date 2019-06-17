package com.engineers.tubarrio.firebase;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.engineers.tubarrio.config.Constants;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseIDService extends FirebaseInstanceIdService {
    public static final String TAG = "FirebaseIDService";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(Constants.MY_PREFERENCES, MODE_PRIVATE).edit();
        editor.putString("deviceToken", refreshedToken);
        editor.apply();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
    }
}