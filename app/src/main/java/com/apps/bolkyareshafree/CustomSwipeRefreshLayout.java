package com.apps.bolkyareshafree;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by rajkumar.waghmare on 2014/11/30.
 */
public class CustomSwipeRefreshLayout extends SwipeRefreshLayout{
    private View mScrollView;
    public CustomSwipeRefreshLayout(Context context) {
        super(context);
        setCustomColorSchemeResources();
    }

    public CustomSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomColorSchemeResources();
    }

    /** http://stackoverflow.com/questions/23236650/swiperefreshlayout-in-android*/
    public void setScrollView(View view) {
        this.mScrollView = view;
        /**
         * http://stackoverflow.com/questions/26787375/swiperefreshlayout-setcolorschemecolors-always-throws-a-nullpointerexception
         * Color scheme can only be set only when SwipeRefreshLayout has a child.
         */
        setCustomColorSchemeResources();
    }

    @Override
    public boolean canChildScrollUp() {
        if(mScrollView == null ) {
            return super.canChildScrollUp();
        }
        return ViewCompat.canScrollVertically(mScrollView, -1);
    }

    private void setCustomColorSchemeResources() {
        setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }
}
