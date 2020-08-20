package com.upit.coex.user.repository.model.remote.withdraw;

import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.coin.HistoryExchangeRespone;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface GetHistoryExchangeApiRequest {
    @Headers("Content-Type: application/json")
    @GET("history")
    Single<BaseResponce<HistoryExchangeRespone>> getHistoryExchange(@Header("authorization") String TOKEN);
}
