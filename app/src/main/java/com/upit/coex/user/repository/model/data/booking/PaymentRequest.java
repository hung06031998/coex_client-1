
package com.upit.coex.user.repository.model.data.booking;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class PaymentRequest {
    @SerializedName("transaction_id")
    private String mTransaction_id;

    public PaymentRequest(String mTransaction_id) {
        this.mTransaction_id = mTransaction_id;
    }

    public String getmTransaction_id() {
        return mTransaction_id;
    }

    public void setmTransaction_id(String mTransaction_id) {
        this.mTransaction_id = mTransaction_id;
    }
}
