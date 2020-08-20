package com.upit.coex.user.repository.model.data.coin;

import com.google.gson.annotations.SerializedName;

public class ListExchangePoint {
    @SerializedName("point")
    private Double mCoin;
    @SerializedName("createAt")
    private String mCreateAt;

    public Double getCoin() {
        return mCoin;
    }

    public void setCoin(Double coin) {
        mCoin = coin;
    }

    public String getCreateAt() {
        return mCreateAt;
    }

    public void setCreateAt(String createAt) {
        mCreateAt = createAt;
    }
}
