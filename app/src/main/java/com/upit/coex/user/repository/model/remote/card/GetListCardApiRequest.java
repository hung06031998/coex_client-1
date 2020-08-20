package com.upit.coex.user.repository.model.remote.card;

import com.upit.coex.user.repository.model.data.base.BaseResponceList;
import com.upit.coex.user.repository.model.data.card.Card;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface GetListCardApiRequest {
    @Headers("Content-Type: application/json")
    @GET("cards")
    Single<BaseResponceList<Card>> getListCard(@Header("authorization") String TOKEN);
}
