package com.engineers.tubarrio.requests;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.engineers.tubarrio.LoadingActivity;
import com.engineers.tubarrio.activities.EditProfileActivity;
import com.engineers.tubarrio.activities.LoginActivity;
import com.engineers.tubarrio.activities.MapsActivity;
import com.engineers.tubarrio.config.Config;
import com.engineers.tubarrio.config.Constants;
import com.engineers.tubarrio.entities.User;
import com.engineers.tubarrio.widgets.Loader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginRequest {

    Activity activity;
    Context context;
    Map<String, String> params;
    Notification pendingNotification;


    public LoginRequest(final Activity activity, String token) {
        final Loader loader = new Loader(activity);
        loader.showLoader();
        this.activity = activity;
        this.context = activity.getApplicationContext();
        params = new HashMap<String, String>();

        String url = Constants.URL + "api/User/LoginUserGoogle?googleToken="+token+"&fcmToken="+Config.getFCMToken(activity);
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loader.hideLoader();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Config.setToken(activity, jsonObject.getString("Token"));
                            Config.setLoggedUserInfo(activity, new User(jsonObject.getJSONObject("User")));

                            User user = Config.getLoggedUserInfo(activity);
                            if (user.getPhone().isEmpty()){
                                Intent loginIntent = new Intent(activity, EditProfileActivity.class);
                                loginIntent.putExtra("initial", true);
                                activity.startActivity(loginIntent);
                                activity.finish();
                            }else{
                                Intent loginIntent = new Intent(activity, MapsActivity.class);
                                activity.startActivity(loginIntent);
                                activity.finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loader.hideLoader();
//                        Loader loader = new Loader(activity);
//                        loader.hideLoader();
                        if (error != null) {
                            if (error.networkResponse != null) {
                                byte[] data = error.networkResponse.data;
                                String dataStr = null;
                                try {
                                    dataStr = new String(data, "UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                Log.v("Error - DATA", dataStr);
                            }
                        }
                        if (activity != null) {
                            Toast toast = Toast.makeText(context, "Verifique su conexion a internet", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<String, String>();
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(postRequest);
    }
}

