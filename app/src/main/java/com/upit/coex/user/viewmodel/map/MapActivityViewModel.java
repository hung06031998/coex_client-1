package com.upit.coex.user.viewmodel.map;

import androidx.lifecycle.MutableLiveData;

import com.upit.coex.user.contract.map.MapContract;
import com.upit.coex.user.screen.map.activity.MapActivity;
import com.upit.coex.user.viewmodel.base.BaseActivityViewModel;

public class MapActivityViewModel extends BaseActivityViewModel<MapActivity> implements MapContract.MapViewModel {

    @Override
    public void setView(MapActivity view) {
        mLive = new MutableLiveData<>();
        mView = view;
        //bindView
        mView.bindView();
    }

    @Override
    public void requestPermission() {

    }

    @Override
    public void destroyView() {

    }

    @Override
    public MutableLiveData getMutableLiveData() {
        return null;
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
