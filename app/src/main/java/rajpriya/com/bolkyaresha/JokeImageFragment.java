package rajpriya.com.bolkyaresha;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;

import java.io.File;
import java.io.FileOutputStream;

import rajpriya.com.bolkyaresha.R;
import rajpriya.com.bolkyaresha.models.FBPagePost;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JokeImageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JokeImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JokeImageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FBPagePost mPost;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JokeImageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JokeImageFragment newInstance(FBPagePost post) {
        JokeImageFragment fragment = new JokeImageFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, post);
        fragment.setArguments(args);
        return fragment;
    }

    public JokeImageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPost = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_joke_image, container, false);
        final NetworkImageView image = (NetworkImageView)view.findViewById(R.id.big_joke_image);
        if (!TextUtils.isEmpty(mPost.getObjectId()) && TextUtils.equals(mPost.getType(), "photo")) {
            String url = "https://graph.facebook.com/"+ mPost.getObjectId() +"/picture?type=normal";
            image.setImageUrl(url, App.getImageLoader());
            image.setBackgroundColor(android.R.color.holo_red_dark);
        }
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View banner  = view.findViewById(R.id.banner);
                if(banner.getVisibility() == View.VISIBLE) {
                    banner.setVisibility(View.GONE);
                } else {
                    banner.setVisibility(View.VISIBLE);
                }
            }
        });
        view.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImage(image);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private void shareImage(View content) {
        content.setDrawingCacheEnabled(true);

        Bitmap bitmap = content.getDrawingCache();
        File root = Environment.getExternalStorageDirectory();
        File cachePath = new File(root.getAbsolutePath() + "/DCIM/Camera/bolkyaresha_temp_image.jpg");
        if(cachePath.exists()) {
            cachePath.delete();
        }
        try {
            cachePath.createNewFile();
            FileOutputStream ostream = new FileOutputStream(cachePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
            ostream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(cachePath));
        startActivity(Intent.createChooser(share,"Share via"));

    }

}
