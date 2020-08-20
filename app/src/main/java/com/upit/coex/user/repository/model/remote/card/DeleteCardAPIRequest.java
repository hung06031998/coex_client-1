package com.upit.coex.user.repository.model.remote.card;

import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.logout.LogoutRespone;

import io.reactivex.Single;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface DeleteCardAPIRequest {
    @Headers("Content-Type: application/json")
    @DELETE(".")
    Single<BaseResponce<LogoutRespone>> deleteCard(@Header("authorization") String TOKEN);
}
