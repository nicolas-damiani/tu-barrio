package com.engineers.tubarrio.config;

import android.app.Activity;
import android.content.Context;

import org.json.JSONObject;

public class Config {

    private static void assignSharedPreferences(Activity activity, String key, String value) {
        android.content.SharedPreferences sharedpreferences = activity.getSharedPreferences(Constants.MY_PREFERENCES, Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public static void setToken(Activity activity, String token) {
        assignSharedPreferences(activity, "token", token);
    }

    public static String getToken(Activity activity) {
        return activity.getSharedPreferences(Constants.MY_PREFERENCES, Context.MODE_PRIVATE).getString("token", "");
    }

    public static void setUserInfo (Activity activity, JSONObject user){
        assignSharedPreferences(activity, "userId", user.getString("id"));
    }


}
