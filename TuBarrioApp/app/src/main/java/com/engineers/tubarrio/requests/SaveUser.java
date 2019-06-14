package com.engineers.tubarrio.requests;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.engineers.tubarrio.activities.MapsActivity;
import com.engineers.tubarrio.config.Config;
import com.engineers.tubarrio.config.Constants;
import com.engineers.tubarrio.entities.Publication;
import com.engineers.tubarrio.entities.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveUser {


    Activity activity;
    Context context;
    Map<String, String> params;
    public List<Publication> publications;
    Notification pendingNotification;


    public SaveUser(final Activity activity, User user) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        params = new HashMap<String, String>();

        String url = Constants.URL + "api/User";
        params.put("Name", user.getFirstName());
        params.put("Surname", user.getLastName());
        params.put("Email", user.getEmail());
        params.put("ProfileImage", "/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEB\n" +
                "AQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQH/2wBDAQEBAQEBAQEBAQEBAQEBAQEBAQEB\n" +
                "AQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQH/wAARCBAgDBgDASIA\n" +
                "AhEBAxEB/8QAGwAAAwEBAQEBAAAAAAAAAAAAAgMEAQAFBgr/xABWEAACAQEFBQUFBgYBAwICABcB\n" +
                "AgMRABITITEEIiNBUTIzYXGBQkORofBSU2OxwdEUYnOD4fGTJHKjgrPDBZLT4zTzorJEFVRkwtLi\n" +
                "dISU8qTkJbTU/8QAGgEBAQEBAQEBAAAAAAAAAAAAAAECAwQFBv/EADgRAAIBBAEDBAIDAAICAgEB\n" +
                "CQERIQAxQVFhcYGRobHB8ALREuHxIjJCUgNicoKSorLCE9Li8gT/2gAMAwEAAhEDEQA/APwVbbL/\n" +
                "APMNqlUbXABHPIqLgiOKN8OOnDOfPjZv4HKtKkhCSzbK3GEQxI3/AJ3HdmXrFSn0bS7V/wDMNp2i\n" +
                "KR55khcSbscUShl/9UcYArTWtKUAItNsiTbUt9ZWQw0N1XNWQMDQ+IqB40rS3gxhAq+fK8eyr6+b\n" +
                "zft+uvNbLFtM+0LLRY2Em5HUXw9a5RYmVTpmCbPie8sp2uOaTaw104S0vRw0GGcLKb/GZ52mmCDa\n" +
                "I0kJ/iKw3JNUvOdZB50z6Z9bUySbTsO0tPLJJBKBG8aXMWKVG17008yQfS1LQHiF423OYps+Z6Se\n" +
                "gHjrU0GzvLJtEu0phJLFI4ZhRVKn/FP20tySbNIVhjjjVXQxkgiN6+7lJkyMvkc9elhSMyPLJtc0\n" +
                "qvLFeXDrRnpREkyBNSMGmlfe0yIzBHkjJu3o1jXDiAS7cEQzJkrWh8Mx31QKMkM4tYIDd5hkjBdA\n" +
                "1Amb");
        params.put("Phone", user.getPhone());

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Intent loginIntent = new Intent(activity, MapsActivity.class);
                        activity.startActivity(loginIntent);
                        activity.finish();
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
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
                headers.put("token", Config.getToken(activity));
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
