package com.upit.coex.user.contract.booking;

import com.upit.coex.user.contract.base.BaseInterfaceView;
import com.upit.coex.user.contract.base.BaseInterfaceViewModel;
import com.upit.coex.user.repository.model.data.booking.ListDate;
import com.upit.coex.user.screen.booking.BookingActivity;

import java.util.List;

public class BookingConrtact {
    public interface BookingViewModel extends BaseInterfaceViewModel<BookingActivity> {

        void booking(List<ListDate> listDate, int numberGuest, String id);

        void editBooking(List<ListDate> listDate, int numberGuest, String id, String idBooking);

        void getPrice(List<ListDate> listDate, int numberGuest, String id);
    }

    public interface BookingView extends BaseInterfaceView {

        void booingRespone(int number, String respone);

        void editBooking(boolean result, String message);

        void showPrice(long cost, boolean result,String message);
    }
}
