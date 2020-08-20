
package com.upit.coex.user.repository.model.data.booking;

import com.google.gson.annotations.SerializedName;
import com.upit.coex.user.repository.model.data.base.BaseData;

@SuppressWarnings("unused")
public class BookingResponce extends BaseData {

    @SerializedName("transaction_id")
    private String mTransactionId;

    public String getmTransactionId() {
        return mTransactionId;
    }

    public void setmTransactionId(String mTransactionId) {
        this.mTransactionId = mTransactionId;
    }
}
