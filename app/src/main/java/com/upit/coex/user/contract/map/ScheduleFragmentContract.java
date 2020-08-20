package com.upit.coex.user.contract.map;

import com.upit.coex.user.contract.base.BaseInterfaceView;
import com.upit.coex.user.contract.base.fragment.BaseInterfaceFragmentViewModel;
import com.upit.coex.user.repository.model.data.booking.BookingInfoRespone;
import com.upit.coex.user.screen.map.fragment.ScheduleFragment;

import java.util.List;

public class ScheduleFragmentContract {
    public interface ScheduleInterfaceFragmentViewModel extends BaseInterfaceFragmentViewModel<ScheduleFragment> {
        void getData(long data, int day, int month);

        void getAllBookingHistory();
    }

    public interface ScheduleInterfaceFragmentView extends BaseInterfaceView {
        void showAllDayBooking(List<Long> data);

        void setResultFalse();

        void showInfoBooking(List<BookingInfoRespone> mDatarespone, int day, int month, boolean result);

        void showResultCancelBooking(String message, boolean result);
    }
}
