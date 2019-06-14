package com.engineers.tubarrio.helpers;

/**
 * Created by Usuario on 19/04/2017.
 */
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by etcraf on 10/20/15.
 */
public class ImageHelper {

    public static int getScreenWidth(Activity activity){
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int getScreenHeight(Activity activity){
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static boolean SaveBitmapInternalStorage(Context context,Bitmap bitmap,String fileName){

        boolean result=false;
        try{

            FileOutputStream fOut2 = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            if(fileName.contains("png"))
                bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut2);
            else
                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut2);
            fOut2.flush();
            fOut2.close();

            result=true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        catch(OutOfMemoryError e){
            bitmap=null;
            e.printStackTrace();
        }

        return result;
    }

    public static void DecodeAndSaveImageInternalStorage(Context context,String encodedImage,String fileName){

        try{

            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            FileOutputStream fOut2 = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            decodedByte.compress(Bitmap.CompressFormat.JPEG, 85, fOut2);

            fOut2.flush();
            fOut2.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap RetrieveImageInternalStorage(Context context,String fileName){

        Bitmap bitmap=null;
        FileInputStream fis;
        try
        {
            fis = context.openFileInput(fileName);
            bitmap= BitmapFactory.decodeStream(fis);
            fis.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch(OutOfMemoryError e){
            bitmap = null;
            e.printStackTrace();
        }

        return bitmap;
    }


    public static Boolean DeleteImageInternalStorage(Context context,String fileName){
        boolean deleted=context.deleteFile(fileName);
        return deleted;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapStream(Context context, String fileName, int reqWidth, int reqHeight) {

        Bitmap bitmap = null;
        FileInputStream fis1,fis2;
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            fis1 = context.openFileInput(fileName);
            BitmapFactory.decodeStream(fis1, null, options);
            fis1.close();
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
            fis2 = context.openFileInput(fileName);
            bitmap = BitmapFactory.decodeStream(fis2, null, options);
            fis2.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            bitmap = null;
            e.printStackTrace();
        }

        return bitmap;
    }

    public static Bitmap resizeBitmap(Bitmap source, Float width, Float height) {
        if (width == null) {
            width = height * source.getWidth() / source.getHeight();
        } else if (height == null) {
            height = width * source.getHeight() / source.getWidth();
        }

        Matrix m = new Matrix();
        m.setRectToRect(new RectF(0, 0, source.getWidth(), source.getHeight()), new RectF(0, 0, width, height), Matrix.ScaleToFit.CENTER);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), m, true);
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {

        int width  = bitmap.getWidth();
        int height = bitmap.getHeight();

        if(width > height) width = height;
        else height = width;

        int calc = bitmap.getHeight() * bitmap.getWidth() * 4;
        Log.v("Bitmap", "" + bitmap.getByteCount() / 1024 + " calc:" + calc / 1024);

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);
        final float roundPx = bitmap.getWidth() / 2;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }



}