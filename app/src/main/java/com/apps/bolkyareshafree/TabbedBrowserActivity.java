package com.apps.bolkyareshafree;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;

import com.apps.bolkyareshafree.settings.SettingsActivity;

public class TabbedBrowserActivity extends ActionBarActivity implements ActionBar.TabListener,
        JokesAdapter.DataLoadingListener,
        SwipeRefreshLayout.OnRefreshListener,
        GridView.OnScrollListener{

    public static final String PAGE_DATA1 = "fb_page_data1";
    public static final String PAGE_DATA2 = "fb_page_data2";

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    int TAB0 = 0;
    int TAB1 = 1;

    private int mSelectedTab = TAB0;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    MenuItem mMenuItem;


    boolean isScrolling = false;

    private CustomSwipeRefreshLayout mSwipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_browser2);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }


        mSwipeLayout = (CustomSwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeLayout.setSize(SwipeRefreshLayout.DEFAULT);

        ImageView header = new ImageView(this);
        header.setImageDrawable(getResources().getDrawable(R.drawable.actionbar_banner));
        getSupportActionBar().setBackgroundDrawable(header.getDrawable());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabbed_browser, menu);
        mMenuItem = menu.findItem(R.id.action_top);
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
            startActivity(new Intent(TabbedBrowserActivity.this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
        GridFragment current = (GridFragment) mSectionsPagerAdapter.getItem(tab.getPosition());
        if(mSwipeLayout != null) {
            current.onTabSelected(mSwipeLayout);
        }
        mSelectedTab = tab.getPosition();
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int state) {
        if(state != SCROLL_STATE_FLING) {
            isScrolling = false;
            if(mMenuItem == null) {
                return;
            }
            if(absListView.getFirstVisiblePosition() != 0) {
                if(!mMenuItem.isVisible()) {
                    //invalidateOptionsMenu();
                    mMenuItem.setVisible(true);
                }

            } else {
                if(mMenuItem.isVisible()) {
                    //invalidateOptionsMenu();
                    mMenuItem.setVisible(false);
                }
            }


        } else {
            isScrolling = true;
        }

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        GridFragment frag1 = GridFragment.newInstance(1);
        GridFragment frag2 = GridFragment.newInstance(2);

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return position == 0 ? frag1 : frag2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Latest";
                case 1:
                    return "More";
                default:
                    return "";
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }



        public void refresh() {
            if(mSelectedTab == TAB0) {
                ((GridFragment)getItem(0)).refresh();
            } else {
                ((GridFragment)getItem(1)).refresh();
            }
            //notifyDataSetChanged();
        }

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
        mSectionsPagerAdapter.refresh();
    }

    public static int dpToPx(int dp)    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }


    public CustomSwipeRefreshLayout getSwipeLayout() {
        return mSwipeLayout;
    }

    public void setSwipeLayout(CustomSwipeRefreshLayout mSwipeLayout) {
        this.mSwipeLayout = mSwipeLayout;
    }

    public boolean isScrolling() {
        return isScrolling;
    }
}
