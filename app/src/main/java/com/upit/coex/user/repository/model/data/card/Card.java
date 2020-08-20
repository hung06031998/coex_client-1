
package com.upit.coex.user.repository.model.data.card;

import com.google.gson.annotations.SerializedName;
import com.upit.coex.user.repository.model.data.base.BaseData;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Card extends BaseData implements Serializable {
    public Card(String mAddress, String mName) {
        this.mAddress = mAddress;
        this.mName = mName;
    }

    public Card() {
    }

    @SerializedName("address")
    private String mAddress;
    @SerializedName("name")
    private String mName;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
