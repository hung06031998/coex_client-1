package com.upit.coex.user.service.fetchandsave.remote;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chien.lx on 3/9/2020.
 */

public class RetrofitService {

    public static Retrofit createService(String url){
//        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .readTimeout(10, TimeUnit.SECONDS)
//                .writeTimeout(10, TimeUnit.SECONDS)
//                .build();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(35, TimeUnit.MINUTES) // connect timeout
                .writeTimeout(35, TimeUnit.MINUTES) // write timeout
                .readTimeout(35, TimeUnit.MINUTES); // read timeout

        OkHttpClient okHttpClient = builder.build();

        return new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
}