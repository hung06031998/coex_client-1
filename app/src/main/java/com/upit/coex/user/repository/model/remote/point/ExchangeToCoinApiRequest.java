package com.upit.coex.user.repository.model.remote.point;

import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.point.ExchangeToCoinRequest;
import com.upit.coex.user.repository.model.data.point.ExchangeToCoinResponse;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface ExchangeToCoinApiRequest {
    @Headers("Content-Type: application/json")
    @POST("point/exchangeCoin")
    Single<BaseResponce<ExchangeToCoinResponse>> exchangeToCoin(@Header("authorization") String TOKEN, @Body ExchangeToCoinRequest exchangeToCoinRequest);
}
