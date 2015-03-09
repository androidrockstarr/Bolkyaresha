package rajpriya.com.bolkyaresha.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.IntentCompat;
import android.view.View;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.File;
import java.io.FileOutputStream;

import rajpriya.com.bolkyaresha.App;
import rajpriya.com.bolkyaresha.MainActivity;
import rajpriya.com.bolkyaresha.ads.FullScreenAd;

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
        File root = Environment.getExternalStorageDirectory();
        File cachePath = new File(root.getAbsolutePath() + "/DCIM/Camera/bolkyaresha_temp_image.jpg");
        if(cachePath.exists()) {
            cachePath.delete();
        }
        try {
            cachePath.createNewFile();
            FileOutputStream ostream = new FileOutputStream(cachePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
            ostream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(cachePath));
        context.startActivity(Intent.createChooser(share,"Share via"));

    }


    public static void showFullScreenAd(Context context) {
        context.startActivity(new Intent(context, FullScreenAd.class));

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
