package com.upit.coex.user.repository.model.remote.booking;

import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.booking.BookingRequest;
import com.upit.coex.user.repository.model.data.booking.BookingResponce;
import com.upit.coex.user.repository.model.data.booking.CheckResultResponse;
import com.upit.coex.user.repository.model.data.booking.CreatePaymentResponse;
import com.upit.coex.user.repository.model.data.booking.PaymentRequest;
import com.upit.coex.user.repository.model.data.booking.VnpayCreatePayment;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface BookingApiRequest {
    @Headers("Content-Type: application/json")
    @POST("bookings")
    Single<BaseResponce<BookingResponce>> doBooking(@Header ("authorization") String TOKEN, @Body BookingRequest bodyRequest);

    @Headers("Content-Type: application/json")
    @POST("create_payment_url")
    Single<BaseResponce<CreatePaymentResponse>> paymentBooking(@Header ("authorization") String TOKEN, @Body PaymentRequest paymentRequest);


    @Headers("Content-Type: application/json")
    @POST("checkResult")
    Single<BaseResponce<CheckResultResponse>> checkResult(@Header ("authorization") String TOKEN, @Body PaymentRequest paymentRequest);



    @Headers("Content-Type: application/json")
    @POST("create_payment_url")
    Single<BaseResponce<VnpayCreatePayment>> vnpayCreatePayment(@Header ("authorization") String TOKEN, @Body PaymentRequest paymentRequest);
}
