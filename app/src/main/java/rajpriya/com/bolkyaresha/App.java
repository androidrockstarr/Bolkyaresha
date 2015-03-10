package rajpriya.com.bolkyaresha;

import android.app.Application;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by rajkumar.waghmare on 2014/11/29.
 */
public class App extends Application{

    private static RequestQueue mRequestQueue;
    private static ImageLoader mImageLoader;

    public static ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public static RequestQueue getVolleyRequestQueue() {
        return mRequestQueue;
    }

    private static final String PAGE_ID = "OfficialChintooPage";
    public static  final String FB_PAGE_URL = "https://graph.facebook.com/"+PAGE_ID+"/feed?access_token=668151053299134|iR9g4u-L7ok1_mky8-cNDv8gdV4";
    @Override
    public void onCreate() {
        super.onCreate();
        mRequestQueue = Volley.newRequestQueue(this);
        mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });

    }
}
