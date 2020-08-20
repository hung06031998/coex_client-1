package com.upit.coex.user.repository.model.remote.profile;

import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.profile.ProfileRespone;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface ProfileApiRequest {
    @Headers("Content-Type: application/json")
    @GET("me")
    Single<BaseResponce<ProfileRespone>> getProfile(@Header("authorization") String TOKEN);
}
