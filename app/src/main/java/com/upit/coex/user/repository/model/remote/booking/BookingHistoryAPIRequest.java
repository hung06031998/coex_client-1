package com.upit.coex.user.repository.model.remote.booking;

import com.upit.coex.user.repository.model.data.base.BaseResponceList;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface BookingHistoryAPIRequest {
    @Headers("Content-Type: application/json")
    @GET("history")
    Single<BaseResponceList<Long>> getHistoryBooking(@Header("authorization") String TOKEN);
}
