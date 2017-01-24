package com.apps.bolkyareshafree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

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


    /**
     * Fetch the FB posts sequentially, first from App.FB_APP_PAGE_URL and then from App.FB_PAGE_URL
     * TODO: Fire these requests parallely
     */
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
                        Toast.makeText(MainActivity.this, "Network Error. Please try again!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        App.getVolleyRequestQueue().add(pagePostRequest);
    }
}
