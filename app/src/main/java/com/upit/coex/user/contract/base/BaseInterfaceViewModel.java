package com.upit.coex.user.contract.base;

import androidx.lifecycle.MutableLiveData;

import com.upit.coex.user.screen.base.activity.BaseActivity;

/**
 * Created by chien.lx on 3/9/2020.
 */

public interface BaseInterfaceViewModel<T extends BaseActivity> {
    void setView(T view);

    void requestPermission();

    void destroyView();

    MutableLiveData getMutableLiveData();

    MutableLiveData getSuccessMutableLiveData();

    MutableLiveData getFailedMutableLiveData();
}
