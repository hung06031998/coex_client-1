
package com.upit.coex.user.repository.model.data.booking;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class ListDate {

    @SerializedName("date")
    private String mDate;
    @SerializedName("duration")
    private int mDuration;
    @SerializedName("startTime")
    private int mStartTime;

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    public int getStartTime() {
        return mStartTime;
    }

    public void setStartTime(int mStartTime) {
        this.mStartTime = mStartTime;
    }
}
