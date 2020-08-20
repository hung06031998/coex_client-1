package com.upit.coex.user.repository.model.data.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upit.coex.user.repository.model.data.base.BaseData;

/**
 * Created by chien.lx on 3/9/2020.
 */

public class LoginResponce extends BaseData {

    @SerializedName("token")
    @Expose
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
