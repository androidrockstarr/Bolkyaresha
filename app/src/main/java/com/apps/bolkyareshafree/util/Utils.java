package com.apps.bolkyareshafree.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.IntentCompat;
import android.view.View;

import java.io.OutputStream;

import com.apps.bolkyareshafree.MainActivity;

/**
 * Created by rajkumar on 3/10/15.
 */
public class Utils {

    /**
     * Use this function to fly directly to home screen from any location.
     * All activities on top of the HomeActivity will be closed.
     */
    public static void jumpToHome(Context context) {
        Intent intentToBeNewRoot = new Intent(context, MainActivity.class);

        ComponentName cn = intentToBeNewRoot.getComponent();
        Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(mainIntent);
    }

    public static void shareImage(View content, Context context) {
        content.setDrawingCacheEnabled(true);

        Bitmap bitmap = content.getDrawingCache();


        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "title");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values);


        OutputStream outstream;

        try {
            outstream = context.getContentResolver().openOutputStream(uri);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
            outstream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(share,"Share"));

    }

    /**
     * Get the size in bytes of a bitmap in a BitmapDrawable.
     * @param bitmap
     * @return size in bytes
     */
    @TargetApi(12)
    public static int getBitmapSize(Bitmap bitmap) {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB_MR1){
            return bitmap.getByteCount();
        } else{
            // Pre HC-MR1
            return bitmap.getRowBytes() * bitmap.getHeight();
        }


    }
}
