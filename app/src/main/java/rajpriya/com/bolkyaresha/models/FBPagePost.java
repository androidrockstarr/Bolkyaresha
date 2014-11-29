package rajpriya.com.bolkyaresha.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rajkumar on 11/27/14.
 */

public class FBPagePost implements Parcelable {

    private static final String LINK = "link";
    private static final String TYPE = "type";
    private static final String STATUS_TYPE = "status_type";


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

    @SerializedName(LINK)
    private String link;
    @SerializedName(TYPE)
    private String type;
    @SerializedName(STATUS_TYPE)
    private String status_type;


    public FBPagePost() {}

    public FBPagePost(Parcel in) {
        Bundle bundle = in.readBundle(getClass().getClassLoader());
        link = bundle.getString(LINK);
        type = bundle.getString(TYPE);
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
        bundle.putString(TYPE, type);
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
}

