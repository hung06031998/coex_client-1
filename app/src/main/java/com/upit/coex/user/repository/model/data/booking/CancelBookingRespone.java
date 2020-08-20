
package com.upit.coex.user.repository.model.data.booking;

import com.google.gson.annotations.SerializedName;
import com.upit.coex.user.repository.model.data.base.BaseData;

@SuppressWarnings("unused")
public class CancelBookingRespone extends BaseData {

    @SerializedName("key")
    private String mKey;

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

}
