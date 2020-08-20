
package com.upit.coex.user.repository.model.data.point;

import com.google.gson.annotations.SerializedName;
import com.upit.coex.user.repository.model.data.base.BaseData;

@SuppressWarnings("unused")
public class GetPointRespone extends BaseData {

    @SerializedName("current_point")
    private Long mCurrentPoint;

    public Long getCurrentPoint() {
        return mCurrentPoint;
    }

    public void setCurrentPoint(Long currentPoint) {
        mCurrentPoint = currentPoint;
    }

}
