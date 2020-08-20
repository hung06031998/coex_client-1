package com.upit.coex.user.repository.model.remote.point;

import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.point.GetPointRespone;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface GetPointApiRequest {
    @Headers("Content-Type: application/json")
    @GET("point")
    Single<BaseResponce<GetPointRespone>> getGetPoint(@Header("authorization") String TOKEN);
}
