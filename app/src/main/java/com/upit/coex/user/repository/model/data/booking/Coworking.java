
package com.upit.coex.user.repository.model.data.booking;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Coworking implements Serializable {

    @SerializedName("about")
    private String mAbout;
    @SerializedName("address")
    private String mAddress;
    @SerializedName("id")
    private String mId;
    @SerializedName("location")
    private List<Double> mLocation;
    @SerializedName("name")
    private String mName;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("photo")
    private List<String> mPhoto;
    @SerializedName("service")
    private Service mService;
    @SerializedName("userId")
    private String mUserId;

    public String getAbout() {
        return mAbout;
    }

    public void setAbout(String about) {
        mAbout = about;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public List<Double> getLocation() {
        return mLocation;
    }

    public void setLocation(List<Double> location) {
        mLocation = location;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public List<String> getPhoto() {
        return mPhoto;
    }

    public void setPhoto(List<String> photo) {
        mPhoto = photo;
    }

    public Service getService() {
        return mService;
    }

    public void setService(Service service) {
        mService = service;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

}
