
package com.upit.coex.user.repository.model.data.profile;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class EditProfileRequest {

    @SerializedName("name")
    private String mName;
    @SerializedName("phone")
    private String mPhone;

    public EditProfileRequest(String mName, String mPhone) {
        this.mName = mName;
        this.mPhone = mPhone;
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

}
