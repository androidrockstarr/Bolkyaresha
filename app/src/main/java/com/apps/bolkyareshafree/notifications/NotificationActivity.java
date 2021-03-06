package com.apps.bolkyareshafree.notifications;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.groboot.pushapps.PushManager;
import com.polites.android.GestureImageView;

import org.json.JSONException;
import org.json.JSONObject;

import com.apps.bolkyareshafree.App;
import com.apps.bolkyareshafree.R;
import com.apps.bolkyareshafree.util.Utils;

public class NotificationActivity extends ActionBarActivity {

    private static final String CUSTOM_JSON_KEY = "imageUrl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        ImageView header = new ImageView(this);
        header.setImageDrawable(getResources().getDrawable(R.drawable.actionbar_banner));
        getSupportActionBar().setBackgroundDrawable(header.getDrawable());
        setTitle("");

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            // This is the notification message
            String notificationMessage = bundle.getString(PushManager.NOTIFICATION_MESSAGE_KEY);
            // This is the notification title
            String notificationTitle = bundle.getString(PushManager.NOTIFICATION_TITLE_KEY);
            // This is the notification link
            String notificationLink = bundle.getString(PushManager.NOTIFICATION_LINK_KEY);

            // This is the notification custom JSON, the default key will be PushManager.EXTRA_DATA
            String notificationCustomJSON = bundle.getString(CUSTOM_JSON_KEY);
            if(bundle.containsKey(CUSTOM_JSON_KEY)) {
                try {

                    JSONObject jsonObject = new JSONObject(notificationCustomJSON);
                    String url = jsonObject.getString(CUSTOM_JSON_KEY);

                    if (!TextUtils.isEmpty(url)) {
                        final GestureImageView image = (GestureImageView) findViewById(R.id.big_joke_image);

                        App.getImageLoader().get(url, new ImageLoader.ImageListener() {
                            @Override
                            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                                if (response.getBitmap() != null) {
                                    if (response.getBitmap().getHeight() == 1 && response.getBitmap().getWidth() == 1) {
                                        //image.setImageResource(R.drawable.default_no_image);
                                    } else {
                                        image.setImageBitmap(response.getBitmap());
                                    }
                                }
                            }

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //image.setImageResource(R.drawable.default_no_image);
                            }
                        });


                         findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 Utils.shareImage(image, NotificationActivity.this);
                             }
                         });

                    }

                } catch (JSONException e) {
                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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

        //
        if (id == android.R.id.home) {
            return true;
        }

        if (id == R.id.action_close) {
            if(!isFinishing()) {
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
