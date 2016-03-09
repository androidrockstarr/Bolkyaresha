package com.apps.bolkyareshafree.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by rajkumar.waghmare on 2014/12/13.
 */
public class FBLikesSummary implements Parcelable {

    private static final String SUMMARY = "summary";

    @SerializedName(SUMMARY)
    private HashMap<String, String> summary;

    public HashMap<String, String> getSummary() {
        return summary;
    }

    public FBLikesSummary() {}

    public FBLikesSummary(Parcel in) {
        Bundle bundle = in.readBundle(getClass().getClassLoader());
        summary = (HashMap<String, String>) bundle.getSerializable(SUMMARY);
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
        if (!(obj instanceof FBLike))
            return false;

        FBLike  castObj = (FBLike) obj;

        Gson gson = new Gson();

        String thisStr = gson.toJson(this).toString();
        String objStr = gson.toJson(castObj, FBLike.class).toString();

        return thisStr.equals(objStr);
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(SUMMARY, summary);
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

    public static final Creator<FBLikesSummary> CREATOR
            = new Creator<FBLikesSummary>() {
        public FBLikesSummary createFromParcel(Parcel in) {
            return new FBLikesSummary(in);
        }

        public FBLikesSummary[] newArray(int size) {
            return new FBLikesSummary[size];
        }
    };
}
