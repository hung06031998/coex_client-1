package com.upit.coex.user.repository.model.remote.room;

import com.upit.coex.user.repository.model.data.map.CoWokingResponce;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface SearchRoomApi {
    @Headers("Content-Type: application/json")
    @GET("search")
    Single<CoWokingResponce> doSearchRoom(@Header("authorization") String TOKEN, @Query("key") String name, @Query("lat") double lat, @Query("lng") double lng);
}
