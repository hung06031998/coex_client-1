
package com.upit.coex.user.repository.model.data.booking;

import com.google.gson.annotations.SerializedName;
import com.upit.coex.user.repository.model.data.base.BaseData;

@SuppressWarnings("unused")
public class CheckResultResponse extends BaseData {

    @SerializedName("code")
    private Boolean mCode;
    @SerializedName("key")
    private String mKey;

    public Boolean getCode() {
        return mCode;
    }

    public void setCode(Boolean code) {
        mCode = code;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

}
