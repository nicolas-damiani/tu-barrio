package com.engineers.tubarrio.config;

import android.app.Activity;
import android.content.Context;

import com.engineers.tubarrio.entities.User;
import com.engineers.tubarrio.firebase.MyFirebaseMessagingService;

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

    public static String getFCMToken(Activity activity) {
        return activity.getSharedPreferences(Constants.MY_PREFERENCES, Context.MODE_PRIVATE).getString("deviceToken", "");
    }



    public static void setLoggedUserInfo(Activity activity, User user){
        assignSharedPreferences(activity, "userId", user.id+"");
        assignSharedPreferences(activity, "userFirstName", user.firstName);
        assignSharedPreferences(activity, "userLastName", user.lastName);
        assignSharedPreferences(activity, "userEmail", user.email);
    }



    public static User getLoggedUserInfo (Activity activity){
        User user = new User();
        user.id = Integer.parseInt(activity.getSharedPreferences(Constants.MY_PREFERENCES, Context.MODE_PRIVATE).getString("userId",""));
        user.firstName = activity.getSharedPreferences(Constants.MY_PREFERENCES, Context.MODE_PRIVATE).getString("userFirstName", "");
        user.lastName= activity.getSharedPreferences(Constants.MY_PREFERENCES, Context.MODE_PRIVATE).getString("userFirstName", "");
        user.email = activity.getSharedPreferences(Constants.MY_PREFERENCES, Context.MODE_PRIVATE).getString("userEmail", "");
        return user;

    }


}
