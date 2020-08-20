package com.upit.coex.user.repository.model.remote.booking;

import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.booking.CancelBookingRespone;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface CancelBookingApiRequest {
    @Headers("Content-Type: application/json")
    @GET(".")
    Single<BaseResponce<CancelBookingRespone>> doCancelBooking(@Header("authorization") String TOKEN);
}
