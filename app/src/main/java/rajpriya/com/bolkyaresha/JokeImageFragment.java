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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.polites.android.GestureImageView;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

import rajpriya.com.bolkyaresha.R;
import rajpriya.com.bolkyaresha.models.FBLikesSummary;
import rajpriya.com.bolkyaresha.models.FBPagePost;
import rajpriya.com.bolkyaresha.util.Utils;

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
    private String objectId;
    private boolean mShowAds;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment JokeImageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JokeImageFragment newInstance(FBPagePost post, boolean showTopAdsView) {
        JokeImageFragment fragment = new JokeImageFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, post);
        args.putBoolean("show_top_ads_view", showTopAdsView);
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
            mShowAds = getArguments().getBoolean("show_top_ads_view");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_joke_image, container, false);
        final GestureImageView image = (GestureImageView)view.findViewById(R.id.big_joke_image);
        final TextView likes = (TextView)view.findViewById(R.id.no_likes);
        final TextView likesText = (TextView)view.findViewById(R.id.no_likes_text);
        final TextView comments = (TextView)view.findViewById(R.id.no_comments);
        final TextView commentsText = (TextView)view.findViewById(R.id.no_comments_text);

        if (!TextUtils.isEmpty(mPost.getObjectId()) && TextUtils.equals(mPost.getType(), "photo")) {
            String url = "https://graph.facebook.com/"+ mPost.getObjectId() +"/picture?type=normal";


            App.getImageLoader().get(url, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    if (response.getBitmap() != null) {
                        if(response.getBitmap().getHeight() == 1 && response.getBitmap().getWidth() == 1) {
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
            image.setBackgroundColor(android.R.color.holo_red_dark);
        }
        /*image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View banner  = view.findViewById(R.id.banner);
                if(banner.getVisibility() == View.VISIBLE) {
                    banner.setVisibility(View.GONE);
                } else {
                    banner.setVisibility(View.VISIBLE);
                }
            }
        });*/
        view.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.shareImage(image, getActivity());
            }
        });

        String likesTotalUrl = "https://graph.facebook.com/"+ mPost.getObjectId() +"/likes/?summary=true";
        String commentsTotalUrl = "https://graph.facebook.com/"+ mPost.getObjectId() +"/comments/?summary=true";

        JsonObjectRequest totalLikesRequest = new JsonObjectRequest(Request.Method.GET,likesTotalUrl,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        FBLikesSummary summary = gson.fromJson(response.toString(), FBLikesSummary.class);
                        mPost.setTotalLikes(summary.getSummary().get("total_count"));
                        likes.setVisibility(View.VISIBLE);
                        likesText.setVisibility(View.VISIBLE);
                        likes.setText("" + summary.getSummary().get("total_count"));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        likes.setVisibility(View.GONE);
                        likesText.setVisibility(View.GONE);
                    }
                }
        );


        JsonObjectRequest totalCommentsRequest = new JsonObjectRequest(Request.Method.GET,commentsTotalUrl,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        FBLikesSummary summary = gson.fromJson(response.toString(), FBLikesSummary.class);
                        mPost.setTotalLikes(summary.getSummary().get("total_count"));
                        comments.setVisibility(View.VISIBLE);
                        commentsText.setVisibility(View.VISIBLE);
                        comments.setText("" + summary.getSummary().get("total_count"));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        comments.setVisibility(View.GONE);
                        commentsText.setVisibility(View.GONE);
                    }
                }
        );

        App.getVolleyRequestQueue().add(totalLikesRequest);
        App.getVolleyRequestQueue().add(totalCommentsRequest);

        AdView mAdView = (AdView) view.findViewById(R.id.adView);
        if(mShowAds) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        } else {
            mAdView.setVisibility(View.GONE);
        }



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

}
