
package com.upit.coex.user.repository.model.data.point;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ExchangeToCoinRequest {
    @SerializedName("point")
    private Long mPoint;

    public ExchangeToCoinRequest() {
    }

    public ExchangeToCoinRequest(Long mPoint) {
        this.mPoint = mPoint;
    }

    public Long getPoint() {
        return mPoint;
    }

    public void setPoint(Long point) {
        mPoint = point;
    }


}
