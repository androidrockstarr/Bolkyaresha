package rajpriya.com.bolkyaresha.models;


import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by rajkumar on 11/27/14.
 */

public class FBPage implements Parcelable {

    private static final String DATA = "data";
    private static final String PAGING = "paging";

    @SerializedName(DATA)
    private ArrayList<FBPagePost> data;
    @SerializedName(PAGING)
    private FBPagePaging paging;

    public FBPage() {}

    public ArrayList<FBPagePost> getData() {
        return data;
    }

    public void setData(ArrayList<FBPagePost> data) {
        this.data = data;
    }

    public FBPagePaging getPaging() {
        return paging;
    }

    public void setPaging(FBPagePaging paging) {
        this.paging = paging;
    }


    public FBPage(Parcel in) {
        Bundle bundle = in.readBundle(getClass().getClassLoader());
        data = bundle.getParcelableArrayList(DATA);
        paging = (FBPagePaging)bundle.getParcelable(PAGING);
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
        if (!(obj instanceof FBPage))
            return false;

        FBPage  castObj = (FBPage) obj;

        Gson gson = new Gson();

        String thisStr = gson.toJson(this).toString();
        String objStr = gson.toJson(castObj, FBPage.class).toString();

        return thisStr.equals(objStr);
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(DATA, data);
        bundle.putParcelable(PAGING, paging);
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

    public static final Creator<FBPage> CREATOR
            = new Creator<FBPage>() {
        public FBPage createFromParcel(Parcel in) {
            return new FBPage(in);
        }

        public FBPage[] newArray(int size) {
            return new FBPage[size];
        }
    };
}

