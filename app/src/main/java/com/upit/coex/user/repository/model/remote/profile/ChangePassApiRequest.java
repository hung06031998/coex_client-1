package com.upit.coex.user.repository.model.remote.profile;

import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.profile.ChangePassRequest;
import com.upit.coex.user.repository.model.data.profile.EditProfileRespone;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ChangePassApiRequest {
    @Headers("Content-Type: application/json")
    @POST("changepassword")
    Single<BaseResponce<EditProfileRespone>> doChangePass(@Header("authorization") String TOKEN, @Body ChangePassRequest request);
}
