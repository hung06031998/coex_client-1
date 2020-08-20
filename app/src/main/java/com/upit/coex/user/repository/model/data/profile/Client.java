
package com.upit.coex.user.repository.model.data.profile;

import com.google.gson.annotations.SerializedName;
import com.upit.coex.user.repository.model.data.base.BaseData;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Client extends BaseData implements Serializable {

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
