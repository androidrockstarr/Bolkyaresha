package rajpriya.com.bolkyaresha.ads;

/**
 * Created by rajkumar on 3/11/15.
 */
import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.ads.*;

import rajpriya.com.bolkyaresha.R;
import rajpriya.com.bolkyaresha.util.Utils;

public class FullScreenAd extends Activity {

    private InterstitialAd interstitial;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreen_ad);

        // Create the interstitial.
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(getString(R.string.ad_unit_id));

        // Create ad request.
        AdRequest adRequest = new AdRequest.Builder().build();

        // Begin loading your interstitial.
        interstitial.loadAd(adRequest);

        //track
        Utils.trackScreen(this, "FullScreenAd");

    }

    /**
     * Called after {@link #onCreate} &mdash; or after {@link #onRestart} when
     * the activity had been stopped, but is now again being displayed to the
     * user.  It will be followed by {@link #onResume}.
     * <p/>
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @see #onCreate
     * @see #onStop
     * @see #onResume
     */
    @Override
    protected void onStart() {
        super.onStart();
        displayInterstitial();
    }

    @Override
    public void onBackPressed() {
        Utils.jumpToHome(this);
        super.onBackPressed();
    }

    // Invoke displayInterstitial() when you are ready to display an interstitial.
    public void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }
}
