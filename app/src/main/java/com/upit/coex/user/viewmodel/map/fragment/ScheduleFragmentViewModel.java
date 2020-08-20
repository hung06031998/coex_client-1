package com.upit.coex.user.viewmodel.map.fragment;

import androidx.lifecycle.MutableLiveData;

import com.upit.coex.user.contract.map.ScheduleFragmentContract;
import com.upit.coex.user.repository.model.data.base.BaseErrorData;
import com.upit.coex.user.repository.model.data.base.BaseResponceList;
import com.upit.coex.user.repository.model.data.booking.BookingInfoRespone;
import com.upit.coex.user.repository.model.remote.booking.BookingApiInfoRequest;
import com.upit.coex.user.repository.model.remote.booking.BookingHistoryAPIRequest;
import com.upit.coex.user.screen.map.fragment.ScheduleFragment;
import com.upit.coex.user.service.fetchandsave.ApiRepository;
import com.upit.coex.user.service.logger.L;
import com.upit.coex.user.service.sharepreference.CoexSharedPreference;
import com.upit.coex.user.viewmodel.base.BaseFragmentViewModel;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.upit.coex.user.constants.register.RegisterConstant.BOOKING_URL;

public class ScheduleFragmentViewModel extends BaseFragmentViewModel<ScheduleFragment> implements ScheduleFragmentContract.ScheduleInterfaceFragmentViewModel {
    @Override
    public void setView(ScheduleFragment view) {
        mView = view;
        mView.bindView();
    }

    @Override
    public MutableLiveData getMutableLiveData() {
        return mLive;
    }

    @Override
    public void getData(long data, int day, int month) {
        getBookingInfo(data, day, month);
    }

    private void getBookingInfo(long date, int day, int month) {
        ApiRepository.getInstance().
                setUrl(BOOKING_URL + date + "/").
                createRetrofit().
                create(BookingApiInfoRequest.class).
                doGetInfo("Bearer " + CoexSharedPreference.getInstance().get("token", String.class)).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new SingleObserver<BaseResponceList<BookingInfoRespone>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseResponceList<BookingInfoRespone> value) {
                        if (value.getCode() == 200) {
                            if (value.getMdata() != null && value.getMdata().size() != 0) {
                                mView.showInfoBooking(value.getMdata(), day, month, true);
                            } else {
                                mView.showInfoBooking(value.getMdata(), day, month, false);
                            }
                        } else {
                            mView.showInfoBooking(value.getMdata(), day, month, false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        BaseErrorData err = new BaseErrorData(e);
                        if (err.getMessage() != null)
                            mView.showInfoBooking(null, day, month, false);
                        else
                            mView.showInfoBooking(null, day, month, false);
                    }
                });
    }

    @Override
    public void getAllBookingHistory() {
        ApiRepository.getInstance().
                setUrl(BOOKING_URL).
                createRetrofit().
                create(BookingHistoryAPIRequest.class).
                getHistoryBooking("Bearer " + CoexSharedPreference.getInstance().get("token", String.class)).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<BaseResponceList<Long>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseResponceList<Long> value) {
                        if (value.getCode() == 200) {
                            List<Long> data = value.getMdata();
                            mView.showAllDayBooking(data);
                        } else {
                            mView.setResultFalse();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.setResultFalse();
                    }
                });
    }
}
