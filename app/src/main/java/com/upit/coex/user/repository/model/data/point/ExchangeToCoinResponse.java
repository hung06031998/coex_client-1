
package com.upit.coex.user.repository.model.data.point;

import com.google.gson.annotations.SerializedName;
import com.upit.coex.user.repository.model.data.base.BaseData;

@SuppressWarnings("unused")
public class ExchangeToCoinResponse extends BaseData {

    @SerializedName("coin")
    private Float mCoin;
    @SerializedName("point")
    private Long mPoint;

    public Float getCoin() {
        return mCoin;
    }

    public void setCoin(Float coin) {
        mCoin = coin;
    }

    public Long getPoint() {
        return mPoint;
    }

    public void setPoint(Long point) {
        mPoint = point;
    }

}
