
package com.upit.coex.user.repository.model.data.booking;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BookingRequest {
    @SerializedName("listDate")
    private List<ListDate> mListDate;
    @SerializedName("numberPerson")
    private int mNumberPerson;
    @SerializedName("room_id")
    private String mRoomId;

    public BookingRequest(int mNumberPerson,List<ListDate> mListDate, String mRoomId) {
        this.mListDate = mListDate;
        this.mNumberPerson = mNumberPerson;
        this.mRoomId = mRoomId;
    }

    public List<ListDate> getListDate() {
        return mListDate;
    }

    public void setListDate(List<ListDate> listDate) {
        mListDate = listDate;
    }

    public int getNumberPerson() {
        return mNumberPerson;
    }

    public void setNumberPerson(int numberPerson) {
        mNumberPerson = numberPerson;
    }

    public String getRoomId() {
        return mRoomId;
    }

    public void setRoomId(String roomId) {
        mRoomId = roomId;
    }

}
