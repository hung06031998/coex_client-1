
package com.upit.coex.user.repository.model.data.base;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.upit.coex.user.CoexApplication;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

@SuppressWarnings("unused")
public class BaseErrorData {

    @SerializedName("code")
    private Long mCode;
    @SerializedName("message")
    private String mMessage;

    public BaseErrorData(Throwable e){
        try {
            BaseErrorData m = CoexApplication.self().getGSon().fromJson(((HttpException) e).response().errorBody().string(), BaseErrorData.class);
            this.mCode = m.getCode();
            this.mMessage = m.getMessage();
        }catch(Exception ie){
            Log.d("aaa",  ie.getMessage());
        }

    }

    public BaseErrorData(String e){
            BaseErrorData m = CoexApplication.self().getGSon().fromJson(e, BaseErrorData.class);
            this.mCode = m.getCode();
            this.mMessage = m.getMessage();
    }

    public Long getCode() {
        return mCode;
    }

    public void setCode(Long code) {
        mCode = code;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

}
