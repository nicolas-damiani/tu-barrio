package com.engineers.tubarrio.requests;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.engineers.tubarrio.activities.EditProfileActivity;
import com.engineers.tubarrio.config.Config;
import com.engineers.tubarrio.config.Constants;
import com.engineers.tubarrio.entities.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendUserInformation extends AsyncTask<String, Void, String> {


    private boolean success;
    private Activity mActivity;
    private User user;

    public SendUserInformation(Activity activity, User user) {
        mActivity = activity;
        this.user = user;
        success = false;
    }

    @Override
    protected String doInBackground(String... urls) {
        try {

            URL url = new URL(generateUrl());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.setRequestMethod("PUT");
            con.connect();
            int status = con.getResponseCode();
            InputStreamReader inputStreamReader = null;
            if (status >= 400 && status < 600) {
                inputStreamReader = new InputStreamReader(con.getErrorStream(), "UTF-8");
            } else {
                inputStreamReader = new InputStreamReader(con.getInputStream(), "UTF-8");
            }
            if (status >= 200 && status < 300) {
                success = true;
            }
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String valor = reader.readLine();
            reader.close();
            con.disconnect();
            return valor;
        } catch (Exception ex) {
            Log.e("Error", ex.getMessage());
            return ex.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String resultString) {
        if (success) {
            AlertDialog alertDialog = new AlertDialog.Builder(mActivity).create();
            alertDialog.setTitle("Éxito!");
            alertDialog.setMessage("Usuario guardado exitosamente");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
            alertDialog.show();

        } else {

        }
    }

    @Override
    protected void onCancelled() {

    }

    @NonNull
    private String generateUrl() {
       String urlString = Constants.URL + "/users/";
        return urlString;
    }
}
