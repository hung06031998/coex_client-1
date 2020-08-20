package com.upit.coex.user.repository.model.remote.login;

import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.login.LoginRequest;
import com.upit.coex.user.repository.model.data.login.LoginResponce;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LoginLoginApi {
    @Headers("Content-Type: application/json")
    @POST("login")
    Single<BaseResponce<LoginResponce>> doLogin(@Body LoginRequest login);
}
