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
import com.engineers.tubarrio.activities.ViewPublicationActivity;
import com.engineers.tubarrio.config.Config;
import com.engineers.tubarrio.config.Constants;
import com.engineers.tubarrio.entities.Publication;
import com.engineers.tubarrio.entities.User;
import com.engineers.tubarrio.widgets.Loader;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddPublicationRequest {

    Activity activity;
    Context context;
    Map<String, String> params;
    public List<Publication> publications;
    Notification pendingNotification;


    public AddPublicationRequest(final Activity activity, final Publication publication, final boolean isEdit) {
        final Loader loader = new Loader(activity);
        loader.showLoader();
        this.activity = activity;
        this.context = activity.getApplicationContext();
        params = new HashMap<String, String>();



        String url = isEdit ? Constants.URL + "api/Publication?id="+publication.getId():Constants.URL + "api/Publication";
        params.put("Title", publication.getTitle());
        params.put("Description", publication.getDescription());
        params.put("Latitude", publication.getLatitude()+"");
        params.put("Longitude", publication.getLongitude()+"");
        params.put("PublicationImage", publication.getImage());


        int request = isEdit ? Request.Method.PUT : Request.Method.POST;

        StringRequest postRequest = new StringRequest(request, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loader.hideLoader();
                        if (!isEdit) {
                            Intent loginIntent = new Intent(activity, ViewPublicationActivity.class);
                            loginIntent.putExtra("publication", publication);
                            activity.startActivity(loginIntent);
                        }
                        activity.finish();
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
