package com.upit.coex.user.repository.model.data.base;

import com.google.gson.annotations.SerializedName;


public class BaseResponce<T extends BaseData> extends TemplateResponce{

    @SerializedName("data")
    private T mdata;

    public T getMdata() {
        return mdata;
    }

    public void setMdata(T mdata) {
        this.mdata = mdata;
    }

}
