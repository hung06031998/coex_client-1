package com.upit.coex.user.viewmodel.booking;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.upit.coex.user.contract.booking.BookingConrtact;
import com.upit.coex.user.repository.model.data.base.BaseErrorData;
import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.booking.BookingGetPriceRespone;
import com.upit.coex.user.repository.model.data.booking.BookingRequest;
import com.upit.coex.user.repository.model.data.booking.BookingResponce;
import com.upit.coex.user.repository.model.data.booking.ListDate;
import com.upit.coex.user.repository.model.remote.booking.BookingApiRequest;
import com.upit.coex.user.repository.model.remote.booking.BookingEditApiRequest;
import com.upit.coex.user.repository.model.remote.booking.BookingGetPriceApiRequest;
import com.upit.coex.user.screen.booking.BookingActivity;
import com.upit.coex.user.service.fetchandsave.ApiRepository;
import com.upit.coex.user.service.sharepreference.CoexSharedPreference;
import com.upit.coex.user.viewmodel.base.BaseActivityViewModel;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.upit.coex.user.constants.CommonConstants.BASE_URL;
import static com.upit.coex.user.constants.register.RegisterConstant.BOOKING_URL;

public class BookingActivityViewModel extends BaseActivityViewModel<BookingActivity> implements BookingConrtact.BookingViewModel {

    @Override
    public void setView(BookingActivity view) {
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
    public void booking(List<ListDate> listDate, int numberGuest, String id) {
        ApiRepository.getInstance().
                setUrl(BASE_URL).
                createRetrofit().
                create(BookingApiRequest.class).
                doBooking("Bearer " + CoexSharedPreference.getInstance().get("token", String.class),
                        new BookingRequest(numberGuest, listDate, id)).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<BaseResponce<BookingResponce>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseResponce<BookingResponce> value) {
                        if (value.getCode() == 200) {
                            mView.booingRespone(1, "");
                        } else {
                            mView.booingRespone(0, "Fail : " + value.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        BaseErrorData err = new BaseErrorData(e);
                        if(err.getMessage()!=null)
                            mView.editBooking(false, "Fail : " + err.getMessage());
                        else
                            mView.editBooking(false, "Fail : " + e.getMessage());
                    }
                });
    }

    @Override
    public void editBooking(List<ListDate> listDate, int numberGuest, String id, String idBooking) {
        ApiRepository.getInstance().
                setUrl(BOOKING_URL + idBooking + "/").
                createRetrofit().
                create(BookingEditApiRequest.class).
                doEditBooking("Bearer " + CoexSharedPreference.getInstance().get("token", String.class), new BookingRequest(numberGuest, listDate, id)).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<BaseResponce<BookingResponce>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseResponce<BookingResponce> value) {
                        if (value.getCode() == 200) {
                            mView.editBooking(true, "");
                        } else {
                            mView.editBooking(false, "Fail : " + value.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        BaseErrorData err = new BaseErrorData(e);
                        if(err.getMessage()!=null)
                            mView.editBooking(false, "Fail : " + err.getMessage());
                        else
                            mView.editBooking(false, "Fail : " + e.getMessage());
                    }
                });
    }

    @Override
    public void getPrice(List<ListDate> listDate, int numberGuest, String id) {
        ApiRepository.getInstance().
                setUrl(BOOKING_URL).
                createRetrofit().
                create(BookingGetPriceApiRequest.class).
                doGetPrice("Bearer " + CoexSharedPreference.getInstance().get("token", String.class),
                        new BookingRequest(numberGuest, listDate, id)).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<BaseResponce<BookingGetPriceRespone>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseResponce<BookingGetPriceRespone> value) {
                        if (value.getCode() == 200) {
                            mView.showPrice(value.getMdata().getPrice(), true,"");
                        } else {
                            mView.showPrice(123456, false,value.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        BaseErrorData err = new BaseErrorData(e);
                        if(err.getMessage() != null)
                            mView.showPrice(123456, false,err.getMessage());
                        else
                            mView.showPrice(123456, false,e.getMessage());
                    }
                });
    }
}
