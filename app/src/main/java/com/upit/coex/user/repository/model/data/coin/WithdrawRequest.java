
package com.upit.coex.user.repository.model.data.coin;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class WithdrawRequest {

    @SerializedName("address")
    private String mAddress;
    @SerializedName("coin")
    private Double mCoin;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public Double getCoin() {
        return mCoin;
    }

    public void setCoin(Double coin) {
        mCoin = coin;
    }

}
