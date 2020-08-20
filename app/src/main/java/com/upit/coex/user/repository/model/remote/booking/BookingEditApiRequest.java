package com.upit.coex.user.repository.model.remote.booking;

import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.booking.BookingRequest;
import com.upit.coex.user.repository.model.data.booking.BookingResponce;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;

public interface BookingEditApiRequest {
    @Headers("Content-Type: application/json")
    @PATCH(".")
    Single<BaseResponce<BookingResponce>> doEditBooking(@Header("authorization") String TOKEN, @Body BookingRequest bodyRequest);
}
