package com.upit.coex.user.viewmodel.addinfomation;

import androidx.lifecycle.MutableLiveData;

import com.upit.coex.user.contract.register.RegisterContract;
import com.upit.coex.user.repository.model.data.base.BaseErrorData;
import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.register.Client;
import com.upit.coex.user.repository.model.data.register.RegisterRequest;
import com.upit.coex.user.repository.model.data.register.RegisterRespone;
import com.upit.coex.user.repository.model.remote.register.RegisterApiRequest;
import com.upit.coex.user.screen.addinfomation.AddInfomationActivity;
import com.upit.coex.user.service.fetchandsave.ApiRepository;
import com.upit.coex.user.service.logger.L;
import com.upit.coex.user.viewmodel.base.BaseActivityViewModel;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.upit.coex.user.constants.register.RegisterConstant.REGISTER_URL;

public class AddInfomationViewModel extends BaseActivityViewModel<AddInfomationActivity> implements RegisterContract.RegisterInterfaceViewModel {

    public AddInfomationViewModel() {
        initLiveData();
    }

    @Override
    public void initLiveData() {
        super.initLiveData();
    }


    @Override
    public void setView(AddInfomationActivity view) {
        mView = view;
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
    public void doRegister(String email, String password, String name, String phone, boolean typeUser) {
        L.d("ahiuhiu", REGISTER_URL);
        Client client = new Client();
        client.setName(name);
        client.setPhone(phone);
        ApiRepository.getInstance().
                setUrl(REGISTER_URL).
                createRetrofit().
                create(RegisterApiRequest.class).
                doRegistster(
                        new RegisterRequest(email, password, false, client)).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<BaseResponce<RegisterRespone>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseResponce<RegisterRespone> value) {
                        if (value.getCode() == 200) {
                            mView.registerSuccess();
                        } else {
                            mView.registerFailed(value.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        BaseErrorData err = new BaseErrorData(e);
                        if (err.getMessage() != null)
                            mView.registerFailed(err.getMessage());
                        else
                            mView.registerFailed(e.getMessage());
                    }
                });
    }
}
