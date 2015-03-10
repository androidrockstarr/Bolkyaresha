package rajpriya.com.bolkyaresha;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;

import rajpriya.com.bolkyaresha.models.FBPagePost;

/**
 * Created by rajkumar.waghmare on 2014/11/29.
 * A placeholder fragment containing a simple view.
 */
public class JokePostFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String IMAGE_LINK = "link-to-image";

    private NetworkImageView mImage;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static JokePostFragment newInstance(FBPagePost post) {
        JokePostFragment fragment = new JokePostFragment();
        Bundle args = new Bundle();
        args.putParcelable(IMAGE_LINK, post);
        fragment.setArguments(args);
        return fragment;
    }

    public JokePostFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_browser, container, false);
        mImage = (NetworkImageView) rootView.findViewById(R.id.joke_image);
        Bundle args = getArguments();
        if (args != null) {
            FBPagePost post = args.getParcelable(IMAGE_LINK);
            //String link = args.getString(IMAGE_LINK);
            if (!TextUtils.isEmpty(post.getObjectId()) && TextUtils.equals(post.getType(), "photo")) {
                String url = "https://graph.facebook.com/"+ post.getObjectId() +"/picture?type=normal";
                mImage.setImageUrl(url, App.getImageLoader());
            }

        }
        return rootView;
    }

    public void showPost(FBPagePost post) {
        if (!TextUtils.isEmpty(post.getObjectId()) && TextUtils.equals(post.getType(), "photo")) {
            String url = "https://graph.facebook.com/"+ post.getObjectId() +"/picture?type=normal";
            mImage.setImageUrl(url, App.getImageLoader());
            mImage.setBackgroundColor(android.R.color.holo_red_dark);
            ((ActionBarActivity)JokePostFragment.this.getActivity()).getSupportActionBar().hide();
        }
    }

}
