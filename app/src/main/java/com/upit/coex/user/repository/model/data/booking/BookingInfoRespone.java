
package com.upit.coex.user.repository.model.data.booking;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class BookingInfoRespone implements Serializable {

    @SerializedName("booking_reference")
    private String mBookingReference;
    @SerializedName("check_in")
    private Boolean mCheckIn;
    @SerializedName("check_out")
    private Boolean mCheckOut;
    @SerializedName("coworking")
    private Coworking mCoworking;
    @SerializedName("create_at")
    private String mCreateAt;
    @SerializedName("date_time")
    private Long mDateTime;
    @SerializedName("duration")
    private Long mDuration;
    @SerializedName("duration_date")
    private Long mDurationDate;
    @SerializedName("id")
    private String mId;
    @SerializedName("numPerson")
    private Long mNumPerson;
    @SerializedName("price")
    private Long mPrice;
    @SerializedName("room")
    private Room mRoom;
    @SerializedName("roomId")
    private String mRoomId;
    @SerializedName("start_time")
    private Long mStartTime;
    @SerializedName("start_time_date")
    private Long mStartTimeDate;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("update_at")
    private String mUpdateAt;
    @SerializedName("userId")
    private String mUserId;

    public String getBookingReference() {
        return mBookingReference;
    }

    public void setBookingReference(String bookingReference) {
        mBookingReference = bookingReference;
    }

    public Boolean getCheckIn() {
        return mCheckIn;
    }

    public void setCheckIn(Boolean checkIn) {
        mCheckIn = checkIn;
    }

    public Boolean getCheckOut() {
        return mCheckOut;
    }

    public void setCheckOut(Boolean checkOut) {
        mCheckOut = checkOut;
    }

    public Coworking getCoworking() {
        return mCoworking;
    }

    public void setCoworking(Coworking coworking) {
        mCoworking = coworking;
    }

    public String getCreateAt() {
        return mCreateAt;
    }

    public void setCreateAt(String createAt) {
        mCreateAt = createAt;
    }

    public Long getDateTime() {
        return mDateTime;
    }

    public void setDateTime(Long dateTime) {
        mDateTime = dateTime;
    }

    public Long getDuration() {
        return mDuration;
    }

    public void setDuration(Long duration) {
        mDuration = duration;
    }

    public Long getDurationDate() {
        return mDurationDate;
    }

    public void setDurationDate(Long durationDate) {
        mDurationDate = durationDate;
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

    public Long getPrice() {
        return mPrice;
    }

    public void setPrice(Long price) {
        mPrice = price;
    }

    public Room getRoom() {
        return mRoom;
    }

    public void setRoom(Room room) {
        mRoom = room;
    }

    public String getRoomId() {
        return mRoomId;
    }

    public void setRoomId(String roomId) {
        mRoomId = roomId;
    }

    public Long getStartTime() {
        return mStartTime;
    }

    public void setStartTime(Long startTime) {
        mStartTime = startTime;
    }

    public Long getStartTimeDate() {
        return mStartTimeDate;
    }

    public void setStartTimeDate(Long startTimeDate) {
        mStartTimeDate = startTimeDate;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getUpdateAt() {
        return mUpdateAt;
    }

    public void setUpdateAt(String updateAt) {
        mUpdateAt = updateAt;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

}
