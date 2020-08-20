package com.upit.coex.user.screen.base.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.upit.coex.user.viewmodel.base.BaseActivityViewModel;

/**
 * Created by chien.lx on 3/9/2020.
 */

public abstract class BaseActivity<T extends BaseActivityViewModel> extends AppCompatActivity {
    protected T mViewModal;

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
