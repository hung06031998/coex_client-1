
package com.upit.coex.user.repository.model.data.map;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CoWokingResponce {

    @SerializedName("code")
    private Long mCode;
    @SerializedName("data")
    private List<Data> mData;
    @SerializedName("message")
    private String mMessage;

    public Long getCode() {
        return mCode;
    }

    public void setCode(Long code) {
        mCode = code;
    }

    public List<Data> getData() {
        return mData;
    }

    public void setData(List<Data> data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

}
