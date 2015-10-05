package com.example.joseph.appinforeaderfragment.data;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by joseph on 15/9/23.
 */
public class AppInfo implements Parcelable {

    public Drawable icon;
    public String appName;
    public String packageName;
    public String appVersion;
    public String appSize;
    public String installedTime;
    public String androidVersion;

    protected AppInfo(Parcel in) {
        appName = in.readString();
        packageName = in.readString();
        appVersion = in.readString();
        appSize = in.readString();
        installedTime = in.readString();
        androidVersion = in.readString();
    }

    public static final Creator<AppInfo> CREATOR = new Creator<AppInfo>() {
        @Override
        public AppInfo createFromParcel(Parcel in) {
            return new AppInfo(in);
        }

        @Override
        public AppInfo[] newArray(int size) {
            return new AppInfo[size];
        }
    };

    public AppInfo() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(appName);
        dest.writeString(packageName);
        dest.writeString(appVersion);
        dest.writeString(appSize);
        dest.writeString(installedTime);
        dest.writeString(androidVersion);
    }
}
