package com.upit.coex.user.viewmodel.splash;

import androidx.lifecycle.MutableLiveData;

import com.upit.coex.user.R;
import com.upit.coex.user.contract.splash.SplashContract;
import com.upit.coex.user.repository.model.data.splash.SplashResponce;
import com.upit.coex.user.screen.splash.activity.SplashActivity;
import com.upit.coex.user.viewmodel.base.BaseActivityViewModel;

/**
 * Created by chien.lx on 3/9/2020.
 */

public class SplashActivityViewModel extends BaseActivityViewModel<SplashActivity> implements SplashContract.SplashInterfaceViewModel{
    public SplashActivityViewModel(){

    }

    @Override
    public void setView(SplashActivity view) {
        mView = view;
        mView.bindView();
        mLive = new MutableLiveData();
    }

    @Override
    public void requestPermission() {

    }


    @Override
    public void destroyView() {

    }

    @Override
    public MutableLiveData<SplashResponce> getMutableLiveData() {
        return mLive;
    }

    @Override
    public MutableLiveData getSuccessMutableLiveData() {
        return null;
    }

    @Override
    public MutableLiveData getFailedMutableLiveData() {
        return null;
    }
}
