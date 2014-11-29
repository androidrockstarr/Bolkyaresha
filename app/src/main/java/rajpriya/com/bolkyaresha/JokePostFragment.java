package rajpriya.com.bolkyaresha;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
        Bundle args = getArguments();
        if (args != null) {
            FBPagePost post = args.getParcelable(IMAGE_LINK);
            //String link = args.getString(IMAGE_LINK);
            if (!TextUtils.isEmpty(post.getLink()) && TextUtils.equals(post.getType(), "photo")) {
                ((NetworkImageView) rootView.findViewById(R.id.joke_image)).setImageUrl(post.getLink(), App.getImageLoader());
            }

        }
        return rootView;
    }

    public void showPost(FBPagePost post) {
        if (!TextUtils.isEmpty(post.getLink()) && TextUtils.equals(post.getType(), "photo")) {
            ((NetworkImageView) getView().findViewById(R.id.joke_image)).setImageUrl(post.getLink(), App.getImageLoader());
        }
    }
}
