package com.upit.coex.user.repository.model.remote.profile;

import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.profile.EditProfileRequest;
import com.upit.coex.user.repository.model.data.profile.EditProfileRespone;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface EditProfileApiRequest {
    @Headers("Content-Type: application/json")
    @POST("update")
    Single<BaseResponce<EditProfileRespone>> doEditProfile(@Header("authorization") String TOKEN, @Body EditProfileRequest request);
}
