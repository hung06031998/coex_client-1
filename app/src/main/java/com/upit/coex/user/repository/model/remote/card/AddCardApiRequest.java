package com.upit.coex.user.repository.model.remote.card;

import com.upit.coex.user.repository.model.data.base.BaseResponceList;
import com.upit.coex.user.repository.model.data.card.Card;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AddCardApiRequest {
    @Headers("Content-Type: application/json")
    @POST("cards/add")
    Single<BaseResponceList<Card>> addCard(@Header("authorization") String TOKEN, @Body Card card);
}
