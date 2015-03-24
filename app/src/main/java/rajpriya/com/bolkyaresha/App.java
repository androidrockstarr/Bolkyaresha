package rajpriya.com.bolkyaresha;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.groboot.pushapps.DeviceIDTypes;
import com.groboot.pushapps.PushManager;

import io.fabric.sdk.android.Fabric;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static java.net.URLEncoder.*;

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

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();


    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        mRequestQueue = Volley.newRequestQueue(this);
        mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(50);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });


        //TODO Move pushmanager stuff to App.java and unregister when app session ends

        // optional - sets the source of the device id for identification, default is DeviceIDTypes.IMEI.
        //If you use this method, you must do it before your first call to init(Context,String,String), and
        //it is recommended to never change the id type after setting it for the first time
        //to avoid duplicate devices registrations.
        PushManager.getInstance(getApplicationContext()).setDeviceIDType(DeviceIDTypes.ANDROID_ID);
        // Start PushApps and register to the push notification service (GCM)
        PushManager.init(getApplicationContext(), App.GOOGLE_API_PROJECT_NUMBER, App.PUSHAPPS_APP_TOKEN);
        //optional - allows more than on notifications in the status bar, default is false
        PushManager.getInstance(getApplicationContext()).setShouldStackNotifications(true);
        PushManager.getInstance(getApplicationContext()).setShouldStartIntentAsNewTask(false);
        PushManager.getInstance(getApplicationContext()).setIntentNameToLaunch("rajpriya.com.bolkyaresha.notifications.NotificationActivity");
        //optional - set a your own icon for the notification, defaults is the application icon
        //PushManager.getInstance(getApplicationContext()).setNotificationIcon(R.drawable.notification_icon);


    }



    public synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = analytics.newTracker(R.xml.global_tracker);


            mTrackers.put(trackerId, t);

        }
        return mTrackers.get(trackerId);
    }

}
