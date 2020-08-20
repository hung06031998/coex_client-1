
package com.upit.coex.user.repository.model.data.coin;

import com.google.gson.annotations.SerializedName;
import com.upit.coex.user.repository.model.data.base.BaseData;

@SuppressWarnings("unused")
public class WithdrawRespone extends BaseData {

    @SerializedName("transaction_hash")
    private String mTransactionHash;

    public String getTransactionHash() {
        return mTransactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        mTransactionHash = transactionHash;
    }

}
