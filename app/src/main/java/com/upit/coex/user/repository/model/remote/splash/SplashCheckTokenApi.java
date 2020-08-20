package com.upit.coex.user.repository.model.remote.splash;

import com.upit.coex.user.repository.model.data.splash.SplashResponce;

import io.reactivex.Single;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface SplashCheckTokenApi {
    @Headers("access_token:FreYzHzaHmg2uqQ13RV4ivB388dVs8pQl4UClcsJaR9ZIO7pbaqvGBkQAvsufaFz")
    @POST("checkToken")
    Single<SplashResponce> checkToken();
}
