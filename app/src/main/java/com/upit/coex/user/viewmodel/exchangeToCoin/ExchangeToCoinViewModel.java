package com.upit.coex.user.viewmodel.exchangeToCoin;

import android.graphics.Color;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.upit.coex.user.contract.exchangeToCoin.ExchangeToCoinContract;
import com.upit.coex.user.repository.model.data.base.BaseErrorData;
import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.coin.GetCoinResponse;
import com.upit.coex.user.repository.model.data.point.ExchangeToCoinRequest;
import com.upit.coex.user.repository.model.data.point.ExchangeToCoinResponse;
import com.upit.coex.user.repository.model.remote.coin.GetCoinApiRequest;
import com.upit.coex.user.repository.model.remote.point.ExchangeToCoinApiRequest;
import com.upit.coex.user.screen.detail.activity.DetailActivity;
import com.upit.coex.user.screen.exchange.to.coin.ExchangeToCoinActivity;
import com.upit.coex.user.service.fetchandsave.ApiRepository;
import com.upit.coex.user.service.sharepreference.CoexSharedPreference;
import com.upit.coex.user.viewmodel.base.BaseActivityViewModel;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.upit.coex.user.constants.CommonConstants.BASE_URL;

public class ExchangeToCoinViewModel extends BaseActivityViewModel<ExchangeToCoinActivity> implements ExchangeToCoinContract.ExchangeToCoinViewModel {
    @Override
    public void setView(ExchangeToCoinActivity view) {
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
    public void getData() {
        ApiRepository.getInstance().setUrl(BASE_URL)
                .createRetrofit().
                create(GetCoinApiRequest.class).
                getGetCoin("Bearer " + CoexSharedPreference.getInstance().get("token", String.class)).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new SingleObserver<BaseResponce<GetCoinResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseResponce<GetCoinResponse> value) {
                        if (value.getCode() == 200) {
                            mView.setCOin(true, value.getMdata().getCurrentCoin(), "");
                        } else {
                            mView.setCOin(false, 0,value.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        BaseErrorData err = new BaseErrorData(e);
                        if (err.getMessage() != null)
                            mView.setCOin(false,0, err.getMessage());
                        else
                            mView.setCOin(false,0, e.getMessage());
                    }
                });

    }

    public void exchangeToCoin(long point) {
        ApiRepository.getInstance().setUrl(BASE_URL)
                .createRetrofit().
                create(ExchangeToCoinApiRequest.class).
                exchangeToCoin("Bearer " + CoexSharedPreference.getInstance().get("token", String.class)
                        , new ExchangeToCoinRequest(point)).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new SingleObserver<BaseResponce<ExchangeToCoinResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseResponce<ExchangeToCoinResponse> value) {
                        if (value.getCode() == 200) {
                            mView.exchangeCoiFromServer(true, value.getMdata().getCoin(),"");
                        } else {
                            mView.exchangeCoiFromServer(false, value.getMdata().getCoin(),value.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        BaseErrorData err = new BaseErrorData(e);
                        if (err.getMessage() != null)
                            mView.exchangeCoiFromServer(false,0, err.getMessage());
                        else
                            mView.exchangeCoiFromServer(false,0, e.getMessage());
                    }
                });
    }
}
