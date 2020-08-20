package com.upit.coex.user.viewmodel.profile;

import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.upit.coex.user.contract.profile.ProfileContract;
import com.upit.coex.user.repository.model.data.base.BaseErrorData;
import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.logout.LogoutRequest;
import com.upit.coex.user.repository.model.data.logout.LogoutRespone;
import com.upit.coex.user.repository.model.data.profile.ChangePassRequest;
import com.upit.coex.user.repository.model.data.profile.EditProfileRequest;
import com.upit.coex.user.repository.model.data.profile.EditProfileRespone;
import com.upit.coex.user.repository.model.remote.logout.LogoutApiRequest;
import com.upit.coex.user.repository.model.remote.profile.ChangePassApiRequest;
import com.upit.coex.user.repository.model.remote.profile.EditProfileApiRequest;
import com.upit.coex.user.screen.login.activity.LoginActivity;
import com.upit.coex.user.screen.profile.ChangeDataAccountActivity;
import com.upit.coex.user.screen.withdraw.WithdrawActivity;
import com.upit.coex.user.service.fetchandsave.ApiRepository;
import com.upit.coex.user.service.logger.L;
import com.upit.coex.user.service.sharepreference.CoexSharedPreference;
import com.upit.coex.user.viewmodel.base.BaseActivityViewModel;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.upit.coex.user.constants.login.LoginConstant.LOGIN_URL;

public class ChangeDataAccountViewModel extends BaseActivityViewModel<ChangeDataAccountActivity> implements ProfileContract.ProfileViewModel {

    @Override
    public void setView(ChangeDataAccountActivity view) {
        mView = view;
        initLiveData();
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
    public void sendDataChangeInfo(String name, String phoneNumber) {
        EditProfileRequest request = new EditProfileRequest(name, phoneNumber);

        ApiRepository.getInstance().
                setUrl(LOGIN_URL).
                createRetrofit().
                create(EditProfileApiRequest.class).
                doEditProfile("Bearer " + CoexSharedPreference.getInstance().get("token", String.class), request).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new SingleObserver<BaseResponce<EditProfileRespone>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseResponce<EditProfileRespone> value) {
                        if (value.getCode() == 200) {
                            mView.checkDOne(true, name, phoneNumber, "");
                        } else {
                            mView.checkDOne(false, name, phoneNumber, value.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        BaseErrorData err = new BaseErrorData(e);
                        if (err.getMessage() != null) {
                            mView.checkDOne(false, name, phoneNumber, err.getMessage());
                        } else {
                            mView.checkDOne(false, name, phoneNumber, e.getMessage());
                        }
                    }
                });
    }

    @Override
    public void sendDataChangePass(String newPass, String oldPass) {
        ChangePassRequest request = new ChangePassRequest(CoexSharedPreference.getInstance().get("fcmToken", String.class), newPass, oldPass);
        ApiRepository.getInstance().
                setUrl(LOGIN_URL).
                createRetrofit().
                create(ChangePassApiRequest.class).
                doChangePass("Bearer " + CoexSharedPreference.getInstance().get("token", String.class), request).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new SingleObserver<BaseResponce<EditProfileRespone>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseResponce<EditProfileRespone> value) {
                        if (value.getCode() == 200) {
                            mView.checkDoneChangePass(true, "");
                        } else {
                            mView.checkDoneChangePass(false, value.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        BaseErrorData err = new BaseErrorData(e);
                        if (err.getMessage() != null) {
                            mView.checkDoneChangePass(false, err.getMessage());
                        } else {
                            mView.checkDoneChangePass(false, e.getMessage());
                        }
                    }
                });
    }
}
