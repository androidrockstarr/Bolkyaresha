package rajpriya.com.bolakyaresha.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by rajkumar on 11/27/14.
 */

public class FBPagePost implements Parcelable {

    private static final String LINK = "link";
    private static final String OBJECT_ID = "object_id";
    private static final String PICTURE = "picture";
    private static final String TYPE = "type";
    private static final String STATUS_TYPE = "status_type";
    private static final String SHARES = "shares";
    private static final String LIKES = "likes";
    private static final String COMMENTS = "comments";


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus_type() {
        return status_type;
    }

    public void setStatus_type(String status_type) {
        this.status_type = status_type;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


    public FBPostLikes getLikes() {
        return likes;
    }

    public void setLikes(FBPostLikes likes) {
        this.likes = likes;
    }

    public FBPostShares getShares() {
        return shares;
    }

    public void setShares(FBPostShares shares) {
        this.shares = shares;
    }

    public FBPostLikes getComments() {
        return comments;
    }

    public void setComments(FBPostLikes comments) {
        this.comments = comments;
    }



    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @SerializedName(LINK)
    private String link;
    @SerializedName(OBJECT_ID)
    private String objectId;
    @SerializedName(TYPE)
    private String type;
    @SerializedName(STATUS_TYPE)
    private String status_type;
    @SerializedName(PICTURE)
    private String picture;
    @SerializedName(SHARES)
    private FBPostShares shares;
    @SerializedName(LIKES)
    private FBPostLikes likes;
    @SerializedName(COMMENTS)
    private FBPostLikes comments;

    private String mTotalLikes = "0";
    private String mTotalComments = "0";


    public String getTotalLikes() {
        return mTotalLikes;
    }

    public void setTotalLikes(String mTotalLikes) {
        this.mTotalLikes = mTotalLikes;
    }

    public String getTotalComments() {
        return mTotalComments;
    }

    public void setTotalComments(String mTotalComments) {
        this.mTotalComments = mTotalComments;
    }

    public FBPagePost() {}

    public FBPagePost(Parcel in) {
        Bundle bundle = in.readBundle(getClass().getClassLoader());
        link = bundle.getString(LINK);
        objectId = bundle.getString(OBJECT_ID);
        type = bundle.getString(TYPE);
        picture = bundle.getString(PICTURE);
        status_type = bundle.getString(STATUS_TYPE);

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof FBPagePost))
            return false;

        FBPagePost  castObj = (FBPagePost) obj;

        Gson gson = new Gson();

        String thisStr = gson.toJson(this).toString();
        String objStr = gson.toJson(castObj, FBPagePost.class).toString();

        return thisStr.equals(objStr);
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        Bundle bundle = new Bundle();
        bundle.putString(LINK, link);
        bundle.putString(OBJECT_ID, objectId);
        bundle.putString(TYPE, type);
        bundle.putString(PICTURE, picture);
        bundle.putString(STATUS_TYPE, status_type);
        out.writeBundle(bundle);
    }

    /**
     *
     * This field is needed for Android to be able to
     * create new objects, individually or as arrays.
     *
     * This also means that you can use use the default
     * constructor to create the object and use another
     * method to hyrdate it as necessary.
     *
     */

    public static final Creator<FBPagePost> CREATOR
            = new Creator<FBPagePost>() {
        public FBPagePost createFromParcel(Parcel in) {
            return new FBPagePost(in);
        }

        public FBPagePost[] newArray(int size) {
            return new FBPagePost[size];
        }
    };

    /**
     * Created by rajkumar.waghmare on 2014/11/30.
     */

    public static class FBPostLikes implements Parcelable {

        private static final String DATA = "data";


        public ArrayList<FBLike> getData() {
            return data;
        }

        public void setData(ArrayList<FBLike> data) {
            this.data = data;
        }

        @SerializedName(DATA)
        private ArrayList<FBLike> data;

        public FBPostLikes() {}

        public FBPostLikes(Parcel in) {
            Bundle bundle = in.readBundle(getClass().getClassLoader());
            data = bundle.getParcelableArrayList(DATA);
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (obj == this)
                return true;
            if (!(obj instanceof FBPostLikes))
                return false;

            FBPostLikes castObj = (FBPostLikes) obj;

            Gson gson = new Gson();

            String thisStr = gson.toJson(this).toString();
            String objStr = gson.toJson(castObj, FBPostLikes.class).toString();

            return thisStr.equals(objStr);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(DATA, data);
            out.writeBundle(bundle);
        }

        /**
         *
         * This field is needed for Android to be able to
         * create new objects, individually or as arrays.
         *
         * This also means that you can use use the default
         * constructor to create the object and use another
         * method to hyrdate it as necessary.
         *
         */

        public static final Creator<FBPostLikes> CREATOR
                = new Creator<FBPostLikes>() {
            public FBPostLikes createFromParcel(Parcel in) {
                return new FBPostLikes(in);
            }

            public FBPostLikes[] newArray(int size) {
                return new FBPostLikes[size];
            }
        };
    }
}

