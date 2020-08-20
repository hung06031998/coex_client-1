package com.upit.coex.user.repository.model.remote.register;

import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.register.RegisterRequest;
import com.upit.coex.user.repository.model.data.register.RegisterRespone;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RegisterApiRequest {
    @Headers("Content-Type: application/json")
    @POST("register")
    Single<BaseResponce<RegisterRespone>> doRegistster(@Body RegisterRequest bodyRequest);
}
