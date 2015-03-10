package rajpriya.com.bolkyaresha.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rajkumar on 11/30/14.
 */

public class FBPagePaging implements Parcelable {

    private static final String PREV_LINK = "previous";
    private static final String NEXT_LINK = "next";


    @SerializedName(PREV_LINK)
    private String previous;
    @SerializedName(NEXT_LINK)
    private String next;

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public FBPagePaging() {}


    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }


    public FBPagePaging(Parcel in) {
        Bundle bundle = in.readBundle(getClass().getClassLoader());
        previous = bundle.getString(PREV_LINK);
        next = bundle.getString(NEXT_LINK);
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
        if (!(obj instanceof FBPagePaging))
            return false;

        FBPagePaging  castObj = (FBPagePaging) obj;

        Gson gson = new Gson();

        String thisStr = gson.toJson(this).toString();
        String objStr = gson.toJson(castObj, FBPagePaging.class).toString();

        return thisStr.equals(objStr);
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        Bundle bundle = new Bundle();
        bundle.putString(PREV_LINK, previous);
        bundle.putString(NEXT_LINK, next);
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

    public static final Creator<FBPagePaging> CREATOR
            = new Creator<FBPagePaging>() {
        public FBPagePaging createFromParcel(Parcel in) {
            return new FBPagePaging(in);
        }

        public FBPagePaging[] newArray(int size) {
            return new FBPagePaging[size];
        }
    };
}

