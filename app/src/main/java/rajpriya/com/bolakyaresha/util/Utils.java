package rajpriya.com.bolakyaresha.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.IntentCompat;
import android.view.View;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.OutputStream;

import rajpriya.com.bolakyaresha.App;
import rajpriya.com.bolakyaresha.MainActivity;
import rajpriya.com.bolakyaresha.ads.FullScreenAd;

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


    public static void showFullScreenAd(Context context, FullScreenAd.NextAction next) {
        Intent adIntent = new Intent(context, FullScreenAd.class);
        adIntent.putExtra(FullScreenAd.NEXT_ACTION, next);
        context.startActivity(adIntent);

    }

    public static void trackScreen(Activity context, String screenName) {
        // Get tracker.
        Tracker t = ((App)context.getApplication()).getTracker(App.TrackerName.GLOBAL_TRACKER);
        // Set screen name.
        t.setScreenName(screenName);
        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());
    }


    public static void trackEvent(Activity context, String eventName) {
        // Get tracker.
        Tracker t = ((App) context.getApplication()).getTracker(
                App.TrackerName.GLOBAL_TRACKER);
        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder()
                .setCategory("Events")
                .setAction("ButtonClick")
                .setLabel(eventName)
                .build());
    }
}
