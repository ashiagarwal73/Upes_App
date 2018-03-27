package com.agarwal.ashi.upes_app.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 500060150 on 25-03-2018.
 */

public class LayoutInformation implements Parcelable{
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Object createFromParcel(Parcel parcel) {
            return new LayoutInformation(parcel);
        }

        @Override
        public Object[] newArray(int i) {
            return new LayoutInformation[i];
        }
    };
    private int layoutColorLight;
    private int layoutColorDark;

    public LayoutInformation(Parcel parcel) {
        this.layoutColorLight=parcel.readInt();
        this.layoutColorDark=parcel.readInt();
    }
    public LayoutInformation(int layoutColorLight, int layoutColorDark) {
        this.layoutColorLight = layoutColorLight;
        this.layoutColorDark = layoutColorDark;
    }

    public int getLayoutColorLight() {
        return layoutColorLight;
    }

    public void setLayoutColorLight(int layoutColorLight) {
        this.layoutColorLight = layoutColorLight;
    }

    public int getLayoutColorDark() {
        return layoutColorDark;
    }

    public void setLayoutColorDark(int layoutColorDark) {
        this.layoutColorDark = layoutColorDark;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.layoutColorLight);
        parcel.writeInt(this.layoutColorDark);
    }
}
