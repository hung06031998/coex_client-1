
package com.upit.coex.user.repository.model.data.booking;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Room implements Serializable {

    @SerializedName("about")
    private String mAbout;
    @SerializedName("coworkingId")
    private String mCoworkingId;
    @SerializedName("id")
    private String mId;
    @SerializedName("maxPerson")
    private Long mMaxPerson;
    @SerializedName("name")
    private String mName;
    @SerializedName("price")
    private Long mPrice;

    public String getAbout() {
        return mAbout;
    }

    public void setAbout(String about) {
        mAbout = about;
    }

    public String getCoworkingId() {
        return mCoworkingId;
    }

    public void setCoworkingId(String coworkingId) {
        mCoworkingId = coworkingId;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Long getMaxPerson() {
        return mMaxPerson;
    }

    public void setMaxPerson(Long maxPerson) {
        mMaxPerson = maxPerson;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Long getPrice() {
        return mPrice;
    }

    public void setPrice(Long price) {
        mPrice = price;
    }

}
