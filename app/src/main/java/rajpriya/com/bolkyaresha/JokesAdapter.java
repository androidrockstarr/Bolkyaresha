package rajpriya.com.bolkyaresha;

import android.database.DataSetObserver;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import rajpriya.com.bolkyaresha.models.FBPagePost;

/**
 * Created by rajkumar.waghmare on 2014/11/29.
 */
public class JokesAdapter implements ListAdapter {
    private ArrayList<FBPagePost> mPosts;

    public JokesAdapter(ArrayList<FBPagePost> posts) {
        mPosts = posts;

    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return mPosts.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.joke_layout, null);
        }
        if (!TextUtils.isEmpty(mPosts.get(position).getLink()) && TextUtils.equals(mPosts.get(position).getType(), "photo")) {
            ((NetworkImageView) convertView.findViewById(R.id.joke_image)).setImageUrl(mPosts.get(position).getLink(), App.getImageLoader());
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

/*    private static class Holder {
        public static Holder get(View convertView) {

        }

    }*/

}
