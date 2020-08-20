package com.upit.coex.user.repository.model.remote.booking;

import com.upit.coex.user.repository.model.data.base.BaseResponceList;
import com.upit.coex.user.repository.model.data.booking.BookingInfoRespone;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface BookingApiInfoRequest {
    @Headers("Content-Type: application/json")
    @GET(".")
    Single<BaseResponceList<BookingInfoRespone>> doGetInfo(@Header("authorization") String TOKEN);
}
