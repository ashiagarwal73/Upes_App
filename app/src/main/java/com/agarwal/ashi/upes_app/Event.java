package com.agarwal.ashi.upes_app;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 500060150 on 10-02-2018.
 */

public class Event implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

                @Override
                public Object createFromParcel(Parcel parcel) {
                    return new Event(parcel);
                }

                @Override
                public Object[] newArray(int i) {
                    return new Event[i];
                }
            };
    Event(Parcel parcel) {
        this.name=parcel.readString();
    }
    Event(String name) {
        this.name=name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
    }
}
