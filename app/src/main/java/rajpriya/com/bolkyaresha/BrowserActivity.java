package rajpriya.com.bolkyaresha;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.android.volley.toolbox.NetworkImageView;
import com.github.pedrovgs.DraggableListener;
import com.github.pedrovgs.DraggablePanel;
import com.github.pedrovgs.DraggableView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import rajpriya.com.bolkyaresha.models.FBPage;
import rajpriya.com.bolkyaresha.models.FBPagePost;


public class BrowserActivity extends ActionBarActivity implements JokesAdapter.DataLoadingListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String PAGE_DATA1 = "fb_page_data1";
    public static final String PAGE_DATA2 = "fb_page_data2";

    private GridView mGrid;
    private JokePostFragment mJokeFragment;
    private SwipeRefreshLayout mSwipeLayout;
    private JokesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        final FBPage page = getIntent().getExtras().getParcelable(PAGE_DATA1);
        mJokeFragment = JokePostFragment.newInstance(page.getData().get(0));
        //pass the first page
        mAdapter = new JokesAdapter(page);
        mGrid = (GridView)findViewById(R.id.jokes_grid);

        mGrid.setAdapter(mAdapter);
        /*mGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BrowserActivity.this, HorizontalBrowsing.class);
                i.putExtra("apdapter", (JokesAdapter)mGrid.getAdapter());
                startActivity(i);
            }
        })*/;

        mGrid.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                mAdapter.setIsScrolling(scrollState != SCROLL_STATE_IDLE);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeLayout.setSize(SwipeRefreshLayout.DEFAULT);

        mGrid.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                //TODO ideal solution should use ScrollY==0 check.
                int firstVisiblePosition = mGrid.getFirstVisiblePosition();
                if(firstVisiblePosition == 0) mSwipeLayout.setEnabled(true);
                else mSwipeLayout.setEnabled(false);

            }
        });
        ImageView header = new ImageView(this);
        header.setImageDrawable(getResources().getDrawable(R.drawable.bolkyaresha_actionbar_image));
        getSupportActionBar().setBackgroundDrawable(header.getDrawable());

        //Show me the money!
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_browser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_top) {
            mGrid.setSelection(0);
            return true;
        }
        //noinspection SimplifiableIfStatement
        else if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_view_type) {
            int currentCOlumns = mGrid.getNumColumns();
            int currentVisible = mGrid.getFirstVisiblePosition();
            if(currentCOlumns == 1) {
                item.setIcon(R.drawable.ic_action_view_as_list);
                int paddingPx = dpToPx(8);
                mGrid.setNumColumns(2);
                mGrid.setVerticalSpacing(paddingPx);
                mGrid.setHorizontalSpacing(paddingPx);
                mGrid.setPadding(paddingPx,paddingPx,paddingPx,0);
            } else {
                item.setIcon(R.drawable.ic_action_view_as_grid);
                int paddingPx = dpToPx(16);
                int paddingPxTop = dpToPx(8);
                mGrid.setNumColumns(1);
                mGrid.setVerticalSpacing(paddingPx);
                mGrid.setHorizontalSpacing(paddingPx);
                mGrid.setPadding(paddingPx,paddingPxTop,paddingPx,0);
            }
            mGrid.setSelection(currentVisible);

            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onDataLoadingStarted() {
        //mProgress.setVisibility(View.VISIBLE);
        mSwipeLayout.setRefreshing(true);
    }

    @Override
    public void onDataLoadingFinished() {
        //mProgress.setVisibility(View.GONE);
        mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        //mSwipeLayout.setRefreshing(true);
        mAdapter.refresh(this, true);
    }

    public static int dpToPx(int dp)    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

}
