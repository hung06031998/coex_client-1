package com.upit.coex.user.repository.model.remote.logout;

import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.logout.LogoutRequest;
import com.upit.coex.user.repository.model.data.logout.LogoutRespone;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface LogoutApiRequest {
    @Headers("Content-Type: application/json")
    @POST("logout")
    Single<BaseResponce<LogoutRespone>> doLogout(@Header("authorization") String TOKEN,@Body LogoutRequest bodyRequest);
}
