package com.upit.coex.user.contract.base.fragment;

import androidx.lifecycle.MutableLiveData;

import com.upit.coex.user.screen.base.fragment.BaseFragment;

public interface BaseInterfaceFragmentViewModel<T extends BaseFragment> {
    void setView(T view);

    MutableLiveData getMutableLiveData();
}
