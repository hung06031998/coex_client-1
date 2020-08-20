
package com.upit.coex.user.repository.model.data.booking;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Booking {

    @SerializedName("date_time")
    private String mDateTime;
    @SerializedName("end_time")
    private Long mEndTime;
    @SerializedName("id")
    private String mId;
    @SerializedName("numPerson")
    private Long mNumPerson;
    @SerializedName("start_time")
    private Long mStartTime;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("transactionId")
    private String mTransactionId;

    public String getDateTime() {
        return mDateTime;
    }

    public void setDateTime(String dateTime) {
        mDateTime = dateTime;
    }

    public Long getEndTime() {
        return mEndTime;
    }

    public void setEndTime(Long endTime) {
        mEndTime = endTime;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Long getNumPerson() {
        return mNumPerson;
    }

    public void setNumPerson(Long numPerson) {
        mNumPerson = numPerson;
    }

    public Long getStartTime() {
        return mStartTime;
    }

    public void setStartTime(Long startTime) {
        mStartTime = startTime;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getTransactionId() {
        return mTransactionId;
    }

    public void setTransactionId(String transactionId) {
        mTransactionId = transactionId;
    }

}
