package rajpriya.com.bolakyaresha.volley;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

import rajpriya.com.bolakyaresha.util.Utils;

/**
 * Created by rajkumar on 27/3/15.
 */

public class BitmapMemCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    public BitmapMemCache() {
        this((int) (Runtime.getRuntime().maxMemory() / 1024) / 8);
    }

    public BitmapMemCache(int sizeInKiloBytes) {
        super(sizeInKiloBytes);
    }

    @Override
    protected int sizeOf(String key, Bitmap bitmap) {
        int size = Utils.getBitmapSize(bitmap) / 1024;
        return size;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    public Bitmap getBitmap(String key) {
        Bitmap bitmap = get(key);
        return bitmap;
    }

    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
