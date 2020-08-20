
package com.upit.coex.user.repository.model.data.booking;

import com.google.gson.annotations.SerializedName;
import com.upit.coex.user.repository.model.data.base.BaseData;

@SuppressWarnings("unused")
public class CreatePaymentResponse extends BaseData {

    @SerializedName("orderurl")
    private String mOrderurl;
    @SerializedName("returncode")
    private Long mReturncode;
    @SerializedName("returnmessage")
    private String mReturnmessage;
    @SerializedName("zptranstoken")
    private String mZptranstoken;

    public String getOrderurl() {
        return mOrderurl;
    }

    public void setOrderurl(String orderurl) {
        mOrderurl = orderurl;
    }

    public Long getReturncode() {
        return mReturncode;
    }

    public void setReturncode(Long returncode) {
        mReturncode = returncode;
    }

    public String getReturnmessage() {
        return mReturnmessage;
    }

    public void setReturnmessage(String returnmessage) {
        mReturnmessage = returnmessage;
    }

    public String getZptranstoken() {
        return mZptranstoken;
    }

    public void setZptranstoken(String zptranstoken) {
        mZptranstoken = zptranstoken;
    }

}
