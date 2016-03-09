package com.apps.bolkyareshafree.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * Created by rajkumar.waghmare on 2014/11/30.
 */

public class FBLike implements Parcelable {

    public FBLike() {}

    public FBLike(Parcel in) {
        Bundle bundle = in.readBundle(getClass().getClassLoader());
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

    public static final Creator<FBLike> CREATOR
            = new Creator<FBLike>() {
        public FBLike createFromParcel(Parcel in) {
            return new FBLike(in);
        }

        public FBLike[] newArray(int size) {
            return new FBLike[size];
        }
    };
}

