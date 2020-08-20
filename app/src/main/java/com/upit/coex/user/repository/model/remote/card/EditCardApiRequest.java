package com.upit.coex.user.repository.model.remote.card;

import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.card.Card;
import com.upit.coex.user.repository.model.data.card.EditCardRespone;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;

public interface EditCardApiRequest {
    @Headers("Content-Type: application/json")
    @PATCH(".")
    Single<BaseResponce<Card>> editCard(@Header("authorization") String TOKEN, @Body EditCardRespone cardName);
}
