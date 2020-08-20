
package com.upit.coex.user.repository.model.data.coin;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.upit.coex.user.repository.model.data.base.BaseData;

@SuppressWarnings("unused")
public class HistoryExchangeRespone extends BaseData {

    @SerializedName("list_exchange_coin")
    private List<ListExchangeCoin> mListExchangeCoin;
    @SerializedName("list_exchange_point")
    private List<ListExchangePoint> mListExchangePoint;

    public List<ListExchangeCoin> getListExchangeCoin() {
        return mListExchangeCoin;
    }

    public void setListExchangeCoin(List<ListExchangeCoin> listExchangeCoin) {
        mListExchangeCoin = listExchangeCoin;
    }

    public List<ListExchangePoint> getListExchangePoint() {
        return mListExchangePoint;
    }

    public void setListExchangePoint(List<ListExchangePoint> listExchangePoint) {
        mListExchangePoint = listExchangePoint;
    }

}
