package com.upit.coex.user.viewmodel.pandr;

import androidx.lifecycle.MutableLiveData;

import com.upit.coex.user.contract.pandr.PAndRContract;
import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.point.GetPointRespone;
import com.upit.coex.user.repository.model.remote.point.GetPointApiRequest;
import com.upit.coex.user.screen.pandr.ActivityPAndR;
import com.upit.coex.user.service.fetchandsave.ApiRepository;
import com.upit.coex.user.service.sharepreference.CoexSharedPreference;
import com.upit.coex.user.viewmodel.base.BaseActivityViewModel;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.upit.coex.user.constants.CommonConstants.BASE_URL;

public class ActivityPAndRViewModel extends BaseActivityViewModel<ActivityPAndR> implements PAndRContract.PAndRContractViewModel {
    @Override
    public void setView(ActivityPAndR view) {
        this.mView = view;
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
                create(GetPointApiRequest.class).
                getGetPoint("Bearer " + CoexSharedPreference.getInstance().get("token", String.class)).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new SingleObserver<BaseResponce<GetPointRespone>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseResponce<GetPointRespone> value) {
                        if(value.getCode() == 200){
                            mView.setPoint(value.getMdata().getCurrentPoint(), true);
                        }else{
                            mView.setPoint(0, false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.setPoint(0, false);
                    }
                });
    }
}
