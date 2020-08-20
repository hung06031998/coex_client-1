package com.upit.coex.user.viewmodel.wallet;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.upit.coex.user.contract.wallet.WalletContract;
import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.base.BaseResponceList;
import com.upit.coex.user.repository.model.data.card.Card;
import com.upit.coex.user.repository.model.data.coin.GetCoinResponse;
import com.upit.coex.user.repository.model.data.logout.LogoutRespone;
import com.upit.coex.user.repository.model.remote.card.DeleteCardAPIRequest;
import com.upit.coex.user.repository.model.remote.card.GetListCardApiRequest;
import com.upit.coex.user.repository.model.remote.coin.GetCoinApiRequest;
import com.upit.coex.user.screen.wallet.activity.WalletActivity;
import com.upit.coex.user.service.fetchandsave.ApiRepository;
import com.upit.coex.user.service.logger.L;
import com.upit.coex.user.service.sharepreference.CoexSharedPreference;
import com.upit.coex.user.viewmodel.base.BaseActivityViewModel;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.upit.coex.user.constants.CommonConstants.BASE_URL;
import static com.upit.coex.user.constants.login.LoginConstant.CARD_URL;

public class ActivityWalletViewModel extends BaseActivityViewModel<WalletActivity> implements WalletContract.WalletViewModel {
    @Override
    public void setView(WalletActivity view) {
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
                            mView.setCoin(value.getMdata().getCurrentCoin());
                        } else {
                            mView.setFalse();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("ahiuhiu", e.getMessage());
                        mView.setFalse();
                    }
                });
    }

    @Override
    public void deleteCard(String nane) {
        ApiRepository.getInstance().
                setUrl(CARD_URL + nane + "/").
                createRetrofit().
                create(DeleteCardAPIRequest.class).
                deleteCard("Bearer " + CoexSharedPreference.getInstance().get("token", String.class)).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new SingleObserver<BaseResponce<LogoutRespone>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseResponce<LogoutRespone> value) {
                        if (value.getCode() == 200) {
                            mView.resultDelete(true);
                        } else {
                            mView.resultDelete(false);
                            L.d("ahiuhiu", value.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.resultDelete(false);
                        L.d("ahiuhiu", e.getMessage());
                    }
                });
    }

    public void getListCard() {
        ApiRepository.getInstance().setUrl(BASE_URL)
                .createRetrofit().
                create(GetListCardApiRequest.class).
                getListCard("Bearer " + CoexSharedPreference.getInstance().get("token", String.class)).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new SingleObserver<BaseResponceList<Card>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseResponceList<Card> value) {
                        if (value.getCode() == 200) {
                            mView.bindListCard(value.getMdata());
                        } else {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("ahiuhiu", e.getMessage());
                    }
                });
    }
}
