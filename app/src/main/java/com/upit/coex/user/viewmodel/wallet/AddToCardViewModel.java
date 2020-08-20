package com.upit.coex.user.viewmodel.wallet;

import androidx.lifecycle.MutableLiveData;

import com.upit.coex.user.contract.base.BaseInterfaceViewModel;
import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.base.BaseResponceList;
import com.upit.coex.user.repository.model.data.card.Card;
import com.upit.coex.user.repository.model.data.card.EditCardRespone;
import com.upit.coex.user.repository.model.remote.card.AddCardApiRequest;
import com.upit.coex.user.repository.model.remote.card.EditCardApiRequest;
import com.upit.coex.user.screen.wallet.activity.AddToCardActivity;
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

public class AddToCardViewModel extends BaseActivityViewModel<AddToCardActivity> implements BaseInterfaceViewModel<AddToCardActivity> {
    @Override
    public void setView(AddToCardActivity view) {
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

    public void addCard(String name, String address) {
        ApiRepository.getInstance().setUrl(BASE_URL)
                .createRetrofit().
                create(AddCardApiRequest.class).
                addCard("Bearer " + CoexSharedPreference.getInstance().get("token", String.class), new Card(address, name)).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new SingleObserver<BaseResponceList<Card>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseResponceList<Card> value) {
                        if (value.getCode() == 200) {
                            mView.onSuccess();
                        } else {
                            mView.onFail();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onFail();
                    }
                });
    }

    public void editCard(String oldName, String newName) {
        EditCardRespone cardEdit = new EditCardRespone();
        cardEdit.setName(newName);
        ApiRepository.getInstance().
                setUrl(CARD_URL + oldName + "/").
                createRetrofit().
                create(EditCardApiRequest.class).
                editCard("Bearer " + CoexSharedPreference.getInstance().get("token", String.class), cardEdit).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new SingleObserver<BaseResponce<Card>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseResponce<Card> value) {
                        if (value.getCode() == 200) {
                            mView.EditCardSuccess();
                        } else {
                            mView.editCardFail();
                            L.d("ahiuhiu", value.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.editCardFail();
                        L.d("ahiuhiu", e.getMessage());
                    }
                });
    }
}
