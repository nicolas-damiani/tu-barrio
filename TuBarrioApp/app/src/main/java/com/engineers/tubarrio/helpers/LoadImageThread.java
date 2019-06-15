package com.engineers.tubarrio.helpers;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public class LoadImageThread extends AsyncTask<Bundle, Void, Bitmap> {
    private ImageView view;
    private Bitmap bm;
    private Context context;
    private final WeakReference<ImageView> imageViewReference;


    public LoadImageThread(Context context, ImageView view) {
        this.context=context;
        imageViewReference = new WeakReference<ImageView>(view);
    }

    @Override
    protected Bitmap doInBackground(Bundle... b) {

        Bitmap bm =null;
        byte[] decodedString = Base64.decode(b[0].getString("imageBit"), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        bm = getResizedBitmap(decodedByte, imageViewReference.get().getMaxWidth(), imageViewReference.get().getMaxHeight());

        return bm;
    }

    @Override
    protected void onPostExecute(final Bitmap bm) {
        super.onPostExecute(bm);
        if (bm != null){ //if bitmap exists...
            view = imageViewReference.get();
            view.setImageBitmap(bm);
            // Fade out


        }else{ //if not picture, display the default ressource
        }

    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

}