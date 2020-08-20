package com.upit.coex.user.screen.base.fragment;

import androidx.fragment.app.Fragment;

import com.upit.coex.user.viewmodel.base.BaseFragmentViewModel;

public class BaseFragment<T extends BaseFragmentViewModel> extends Fragment {
    protected  T mViewModal;
}
