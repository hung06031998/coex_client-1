package com.upit.coex.user.repository.model.data.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("email")
    @Expose
    private String mEmail;
    @SerializedName("password")
    @Expose
    private String mPassword;
    @SerializedName("firebase_token")
    @Expose
    private String mFirebaseToken;


    public String getmFirebaseToken() {
        return mFirebaseToken;
    }

    public void setmFirebaseToken(String mFirebaseToken) {
        this.mFirebaseToken = mFirebaseToken;
    }

    public LoginRequest(String mEmail, String mPassword, String mFirebaseToken) {
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.mFirebaseToken = mFirebaseToken;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }
}
