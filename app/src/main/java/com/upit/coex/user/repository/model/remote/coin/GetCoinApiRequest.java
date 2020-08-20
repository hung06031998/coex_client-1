package com.upit.coex.user.repository.model.remote.coin;

import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.coin.GetCoinResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface GetCoinApiRequest {
    @Headers("Content-Type: application/json")
    @GET("coin")
    Single<BaseResponce<GetCoinResponse>> getGetCoin(@Header("authorization") String TOKEN);
}
