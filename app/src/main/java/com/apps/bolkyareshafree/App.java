package com.apps.bolkyareshafree;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import com.apps.bolkyareshafree.settings.SettingsActivityFragment;
import com.apps.bolkyareshafree.volley.BitmapMemCache;

import java.util.HashMap;

/**
 * Created by rajkumar.waghmare on 2014/11/29.
 */
public class App extends Application{

    private static RequestQueue mRequestQueue;
    private static ImageLoader mImageLoader;
    public static int LUANCH_COUNT = 0;

    public static ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public static RequestQueue getVolleyRequestQueue() {
        return mRequestQueue;
    }

    private static final String CHINTOO_PAGE_ID = "OfficialChintooPage";
    private static final String BOLKYARESHA_PAGE_ID = "Bolkyaresha";
    private static final String BOLKYARESHA_APP_PAGE_ID = "855955177808530";//Bolkyaresha-app
    public static  final String FB_PAGE_URL = "https://graph.facebook.com/"+ BOLKYARESHA_PAGE_ID  +"/posts?access_token=668151053299134|iR9g4u-L7ok1_mky8-cNDv8gdV4";
    public static String FB_APP_PAGE_URL = "https://graph.facebook.com/"+ BOLKYARESHA_APP_PAGE_ID +"/posts?access_token=668151053299134|iR9g4u-L7ok1_mky8-cNDv8gdV4";

    //push app for notifications (http://www.pushapps.mobi/)
    public static final String GOOGLE_API_PROJECT_NUMBER = "837201824571";
    public static final String  PUSHAPPS_APP_TOKEN = "931cdd27-660c-4048-9646-9f91cee274e4";

    /**
     * Enum used to identify the tracker that needs to be used for tracking.
     *
     * A single tracker is usually enough for most purposes. In case you do need multiple trackers,
     * storing them all in Application object helps ensure that they are created only once per
     * application instance.
     */
    public enum TrackerName {
        //APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER // Tracker used by all the apps from a company. eg: roll-up tracking.
        //ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        mRequestQueue = Volley.newRequestQueue(this);
        mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new BitmapMemCache();
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });

        final SharedPreferences PREF = getSharedPreferences(SettingsActivityFragment.BOLKYARESHA_APP_FILE, 0);
        SharedPreferences.Editor editor = PREF.edit();
        editor.putBoolean(SettingsActivityFragment.RECEIVE_NOTIFICATIONS_KEY, true);
        editor.commit();
    }

    public static int getPercentageOfTotalMemory(int divider)    {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / divider;
        return cacheSize;
    }
}
