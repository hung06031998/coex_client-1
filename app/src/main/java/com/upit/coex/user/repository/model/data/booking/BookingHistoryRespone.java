
package com.upit.coex.user.repository.model.data.booking;

import com.upit.coex.user.repository.model.data.base.BaseData;

import java.util.List;

@SuppressWarnings("unused")
public class BookingHistoryRespone extends BaseData {

    private List<Long> listData;

    public List<Long> getmTransactionId() {
        return listData;
    }

    public void setmTransactionId(List<Long> mTransactionId) {
        this.listData = mTransactionId;
    }
}
