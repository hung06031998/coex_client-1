package com.upit.coex.user.template.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TextViewCalendarView extends TextView {
    public TextViewCalendarView(Context context) {
        super(context);
        setView();
    }

    public TextViewCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setView();
    }

    public TextViewCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setView();
    }

    public TextViewCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setView();
    }

    private void setView(){
        setGravity(Gravity.CENTER);

    }
}
