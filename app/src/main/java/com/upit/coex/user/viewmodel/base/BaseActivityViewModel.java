package com.upit.coex.user.viewmodel.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.upit.coex.user.screen.base.activity.BaseActivity;

/**
 * Created by chien.lx on 3/9/2020.
 */

public abstract class BaseActivityViewModel<T extends BaseActivity> extends ViewModel {
    protected MutableLiveData mLive;
    protected MutableLiveData mLiveSuccess;
    protected MutableLiveData mLiveFailed;
    protected T mView;

    public void initLiveData() {
        mLive = new MutableLiveData();
        mLiveSuccess = new MutableLiveData();
        mLiveFailed = new MutableLiveData();
    }

}