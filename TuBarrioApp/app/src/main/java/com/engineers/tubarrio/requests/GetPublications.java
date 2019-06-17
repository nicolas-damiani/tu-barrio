package com.engineers.tubarrio.requests;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.engineers.tubarrio.config.Config;
import com.engineers.tubarrio.config.Constants;
import com.engineers.tubarrio.entities.Publication;
import com.engineers.tubarrio.widgets.Loader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GetPublications {

    Activity activity;
    Context context;
    Map<String, String> params;
    public List<Publication> publications;
    Notification pendingNotification;


    public GetPublications(final Activity activity, int publicationFilter) {
        final Loader loader = new Loader(activity);
        loader.showLoader();
        this.activity = activity;
        this.context = activity.getApplicationContext();
        params = new HashMap<String, String>();

        String url = "";
        if (publicationFilter == Constants.ALL_PUBLICATIONS) {
            url = Constants.URL + "api/Publications";
        } else if (publicationFilter == Constants.FOLLOWED_PUBLICATIONS){
            url = Constants.URL + "api/Publication/GetFollowedPublications";
        }else{
            url = Constants.URL + "api/Publications/AllFromUser";
        }
        publications = new ArrayList<>();
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loader.hideLoader();


                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() != 0) {
                                ArrayList<Publication> publicationsList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                                    Publication navigationMenuOption = new Publication(jsonObj);
                                    publicationsList.add(navigationMenuOption);
                                }
                                publications = publicationsList;
                            }
                            onFinished();
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

    public abstract void onFinished();
}
