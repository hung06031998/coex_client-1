package com.upit.coex.user.repository.model.data.coin;

import com.google.gson.annotations.SerializedName;
import com.upit.coex.user.repository.model.data.base.BaseData;

@SuppressWarnings("unused")
public class GetCoinResponse extends BaseData {

    @SerializedName("current_coin")
    private Float mCurrentCoin;

    public Float getCurrentCoin() {
        return mCurrentCoin;
    }

    public void setCurrentCoin(Float currentCoin) {
        mCurrentCoin = currentCoin;
    }

}
