
package com.upit.coex.user.repository.model.data.booking;

import com.google.gson.annotations.SerializedName;
import com.upit.coex.user.repository.model.data.base.BaseData;

@SuppressWarnings("unused")
public class VnpayCreatePayment extends BaseData {

    @SerializedName("vnpayUrl")
    private String mVnpayUrl;

    public String getVnpayUrl() {
        return mVnpayUrl;
    }

    public void setVnpayUrl(String vnpayUrl) {
        mVnpayUrl = vnpayUrl;
    }

}
