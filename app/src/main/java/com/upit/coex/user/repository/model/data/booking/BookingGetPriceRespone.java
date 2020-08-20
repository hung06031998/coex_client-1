
package com.upit.coex.user.repository.model.data.booking;

import com.google.gson.annotations.SerializedName;
import com.upit.coex.user.repository.model.data.base.BaseData;

@SuppressWarnings("unused")
public class BookingGetPriceRespone extends BaseData {

    @SerializedName("price")
    private Long mPrice;

    public Long getPrice() {
        return mPrice;
    }

    public void setPrice(Long price) {
        mPrice = price;
    }

}
