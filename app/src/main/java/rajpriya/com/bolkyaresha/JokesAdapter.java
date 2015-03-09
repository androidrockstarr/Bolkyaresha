package rajpriya.com.bolkyaresha;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Observer;

import rajpriya.com.bolkyaresha.models.FBLikesSummary;
import rajpriya.com.bolkyaresha.models.FBPage;
import rajpriya.com.bolkyaresha.models.FBPagePaging;
import rajpriya.com.bolkyaresha.models.FBPagePost;
import rajpriya.com.bolkyaresha.models.FBPostShares;

/**
 * Created by rajkumar.waghmare on 2014/11/29.
 */
public class JokesAdapter extends BaseAdapter {
    private ArrayList<FBPagePost> mPosts = new ArrayList<FBPagePost>();
    private FBPagePaging mCurrentPaging;
    private boolean isScrolling = false;

    public JokesAdapter(final FBPage firstPage) {
        new CleanDataTask().execute(firstPage.getData());
        mCurrentPaging = firstPage.getPaging();
    }

    public void setIsScrolling(boolean isScrolling) {
        this.isScrolling = isScrolling;
    }

    public ArrayList<FBPagePost> cleanData(ArrayList<FBPagePost> posts) {
        ArrayList<FBPagePost> ret = new ArrayList<FBPagePost>();
        for(int position=0; position < posts.size(); position++) {
            if (!TextUtils.isEmpty(posts.get(position).getObjectId()) &&
                    TextUtils.equals(posts.get(position).getType(), "photo")) {
                ret.add(posts.get(position));
            }
        }
        return ret;
    }


    @Override
    public int getCount() {
        return mPosts.size();
    }

    @Override
    public Object getItem(int position) {
        return mPosts.get(position);
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


        if(mPosts.size() - position < 15) {
            loadNextFeeds(convertView.getContext(), false);
        }

        final PostHolder holder = new PostHolder(convertView);

        final FBPagePost post = mPosts.get(position);
        String url = "https://graph.facebook.com/"+ post.getObjectId() +"/picture?type=normal";
        final View finalConvertView = convertView;

        holder.image.setImageUrl(url, App.getImageLoader());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(finalConvertView.getContext(), JokeDetailsActivity.class);
                i.putExtra("post", post);
                finalConvertView.getContext().startActivity(i);
            }
        });
        return convertView;
    }

    private void loadNextFeeds(final Context parentActivity, final boolean firstLoad) {
        String url;
        if(!firstLoad) {
            url = mCurrentPaging.getNext();
        } else {
            url = App.FB_PAGE_URL;
        }
        if(TextUtils.isEmpty(url)) {
            return;
        }

        if(parentActivity instanceof DataLoadingListener) {
            ((DataLoadingListener)parentActivity).onDataLoadingStarted();

        }

        JsonObjectRequest pagePostRequest = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(parentActivity instanceof DataLoadingListener) {
                            ((DataLoadingListener)parentActivity).onDataLoadingFinished();
                        }

                        Gson gson = new Gson();
                        final FBPage page = gson.fromJson(response.toString(), FBPage.class);
                        new CleanDataTask().execute(page.getData());
                        mCurrentPaging = page.getPaging();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO: show network error, disable progress bar
                        if(parentActivity instanceof DataLoadingListener) {
                            ((DataLoadingListener)parentActivity).onDataLoadingFinished();

                        }
                    }
                }
        );
        App.getVolleyRequestQueue().add(pagePostRequest);
    }

/*    private static class Holder {
        public static Holder get(View convertView) {

        }

    }*/

    public interface DataLoadingListener {
        public void onDataLoadingStarted();
        public void onDataLoadingFinished();
    }

    public void refresh(Context caller, boolean firstLoad) {
        mPosts.clear();
        notifyDataSetChanged();
        loadNextFeeds(caller, firstLoad);
    }


    public  class CleanDataTask extends AsyncTask<ArrayList<FBPagePost>, Void, ArrayList<FBPagePost>> {
        @Override
        protected void onPostExecute(ArrayList<FBPagePost> cleaned) {
            mPosts.addAll(cleaned);
            notifyDataSetChanged();
        }
        @Override
        protected ArrayList<FBPagePost> doInBackground (ArrayList < FBPagePost >...params){
            ArrayList<FBPagePost> input = (ArrayList<FBPagePost>) params[0];
            ArrayList<FBPagePost> ret = cleanData(input);
            //ret.addAll(cleanData((ArrayList<FBPagePost>) params[0]));
            return ret;
        }
    }



    private static final class PostHolder {

        final NetworkImageView image;  // volley image for network-hosted images
        final TextView likes;
        final TextView likesText;
        final TextView comments;
        final TextView commentsText;

        PostHolder(View v) {
            image = (NetworkImageView)v.findViewById(R.id.big_joke_image);
            likes = (TextView)v.findViewById(R.id.no_likes);
            comments = (TextView)v.findViewById(R.id.no_comments);
            likesText = (TextView)v.findViewById(R.id.no_likes_text);
            commentsText = (TextView)v.findViewById(R.id.no_comments_text);
            v.setTag(this);
        }

        static PostHolder get(View v) {
            if (v.getTag() instanceof PostHolder) {
                return (PostHolder) v.getTag();
            }
            return new PostHolder(v);
        }
    }
}
