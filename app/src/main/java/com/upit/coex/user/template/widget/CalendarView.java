package com.upit.coex.user.template.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.StringDef;

import com.upit.coex.user.R;
import com.upit.coex.user.template.actions.IActionCalendar;
import com.upit.coex.user.template.datahelper.ItemCalendar;
import com.upit.coex.user.template.viewhelper.ItemCalendarAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class CalendarView extends LinearLayout implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String STYLE_BASIC = "basic";
    private static final String STYLE_COMPACT = "compact";
    private static final String STYLE_PLAYONLY = "playonly";

    private static final int COLUMNS_NUMBER = 7;
    private static final int ROWS_NUMBER = 6;
    private static final float MIN_HEIGHT = (float) 875.0;

    private static final String TAG = "CalendarView";
    private int mItemHeight = 0;
    private FrameLayout mFrameLayoutPrevious;
    private FrameLayout mFrameLayoutNext;
    private TextView mTextViewTitle;
    private GridView mGridViewDays;
    private ItemCalendarAdapter mAdapter;
    private Calendar mCalendar;
    private String[] strMonth = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    private HashMap<Integer, Boolean> mChoosedDays = new HashMap<>();
    private ItemCalendar[] mDays;
    private int mIndexClickedItem = -1;

    // getter &&& settet

    public Calendar getmCalendar() {
        return mCalendar;
    }

    public void setmCalendar(Calendar mCalendar) {
        this.mCalendar = mCalendar;
    }

    //Listener
    private CopyOnWriteArrayList<IActionCalendar.CalendarViewListener> mListeners = new CopyOnWriteArrayList<>();


    public CalendarView(Context context) {
        super(context);
        setView(context, null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setView(context, attrs);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setView(context, attrs);
    }

    private void setView(Context context, AttributeSet attrs) {

        LayoutInflater.from(context).inflate(R.layout.calendar_view_widget, this);
//        CalendarView.LayoutParams lp1 = (CalendarView.LayoutParams) this.getLayoutParams();
//        int h = lp1.height;
//        Log.d(TAG, "h = " + h);


        mFrameLayoutPrevious = findViewById(R.id.flPrevious);

        mFrameLayoutNext = findViewById(R.id.flNext);

        mTextViewTitle = findViewById(R.id.tvTitleCalendar);

        mCalendar = Calendar.getInstance();
        mTextViewTitle.setText(String.format("%s %d", strMonth[mCalendar.get(Calendar.MONTH)], mCalendar.get(Calendar.YEAR)));

        Log.d(TAG, "month:" + mCalendar.get(Calendar.MONTH));

        ItemCalendar[][] days = getTableDay(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), this.mChoosedDays);
        this.
//        Log.d(TAG, "Current day:\n" + getTableDayToString(days));

//

        mFrameLayoutPrevious.setOnClickListener(this);
        mFrameLayoutNext.setOnClickListener(this);
        if (attrs != null){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CalendarView);
            try {
                float height = typedArray.getDimension(R.styleable.CalendarView_height, 0);
                if (height < MIN_HEIGHT){
                    height = MIN_HEIGHT;
                }
                mItemHeight = (int) height/8;
                Log.d("bao.nt", "height : " + height);
                ((LinearLayout.LayoutParams) findViewById(R.id.lnRoot).getLayoutParams()).height = (int) height;
                ((LinearLayout.LayoutParams) findViewById(R.id.lnHeader).getLayoutParams()).height = (int) mItemHeight;
                ((LinearLayout.LayoutParams) findViewById(R.id.lnDayName).getLayoutParams()).height = (int) mItemHeight;
                ((LayoutParams) findViewById(R.id.grvDays).getLayoutParams()).height = mItemHeight * 6;
                mGridViewDays = findViewById(R.id.grvDays);
                this.mDays = convert2xArrayToArray(days, COLUMNS_NUMBER, ROWS_NUMBER);
                mAdapter = new ItemCalendarAdapter(this.getContext(), this.mDays, mItemHeight);
                mGridViewDays.setAdapter(mAdapter);
                mGridViewDays.setOnItemClickListener(this);
            } finally {
                typedArray.recycle();
            }
        }


    }

    private ItemCalendar[] convert2xArrayToArray(ItemCalendar[][] sources, int columnsNum, int rowsNum) {
        ItemCalendar[] arr = new ItemCalendar[columnsNum * rowsNum];
        int index = 0;
        for (int i = 0; i < rowsNum; i++) {
            for (int j = 0; j < columnsNum; j++) {
//                Log.d(TAG, String.format("sources[%d][%d]: %s",i,j,sources[i][j]));
                arr[index] = sources[i][j];
                index++;
            }
        }
        return arr;
    }

    private ItemCalendar[][] getTableDay(int year, int month, HashMap<Integer, Boolean> mChoosedDays) {
        ItemCalendar[][] days = new ItemCalendar[6][7];
        int rowIndex = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month+1, 0); // month
        int endDay = calendar.get(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                days[i][j] = new ItemCalendar();
                days[i][j].mDay = "";
                days[i][j].isChoose = false;
                days[i][j].isClick = false;
            }
        }
        Log.d("anhtu123", month+"|" + endDay);

        for (int i = 1; i <= endDay; i++) {
            calendar.set(year, month, i); // month -1
            if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
                days[rowIndex][6].mDay = "" + i;
                if (mChoosedDays.get(i) != null && mChoosedDays.get(i) == true ){
                    days[rowIndex][6].isChoose = true;
                }
                rowIndex++;
            } else {

                days[rowIndex][calendar.get(Calendar.DAY_OF_WEEK) - 2].mDay = "" + i;
                if (mChoosedDays.get(i) != null && mChoosedDays.get(i) == true ){
                    days[rowIndex][calendar.get(Calendar.DAY_OF_WEEK) - 2].isChoose = true;
                }
            }
        }
        return days;
    }

//    private String getTableDayToString(String[][] days) {
//        String s = "";
//        for (int i = 0; i < 6; i++) {
//            for (int j = 0; j < 7; j++) {
//                s += (String.format("%2s", days[i][j]) + " ");
//            }
//            s += "\n";
//        }
//        return s;
//    }

    private Calendar getBeginDayOfPreviousMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        if (month == 0) {
            calendar.set(year - 1, 11
                    , 1);
        } else {
            calendar.set(year, month - 1, 1);
        }
        return calendar;
    }

    private Calendar getBeginDayOfNextMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        if (month == 11) {
            calendar.set(year + 1, 0, 1);
        } else {
            calendar.set(year, month + 1, 1);
        }
        return calendar;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.flPrevious:
                Log.d(TAG, "previous");
                this.mChoosedDays.clear();
                mCalendar = getBeginDayOfPreviousMonth(mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.YEAR));
                Log.d(TAG, "previous" + mCalendar.getTime());
                updateCalendar();
                mListeners.forEach(listener -> {
                    listener.onClickedPrevious(mCalendar);
                });
                break;
            case R.id.flNext:
                Log.d(TAG, "next");
                this.mChoosedDays.clear();
                mCalendar = getBeginDayOfNextMonth(mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.YEAR));
                Log.d(TAG, "next:" + mCalendar.getTime());
                updateCalendar();
                mListeners.forEach(listener -> {
                    listener.onClickedNext(mCalendar);
                });
                break;
        }
    }

    private void updateCalendar(){
        ItemCalendar[][] days = getTableDay(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), this.mChoosedDays);
        Log.d("anhtu1234", mCalendar.get(Calendar.MONTH)+"");

        this.mDays = convert2xArrayToArray(days, COLUMNS_NUMBER, ROWS_NUMBER);
        mAdapter = new ItemCalendarAdapter(this.getContext(),this.mDays , mItemHeight);
        mAdapter.notifyDataSetChanged(); // Tu
        mGridViewDays.setAdapter(mAdapter);

        mTextViewTitle.setText(String.format("%s %d", strMonth[mCalendar.get(Calendar.MONTH)], mCalendar.get(Calendar.YEAR)));
    }

    public void onCalendarViewListener(IActionCalendar.CalendarViewListener listener){
        mListeners.add(listener);
    }

    public void setChoosedDaysByMonth(int [] days){
        this.mChoosedDays.clear();
        for (int day : days) {
            this.mChoosedDays.put(day, true);

        }
        updateCalendar();
        Log.d("bao.nt", "this.mChoosedDays:"+this.mChoosedDays.get(1));
    }

    public void setChoosedDaysByMonthArrayList(ArrayList<Integer> days){
        this.mChoosedDays.clear();
        for (int day : days) {
            this.mChoosedDays.put(day, true);
        }
        updateCalendar();
        Log.d("bao.nt", "this.mChoosedDays:"+this.mChoosedDays.get(1));
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("bao.nt", "i:"+i+"   l:"+l);
        if (!"".equals(this.mDays[i].mDay)) {
            if (mIndexClickedItem >= 0) {
                this.mDays[mIndexClickedItem].isClick = false;
            }
            this.mIndexClickedItem = i;
            this.mDays[i].isClick = true;
            mAdapter = new ItemCalendarAdapter(this.getContext(), this.mDays, mItemHeight);
            mGridViewDays.setAdapter(mAdapter);
            Calendar calendar = this.mCalendar;
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(this.mDays[i].mDay));
            mListeners.forEach(listener -> {
                listener.onClickedItemDay(calendar);
            });
        }
    }


    @StringDef({STYLE_BASIC, STYLE_COMPACT, STYLE_PLAYONLY})
    @interface Style {
    }
}
