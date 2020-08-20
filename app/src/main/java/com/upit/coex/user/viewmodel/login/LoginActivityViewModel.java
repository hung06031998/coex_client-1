package com.upit.coex.user.viewmodel.login;

import androidx.lifecycle.MutableLiveData;

import com.upit.coex.user.contract.login.LoginContract;
import com.upit.coex.user.repository.model.data.base.BaseErrorData;
import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.login.LoginRequest;
import com.upit.coex.user.repository.model.data.login.LoginResponce;
import com.upit.coex.user.repository.model.remote.login.LoginLoginApi;
import com.upit.coex.user.screen.login.activity.LoginActivity;
import com.upit.coex.user.service.fetchandsave.ApiRepository;
import com.upit.coex.user.service.logger.L;
import com.upit.coex.user.service.sharepreference.CoexSharedPreference;
import com.upit.coex.user.viewmodel.base.BaseActivityViewModel;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.upit.coex.user.constants.login.LoginConstant.LOGIN_URL;

public class LoginActivityViewModel extends BaseActivityViewModel<LoginActivity> implements LoginContract.LoginInterfaceViewModel {


    @Override
    public void destroyView() {

    }

    public LoginActivityViewModel() {
        initLiveData();
    }

    @Override
    public void setView(LoginActivity view) {
        mView = view;
    }

    @Override
    public void requestPermission() {

    }

    @Override
    public MutableLiveData getMutableLiveData() {
        return mLive;
    }

    @Override
    public MutableLiveData getSuccessMutableLiveData() {
        return mLiveSuccess;
    }

    @Override
    public MutableLiveData getFailedMutableLiveData() {
        return mLiveFailed;
    }

    @Override
    public void doLogin(String email, String passWord, String mFirebaseToken) {
        L.d("ahiuhiu", LOGIN_URL);
        ApiRepository.getInstance().
                setUrl(LOGIN_URL).
                createRetrofit().
                create(LoginLoginApi.class).
                doLogin(new LoginRequest(email, passWord, mFirebaseToken)).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new SingleObserver<BaseResponce<LoginResponce>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseResponce<LoginResponce> value) {
                        mLive.setValue(value);
                        if (value.getCode() == 200) {
                            L.d("ahiuhiu", "in success Login ok");
                            mLiveSuccess.setValue("Login success");
                            CoexSharedPreference.getInstance().put("token", value.getMdata().getToken());
                        } else {
                            mLiveFailed.setValue("Login failed");
                            L.d("ahiuhiu", "in success Login failed");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        BaseErrorData err = new BaseErrorData(e);
                        if (err.getMessage() != null)
                            mLiveFailed.setValue(err.getMessage());
                        else
                            mLiveFailed.setValue(e.getMessage());

                    }
                });
    }
}
