package rajpriya.com.bolkyaresha;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.GridView;

import com.google.android.gms.common.api.Api;

import rajpriya.com.bolkyaresha.models.FBPage;

/**
 * Created by rajkumar on 22/3/15.
 */

/**
 * A placeholder fragment containing a simple view.
 */
public class GridFragment extends Fragment {

    public static final String PAGE_DATA = "fb_page_data";

    private GridView mGrid;
    private JokesAdapter mAdapter;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    enum TYPE {
        MOBILE_ONLY,
        FACEBOOK_PAGE
    };

    private TYPE mGridType;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static GridFragment newInstance(int sectionNumber) {
        GridFragment fragment = new GridFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public GridFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabbed_browser2, container, false);
        setHasOptionsMenu(true);

        final FBPage page;

        if(getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
            mGridType = TYPE.MOBILE_ONLY;
            page = getActivity().getIntent().getExtras().getParcelable(BrowserActivity.PAGE_DATA1);
        } else {
            mGridType = TYPE.FACEBOOK_PAGE;
            page = getActivity().getIntent().getExtras().getParcelable(BrowserActivity.PAGE_DATA1);
        }

        mAdapter = new JokesAdapter(page);
        mGrid = (GridView)rootView.findViewById(R.id.jokes_grid);
        mGrid.setAdapter(mAdapter);

        mGrid.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                //TODO ideal solution should use ScrollY==0 check.
                int firstVisiblePosition = mGrid.getFirstVisiblePosition();
                if (firstVisiblePosition == 0) ((TabbedBrowserActivity)getActivity()).getSwipeLayout().setEnabled(true);
                else ((TabbedBrowserActivity)getActivity()).getSwipeLayout().setEnabled(false);

            }
        });

        return rootView;
    }

    public void refresh() {
        if(this.mAdapter != null) {
            this.mAdapter.refresh(getActivity(), true);
        }
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
                int paddingPx = TabbedBrowserActivity.dpToPx(8);
                mGrid.setNumColumns(2);
                mGrid.setVerticalSpacing(paddingPx);
                mGrid.setHorizontalSpacing(paddingPx);
                mGrid.setPadding(paddingPx,paddingPx,paddingPx,0);
            } else {
                item.setIcon(R.drawable.ic_action_view_as_grid);
                int paddingPx = TabbedBrowserActivity.dpToPx(8);
                int paddingPxTop = TabbedBrowserActivity.dpToPx(8);
                mGrid.setNumColumns(1);
                mGrid.setVerticalSpacing(paddingPx);
                mGrid.setHorizontalSpacing(paddingPx);
                mGrid.setPadding(paddingPx,paddingPxTop,paddingPx,0);
            }
            mGrid.setSelection(currentVisible);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

