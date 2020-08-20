
package com.upit.coex.user.repository.model.data.profile;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ChangePassRequest {

    @SerializedName("firebase_token")
    private String mFirebaseToken;
    @SerializedName("newPassword")
    private String mNewPassword;
    @SerializedName("oldPassword")
    private String mOldPassword;

    public ChangePassRequest(String mFirebaseToken, String mNewPassword, String mOldPassword) {
        this.mFirebaseToken = mFirebaseToken;
        this.mNewPassword = mNewPassword;
        this.mOldPassword = mOldPassword;
    }

    public String getFirebaseToken() {
        return mFirebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        mFirebaseToken = firebaseToken;
    }

    public String getNewPassword() {
        return mNewPassword;
    }

    public void setNewPassword(String newPassword) {
        mNewPassword = newPassword;
    }

    public String getOldPassword() {
        return mOldPassword;
    }

    public void setOldPassword(String oldPassword) {
        mOldPassword = oldPassword;
    }

}
