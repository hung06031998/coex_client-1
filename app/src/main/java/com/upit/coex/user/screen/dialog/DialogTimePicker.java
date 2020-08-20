package com.upit.coex.user.screen.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

import com.upit.coex.user.R;
import com.upit.coex.user.template.actions.IActionCalendar;
import com.upit.coex.user.template.widget.CalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.jeesoft.widget.pickerview.CharacterPickerView;
import cn.jeesoft.widget.pickerview.OnOptionChangedListener;

public class DialogTimePicker implements AdapterView.OnItemClickListener {
    private Activity activity;
    private AlertDialog dialog;
    private onClickYes mOnClickYes;
    private Button btnOK;
    private ImageView btnClose;
    private CharacterPickerView spnChooseHour;
    private CalendarView mCalendarView;
    private List<Long> mListDateBooking;
    private int date = 0, month = 0, year = 0, hour = 0;
    private Calendar mCalendar;

    public void setmOnClickYes(onClickYes mOnClickYes) {
        this.mOnClickYes = mOnClickYes;
    }

    public DialogTimePicker(Activity activity) {
        this.activity = activity;
    }

    public void startLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_time_picker, null);
        builder.setView(view);
        builder.setCancelable(false);
        //
        mCalendarView = view.findViewById(R.id.dialog_time_picker_calender);
        spnChooseHour = view.findViewById(R.id.dialog_time_picker_spinner);
        btnClose = view.findViewById(R.id.dialog_pick_date_btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnOK = view.findViewById(R.id.dialog_time_picker_btn_ok);
        mListDateBooking = new ArrayList<>();
        List<String> listHour = new ArrayList<>();
        for (int i = 0; i <= 23; i++) {
            listHour.add(i + "");
        }
        spnChooseHour.setMaxTextSize(14);
        spnChooseHour.setPicker(listHour);
        spnChooseHour.setOnOptionChangedListener(new OnOptionChangedListener() {
            @Override
            public void onOptionChanged(int option1, int option2, int option3) {
                hour = option1;
            }
        });

        mCalendarView.setChoosedDaysByMonthArrayList(setDataCalender(mCalendarView.getmCalendar()));
        mCalendarView.onCalendarViewListener(new IActionCalendar.CalendarViewListener() {
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
                mCalendar = calendar;
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);
                date = calendar.get(Calendar.DATE);
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (month != 0 && date != 0 && year != 0) {
                    mOnClickYes.onYesClick(hour, date, month, year, mCalendar);
                    dialog.dismiss();
                }
            }
        });

        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        hour = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
    }

    public interface onClickYes {
        void onYesClick(int hour, int date, int month, int year, Calendar calendar);
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
}
