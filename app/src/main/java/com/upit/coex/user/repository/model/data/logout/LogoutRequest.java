package com.upit.coex.user.repository.model.data.logout;

import com.google.gson.annotations.SerializedName;

public class LogoutRequest {
    @SerializedName("firebase_token")
    private String mFcmToken;

    public LogoutRequest(String mFcmToken) {
        this.mFcmToken = mFcmToken;
    }

    public String getmFcmToken() {
        return mFcmToken;
    }

    public void setmFcmToken(String mFcmToken) {
        this.mFcmToken = mFcmToken;
    }
}
