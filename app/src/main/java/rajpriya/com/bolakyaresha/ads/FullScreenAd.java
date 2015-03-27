package rajpriya.com.bolakyaresha.ads;

/**
 * Created by rajkumar on 3/11/15.
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.*;

import rajpriya.com.bolakyaresha.R;
import rajpriya.com.bolakyaresha.util.Utils;

public class FullScreenAd extends Activity {

    private InterstitialAd interstitial;

    public enum NextAction {
        LAUNCH_HOME
    }
    public static final String NEXT_ACTION = "next_activity_to_launch";

    private NextAction mNextAction = NextAction.LAUNCH_HOME;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreen_ad);

        mNextAction = (NextAction) getIntent().getSerializableExtra(NEXT_ACTION);

        // Create the interstitial.
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(getString(R.string.interstitial_ad_unit_id_full_screen));
        // Create ad request.
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("2B5FCE7F5371A6FE3457055EA04FDA8E").build();
        // Begin loading your interstitial.
        interstitial.loadAd(adRequest);

        interstitial.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Utils.jumpToHome(FullScreenAd.this);
            }
        });


        //track
        Utils.trackScreen(this, "FullScreenAd");

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mNextAction == NextAction.LAUNCH_HOME) {
                    Utils.jumpToHome(FullScreenAd.this);
                }
                finish();
            }
        });


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
        if(mNextAction == NextAction.LAUNCH_HOME) {
            Utils.jumpToHome(FullScreenAd.this);
        }
        super.onBackPressed();
    }

    // Invoke displayInterstitial() when you are ready to display an interstitial.
    public void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
            finish();
        }
    }
}
