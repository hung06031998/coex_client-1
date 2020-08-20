package com.upit.coex.user.repository.model.data.base;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseResponceList<T extends Object> extends TemplateResponce{

    @SerializedName("data")
    private List<T> mdata;

    public List<T> getMdata() {
        return mdata;
    }

    public void setMdata(List<T> mdata) {
        this.mdata = mdata;
    }
}
