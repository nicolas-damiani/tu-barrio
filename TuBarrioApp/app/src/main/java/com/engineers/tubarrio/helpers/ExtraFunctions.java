package com.engineers.tubarrio.helpers;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ExtraFunctions {

    public String mCurrentPhotoPath;
    private Activity activity;

    public ExtraFunctions(Activity activity){
        this.activity = activity;
        this.mCurrentPhotoPath = "";
    }

    public File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public Bitmap rotateImage(Bitmap bitmap) throws IOException {
        ExifInterface ei = new ExifInterface(mCurrentPhotoPath);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap rotatedBitmap = null;
        switch(orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(bitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(bitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(bitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedBitmap = bitmap;
        }
        return  rotatedBitmap;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }



    public String convertBitmapToBase64(Bitmap image){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 30, baos); //bm is the bitmap object
            byte[] byteArrayImage = baos.toByteArray();
            return Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public Bitmap convertBase64ToBitmap(String imageString){
        try {
            byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }catch(Exception e){
            e.printStackTrace();
            return Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        }
    }

    public String getSpanishDate(Date date){
        SimpleDateFormat formato =
                new SimpleDateFormat("EEEE d 'de' MMMM ',' yyyy '.' ", new Locale("es","UY"));
        return formato.format(date);
    }

    public String getTimeFromDate(Date date){
        SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
        return localDateFormat.format(date);
    }

    public String getSpanishDateTime(Date date){
        SimpleDateFormat formato =
                new SimpleDateFormat("EEEE d 'de' MMMM ',' yyyy '.' ", new Locale("es","UY"));
        String sDate =  formato.format(date);
        String time = getTimeFromDate(date);
        return (sDate + time);
    }

    public Date getNumberDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public int getDayFromDate(Date date){
        String day = (String) DateFormat.format("dd",   date);
        return Integer.parseInt(day);
    }
    public int getMonthFromDate(Date date){
        String month = (String) DateFormat.format("MM",   date);
        return Integer.parseInt(month);
    }
    public int getYearFromDate(Date date){
        String year = (String) DateFormat.format("yyyy", date);
        return Integer.parseInt(year);
    }

    public GregorianCalendar getGregorianDateTimeFromDate(Date date){
        GregorianCalendar gc = new GregorianCalendar(getYearFromDate(date), getMonthFromDate(date), getDayFromDate(date),getHoursFromDate(date),getMinutesFromDate(date));
        return gc;
    }

    public int getHoursFromDate (Date date){
        String time = getTimeFromDate(date);
        String[] timeArray = time.split(":");
        return Integer.parseInt(timeArray[0]);
    }

    public int getMinutesFromDate (Date date){
        String time = getTimeFromDate(date);
        String[] timeArray = time.split(":");
        return Integer.parseInt(timeArray[1]);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}