package com.upit.coex.user.viewmodel.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.upit.coex.user.screen.base.fragment.BaseFragment;

public abstract class BaseFragmentViewModel<T extends BaseFragment> extends ViewModel {
    protected MutableLiveData mLive;
    protected T mView;
}
