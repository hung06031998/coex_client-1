package com.upit.coex.user.repository.model.remote.withdraw;

import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.coin.WithdrawRequest;
import com.upit.coex.user.repository.model.data.coin.WithdrawRespone;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface WithdrawCoinApiRequest {
    @Headers("Content-Type: application/json")
    @POST("withdraw")
    Single<BaseResponce<WithdrawRespone>> withdrawCoin(@Header("authorization") String TOKEN, @Body WithdrawRequest request);
}
