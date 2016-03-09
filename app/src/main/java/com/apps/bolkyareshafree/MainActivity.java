package com.apps.bolkyareshafree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.groboot.pushapps.DeviceIDTypes;
import com.groboot.pushapps.PushManager;

import org.json.JSONObject;

import com.apps.bolkyareshafree.models.FBPage;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        int bannerIds[] = new int[]{R.drawable.banner_1, R.drawable.banner_2, R.drawable.banner_3,
                R.drawable.banner_4,};
        ((ImageView)findViewById(R.id.banner)).setImageResource(bannerIds[(App.LUANCH_COUNT)%4]);
        App.LUANCH_COUNT++;
        fetchPagePosts();

        /*final SharedPreferences PREF = getSharedPreferences(SettingsActivityFragment.BOLKYARESHA_APP_FILE, 0);
        boolean receiveNotification = PREF.getBoolean(SettingsActivityFragment.RECEIVE_NOTIFICATIONS_KEY, false);
        if(receiveNotification) {*/
            /** optional - sets the source of the device id for identification, default is DeviceIDTypes.IMEI.
             If you use this method, you must do it before your first call to init(Context,String,String), and
             it is recommended to never change the id type after setting it for the first time
             to avoid duplicate devices registrations.
             http://wiki.pushapps.mobi/display/PUSHAPPS/Android+Getting+Started*/
            PushManager.getInstance(getApplicationContext()).setDeviceIDType(DeviceIDTypes.ANDROID_ID);
            // Start PushApps and register to the push notification service (GCM)
            PushManager.init(getApplicationContext(), App.GOOGLE_API_PROJECT_NUMBER, App.PUSHAPPS_APP_TOKEN);
            //optional - allows more than on notifications in the status bar, default is false
            PushManager.getInstance(getApplicationContext()).setShouldStackNotifications(true);
            PushManager.getInstance(getApplicationContext()).setShouldStartIntentAsNewTask(false);
            PushManager.getInstance(getApplicationContext()).setIntentNameToLaunch("com.apps.bolkyareshafree.notifications.NotificationActivity");
            PushManager.getInstance(getApplicationContext()).setNotificationIcon(R.drawable.round_app_icon);
        //}

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void fetchPagePosts() {

        JsonObjectRequest pagePostRequest = new JsonObjectRequest(Request.Method.GET, App.FB_APP_PAGE_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        final FBPage page1 = gson.fromJson(response.toString(), FBPage.class);
                        final Intent i = new Intent(MainActivity.this, TabbedBrowserActivity.class);
                        i.putExtra(BrowserActivity.PAGE_DATA1, page1);
                        JsonObjectRequest pagePostRequest2 = new JsonObjectRequest(Request.Method.GET, App.FB_PAGE_URL, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Gson gson = new Gson();
                                        FBPage page2 = gson.fromJson(response.toString(), FBPage.class);
                                        i.putExtra(BrowserActivity.PAGE_DATA2, page2);
                                        startActivity(i);
                                        finish();
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Gson gson = new Gson();
                                    }
                                }
                        );
                        App.getVolleyRequestQueue().add(pagePostRequest2);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO: show network error, disable progress bar
                        Gson gson = new Gson();
                    }
                }
        );
        App.getVolleyRequestQueue().add(pagePostRequest);
    }
}
