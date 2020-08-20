
package com.upit.coex.user.repository.model.data.register;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Client  {

    @SerializedName("name")
    private String mName;
    @SerializedName("phone")
    private String mPhone;

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
