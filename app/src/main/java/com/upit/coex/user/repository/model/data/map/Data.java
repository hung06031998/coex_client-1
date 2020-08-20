
package com.upit.coex.user.repository.model.data.map;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Data implements Serializable {

    @SerializedName("about")
    private String mAbout;
    @SerializedName("address")
    private String mAddress;
    @SerializedName("distance")
    private Double mDistance;
    @SerializedName("id")
    private String mId;
    @SerializedName("location")
    private List<Double> mLocation;
    @SerializedName("name")
    private String mName;
    @SerializedName("photo")
    private List<String> mPhoto;
    @SerializedName("rooms")
    private List<Room> mRooms;
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

    public Double getDistance() {
        return mDistance;
    }

    public void setDistance(Double distance) {
        mDistance = distance;
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

    public List<String> getPhoto() {
        return mPhoto;
    }

    public void setPhoto(List<String> photo) {
        mPhoto = photo;
    }

    public List<Room> getRooms() {
        return mRooms;
    }

    public void setRooms(List<Room> rooms) {
        mRooms = rooms;
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
