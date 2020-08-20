package com.upit.coex.user.screen.map.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.upit.coex.user.R;
import com.upit.coex.user.contract.map.ScheduleFragmentContract;
import com.upit.coex.user.repository.model.data.booking.BookingInfoRespone;
import com.upit.coex.user.screen.base.fragment.BaseFragment;
import com.upit.coex.user.screen.detail.activity.DetailActivity;
import com.upit.coex.user.screen.dialog.DialogLoading;
import com.upit.coex.user.screen.map.adapter.BookingItemAdapter;
import com.upit.coex.user.template.actions.IActionCalendar;
import com.upit.coex.user.template.widget.CalendarView;
import com.upit.coex.user.viewmodel.map.fragment.ScheduleFragmentViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScheduleFragment extends BaseFragment<ScheduleFragmentViewModel> implements ScheduleFragmentContract.ScheduleInterfaceFragmentView,
        BookingItemAdapter.OnItemClick, IActionCalendar.CalendarViewListener {
    private static final int RESULT_EDIT_BOOKING = 8794;
    private View v;
    private RecyclerView rcyMain;
    private BookingItemAdapter adapter;
    private DialogLoading mDialogLoading;
    private LinearLayout layoutShow, layoutNothing;
    private CalendarView mCalendarView;
    private List<Long> mListDateBooking;
    private Calendar mCalenderNow;

    public ScheduleFragment(){

    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_scheldule, container, false);
        mViewModal = ViewModelProviders.of(this).get(ScheduleFragmentViewModel.class);
        mViewModal.setView(this);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mDialogLoading.startLoadingDialog();
        mCalenderNow = Calendar.getInstance();
        mViewModal.getAllBookingHistory();
        int flag = 0;
        int month = mCalenderNow.get(Calendar.MONTH);
        int year = mCalenderNow.get(Calendar.YEAR);
        int day = mCalenderNow.get(Calendar.DATE);
        for (int i = 0; i < mListDateBooking.size(); i++) {
            Date date = new Date(mListDateBooking.get(i));
            if ((date.getYear() + 1900) == year && date.getMonth() == month && day == date.getDate()) {
                flag = 1;
                mViewModal.getData(mListDateBooking.get(i), day, month);
            }
        }
        if (flag == 0) {
            layoutShow.setVisibility(View.INVISIBLE);
            layoutNothing.setVisibility(View.VISIBLE);
            mDialogLoading.dissLoadingDialog();
        }
    }

    @Override
    public void observeLifeCycle() {

    }

    @Override
    public void bindView() {
        mDialogLoading = new DialogLoading(getActivity());
        mListDateBooking = new ArrayList<>();
        mCalendarView = v.findViewById(R.id.fragment_schedule_calender);
        rcyMain = v.findViewById(R.id.fragmetn_schedule_rcy_item);
        layoutShow = v.findViewById(R.id.fragment_schedule_show);
        layoutNothing = v.findViewById(R.id.fragment_schedule_show_nothing);
        adapter = new BookingItemAdapter(this.getContext());
        adapter.setItemClick(this);
        rcyMain.setAdapter(adapter);
        onEventCalendar();
    }

    public void onEventCalendar() {
        mCalendarView.setChoosedDaysByMonthArrayList(setDataCalender(mCalendarView.getmCalendar()));
        mCalendarView.onCalendarViewListener(this);
        onClickedItemDay(Calendar.getInstance());
    }

    public ArrayList<Integer> setDataCalender(Calendar calendar) {
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        ArrayList<Integer> days = new ArrayList<>();
        for (int i = 0; i < mListDateBooking.size(); i++) {
            Date date = new Date(mListDateBooking.get(i));
            if ((date.getYear() + 1900) == year && date.getMonth() == month) {
                days.add(date.getDate());
            }
        }
        return days;
    }

    @Override
    public void showAllDayBooking(List<Long> data) {
        mListDateBooking = data;
        mCalendarView.setChoosedDaysByMonthArrayList(setDataCalender(mCalendarView.getmCalendar()));
    }

    @Override
    public void setResultFalse() {
        Toast.makeText(getActivity(), "Fail !", Toast.LENGTH_SHORT).show();
        mDialogLoading.dissLoadingDialog();
    }

    @Override
    public void showInfoBooking(List<BookingInfoRespone> mDatarespone, int day, int month, boolean result) {
        if (result) {
            adapter.setData(mDatarespone, day, month);
            layoutNothing.setVisibility(View.GONE);
            layoutShow.setVisibility(View.VISIBLE);
        }
        mDialogLoading.dissLoadingDialog();
    }

    @Override
    public void showResultCancelBooking(String message, boolean result) {
        if (result) {
            mViewModal.getAllBookingHistory();
            Toast.makeText(getActivity(), "Cancel successfully !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void itemClick(BookingInfoRespone pos) {
        Intent moveData = new Intent(getActivity(), DetailActivity.class);
        moveData.putExtra("data", pos);
        startActivityForResult(moveData, RESULT_EDIT_BOOKING);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_EDIT_BOOKING && resultCode == Activity.RESULT_OK) {
            mViewModal.getAllBookingHistory();
            mDialogLoading.startLoadingDialog();
            int flag = 0;
            // hien gọi api get user
            int month = mCalenderNow.get(Calendar.MONTH);
            int year = mCalenderNow.get(Calendar.YEAR);
            int day = mCalenderNow.get(Calendar.DATE);
            for (int i = 0; i < mListDateBooking.size(); i++) {
                Date date = new Date(mListDateBooking.get(i));
                if ((date.getYear() + 1900) == year && date.getMonth() == month && day == date.getDate()) {
                    flag = 1;
                    mViewModal.getData(mListDateBooking.get(i), day, month);
                }
            }
            if (flag == 0) {
                layoutShow.setVisibility(View.INVISIBLE);
                layoutNothing.setVisibility(View.VISIBLE);
                mDialogLoading.dissLoadingDialog();
            }
        }
    }

    @Override
    public void onClickedPrevious(Calendar calendar) {
        mCalendarView.setChoosedDaysByMonthArrayList(setDataCalender(calendar));
    }

    @Override
    public void onClickedNext(Calendar calendar) {
        mCalendarView.setChoosedDaysByMonthArrayList(setDataCalender(calendar));
    }

    @Override
    public void onClickedItemDay(Calendar calendar) {
        mCalenderNow = calendar;
        mDialogLoading.startLoadingDialog();
        int flag = 0;
        // hien gọi api get user
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DATE);
        for (int i = 0; i < mListDateBooking.size(); i++) {
            Date date = new Date(mListDateBooking.get(i));
            if ((date.getYear() + 1900) == year && date.getMonth() == month && day == date.getDate()) {
                flag = 1;
                mViewModal.getData(mListDateBooking.get(i), day, month);
            }
        }
        if (flag == 0) {
            layoutShow.setVisibility(View.INVISIBLE);
            layoutNothing.setVisibility(View.VISIBLE);
            mDialogLoading.dissLoadingDialog();
        }
    }
}
