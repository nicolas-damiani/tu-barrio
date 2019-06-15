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
import com.engineers.tubarrio.entities.Comment;
import com.engineers.tubarrio.entities.Publication;
import com.engineers.tubarrio.entities.User;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCommentToPublication {


    Activity activity;
    Context context;
    Map<String, String> params;
    public Publication publication;
    public Comment comment;


    public AddCommentToPublication(final Activity activity, final Publication publication, Comment comment) {
        this.activity = activity;
        this.comment = comment;
        this.publication = publication;
        this.context = activity.getApplicationContext();
        params = new HashMap<String, String>();

        String url = Constants.URL + "api/Publication/Comment?publicationId="+publication.getId();
        params.put("Text", comment.getText());
        params.put("CreatedOn", comment.getCreatedOn().toString());

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Intent publicationIntent = new Intent(activity, ViewPublicationActivity.class);
                        publicationIntent.putExtra("publication", publication);
                        activity.startActivity(publicationIntent);
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
