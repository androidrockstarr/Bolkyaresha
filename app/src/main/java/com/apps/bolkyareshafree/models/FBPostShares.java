package com.apps.bolkyareshafree.models;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rajkumar.waghmare on 2014/11/30.
 */

public class FBPostShares implements Parcelable {

    private static final String COUNT = "count";


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @SerializedName(COUNT)
    private int count;

    public FBPostShares() {}

    public FBPostShares(Parcel in) {
        Bundle bundle = in.readBundle(getClass().getClassLoader());
        count = bundle.getInt(COUNT);
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
        if (!(obj instanceof FBPostShares))
            return false;

        FBPostShares  castObj = (FBPostShares) obj;

        Gson gson = new Gson();

        String thisStr = gson.toJson(this).toString();
        String objStr = gson.toJson(castObj, FBPostShares.class).toString();

        return thisStr.equals(objStr);
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        Bundle bundle = new Bundle();
        bundle.putInt(COUNT, count);
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

    public static final Creator<FBPostShares> CREATOR
            = new Creator<FBPostShares>() {
        public FBPostShares createFromParcel(Parcel in) {
            return new FBPostShares(in);
        }

        public FBPostShares[] newArray(int size) {
            return new FBPostShares[size];
        }
    };
}

