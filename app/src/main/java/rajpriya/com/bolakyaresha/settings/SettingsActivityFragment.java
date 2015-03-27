package rajpriya.com.bolakyaresha.settings;

import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rajpriya.com.bolakyaresha.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class SettingsActivityFragment extends Fragment {

    public static final String RECEIVE_NOTIFICATIONS_KEY = "should_we receive_notifications";
    public static final String BOLKYARESHA_APP_FILE = "bolkyaresha_app_file_pref";

    public SettingsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);;

        String version = null;
        try {
            version = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {

        }

        ((TextView)view.findViewById(R.id.app_version)).setText(version);

        /*ToggleButton notificationToggle = (ToggleButton)view.findViewById(R.id.notification);

        final SharedPreferences PREF = getActivity().getSharedPreferences(BOLKYARESHA_APP_FILE, 0);
        boolean receiveNotification = PREF.getBoolean(SettingsActivityFragment.RECEIVE_NOTIFICATIONS_KEY, false);

        notificationToggle.setChecked(receiveNotification);

        notificationToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // Is the toggle on?
                boolean on = ((ToggleButton) v).isChecked();
                PushManager mPushManager = PushManager.getInstance(getActivity().getApplicationContext());

                if(on) {
                    mPushManager.register();
                } else {
                    mPushManager.unregister();
                }



                SharedPreferences.Editor editor = PREF.edit();
                editor.putBoolean(RECEIVE_NOTIFICATIONS_KEY, on);
                editor.commit();

            }
        });*/

        return view;

    }
}
