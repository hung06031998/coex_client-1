package com.upit.coex.user.repository.model.remote.booking;

import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.booking.BookingGetPriceRespone;
import com.upit.coex.user.repository.model.data.booking.BookingRequest;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface BookingGetPriceApiRequest {
    @Headers("Content-Type: application/json")
    @POST("price")
    Single<BaseResponce<BookingGetPriceRespone>> doGetPrice(@Header("authorization") String TOKEN, @Body BookingRequest bodyRequest);
}
