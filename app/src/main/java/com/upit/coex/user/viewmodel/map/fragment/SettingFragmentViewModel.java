package com.upit.coex.user.viewmodel.map.fragment;

import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.upit.coex.user.contract.map.SettingFragmentContract;
import com.upit.coex.user.repository.model.data.base.BaseErrorData;
import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.logout.LogoutRequest;
import com.upit.coex.user.repository.model.data.logout.LogoutRespone;
import com.upit.coex.user.repository.model.remote.logout.LogoutApiRequest;
import com.upit.coex.user.screen.login.activity.LoginActivity;
import com.upit.coex.user.screen.map.fragment.SettingFragment;
import com.upit.coex.user.service.fetchandsave.ApiRepository;
import com.upit.coex.user.service.logger.L;
import com.upit.coex.user.service.sharepreference.CoexSharedPreference;
import com.upit.coex.user.viewmodel.base.BaseFragmentViewModel;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.upit.coex.user.constants.login.LoginConstant.LOGIN_URL;

public class SettingFragmentViewModel extends BaseFragmentViewModel<SettingFragment> implements SettingFragmentContract.SettingInterfaceFragmentViewModel {

    @Override
    public void setView(SettingFragment view) {
        mView = view;
        mView.bindView();
    }

    @Override
    public MutableLiveData getMutableLiveData() {
        return mLive;
    }

    @Override
    public void logout(String fcmToken) {
        ApiRepository.getInstance().
                setUrl(LOGIN_URL).createRetrofit().
                create(LogoutApiRequest.class).
                doLogout("Bearer " + CoexSharedPreference.getInstance().get("token", String.class),new LogoutRequest(fcmToken)).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new SingleObserver<BaseResponce<LogoutRespone>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseResponce<LogoutRespone> value) {
                        if(value.getCode() == 200){
                            mView.getResult(true,value.getMessage());
                        }else{
                            mView.getResult(false,value.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        BaseErrorData err = new BaseErrorData(e);
                        if (err.getMessage() != null)
                            mView.getResult(false,err.getMessage());
                        else
                            mView.getResult(false,e.getMessage());
                    }
                });
    }
}
