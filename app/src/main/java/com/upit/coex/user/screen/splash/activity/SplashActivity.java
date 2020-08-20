package com.upit.coex.user.screen.splash.activity;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.upit.coex.user.R;
import com.upit.coex.user.contract.splash.SplashContract;
import com.upit.coex.user.screen.base.activity.BaseActivity;
import com.upit.coex.user.service.logger.L;
import com.upit.coex.user.service.sharepreference.CoexSharedPreference;
import com.upit.coex.user.viewmodel.splash.SplashActivityViewModel;

import static com.upit.coex.user.constants.splash.SplashConstant.TAG_ACIVITY;
import static com.upit.coex.user.constants.splash.SplashConstant.TAG;

public class SplashActivity extends BaseActivity<SplashActivityViewModel> implements
        SplashContract.SplashInterfaceView
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mViewModal = ViewModelProviders.of(this).get(SplashActivityViewModel.class);
        mViewModal.setView(this);
    }

    @Override
    public void observeLifeCycle() {

    }

    @Override
    public void bindView() {
        L.d(TAG,TAG_ACIVITY,"bindView");

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( SplashActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                CoexSharedPreference.getInstance().put("fcmToken",newToken);
                L.d(TAG,TAG_ACIVITY,newToken);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
