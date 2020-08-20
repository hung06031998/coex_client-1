package com.upit.coex.user.repository.model.remote.room;

import com.upit.coex.user.repository.model.data.map.CoWokingResponce;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GetRoomApi {
    @Headers("Content-Type: application/json")
    @GET("near")
    Single<CoWokingResponce> doQuery(@Header("authorization") String TOKEN, @Query("maxDistance") double maxDistance, @Query("lat") double lat, @Query("lng") double lng, @Query("latCamera") double latCamera, @Query("lngCamera") double lngCamera);
}
