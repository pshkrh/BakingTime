package com.pshkrh.bakingtime.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements Parcelable{
    public int stepId;
    public String shortDesc, desc, videoUrl, thumbnailUrl;

    public Step(int stepId, String shortDesc, String desc, String videoUrl, String thumbnailUrl) {
        this.stepId = stepId;
        this.shortDesc = shortDesc;
        this.desc = desc;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public Step(Parcel p) {
        stepId = p.readInt();
        shortDesc = p.readString();
        desc = p.readString();
        videoUrl = p.readString();
        thumbnailUrl = p.readString();
    }

    public int getStepId() {
        return stepId;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public String getDesc() {
        return desc;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(stepId);
        dest.writeString(shortDesc);
        dest.writeString(desc);
        dest.writeString(videoUrl);
        dest.writeString(thumbnailUrl);
    }

    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>(){
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
