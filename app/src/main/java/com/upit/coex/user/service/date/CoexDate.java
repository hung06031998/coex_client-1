package com.upit.coex.user.service.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by chien.lx on 3/9/2020.
 */

public class CoexDate {

    public String mMainCalendar;

    public CoexDate(BuilderDate that){
        mMainCalendar = that.mBuilderCalendar.toString();
    }

    public String toString(){
        return mMainCalendar;
    }

    public void example(){
        CoexDate m = new CoexDate.BuilderDate().getBuilder().setDateFormat("DD-mm-yyyy").getFullDateTime();


//0        CoexDate m = new CoexDate.BuilderDate().getBuilder().setDateFormat("DD mm yyyy HH:mm").getFullTime();
//        Date m = new Date.BuilderDate().getBuilder().setDateFormat("DD mm yyyy HH:mm").getDate();
//        Date m = new Date.BuilderDate().getBuilder().setDateFormat("DD mm yyyy HH:mm").getMonth();
//        Date m = new Date.BuilderDate().getBuilder().setDateFormat("DD mm yyyy HH:mm").getYear();
//        Date m = new Date.BuilderDate().getBuilder().setDateFormat("DD mm yyyy HH:mm").getMinimalDayOfFirstWeek();
//        Date m = new Date.BuilderDate().getBuilder().setDateFormat("DD mm yyyy HH:mm").getHour();
//        Date m = new Date.BuilderDate().getBuilder().setDateFormat("DD mm yyyy HH:mm").getMinute();
    }

    public static class BuilderDate{
        private StringBuilder mBuilderCalendar;
        private Calendar mCalendar;
        private SimpleDateFormat dateFormat;

        public BuilderDate(){
            mBuilderCalendar = new StringBuilder();
            mCalendar = Calendar.getInstance();
        }

        public BuilderDate getBuilder(){
            mBuilderCalendar = new StringBuilder();
            mCalendar = Calendar.getInstance();
            return this;
        }

        //set
        public BuilderDate setDay(int inc){
            mCalendar.set(Calendar.DATE,inc);
            return this;
        }

        public BuilderDate setMonth(int inc){
            mCalendar.set(Calendar.MONTH,inc);
            return this;
        }

        public BuilderDate setYear(int inc){
            mCalendar.set(Calendar.YEAR,inc);
            return this;
        }

        public BuilderDate setHour(int inc){
            mCalendar.set(Calendar.HOUR,inc);
            return this;
        }

        public BuilderDate setMinute(int inc){
            mCalendar.set(Calendar.MINUTE,inc);
            return this;
        }

        public BuilderDate setDateFormat(String inputPattern){
            dateFormat = new SimpleDateFormat(inputPattern);
            return this;
        }


        //========================================================================================

        //modify
        public BuilderDate modifiedDay(int inc){
            mCalendar.add(Calendar.DATE,inc);
            return this;
        }

        public BuilderDate modifiedMonth(int inc){
            mCalendar.add(Calendar.MONTH,inc);
            return this;
        }

        public BuilderDate modifiedYear(int inc){
            mCalendar.add(Calendar.YEAR,inc);
            return this;
        }

        public BuilderDate modifiedHour(int inc){
            mCalendar.add(Calendar.HOUR,inc);
            return this;
        }

        public BuilderDate modifiedMinute(int inc){
            mCalendar.add(Calendar.MINUTE,inc);
            return this;
        }
        //=============================================================

        //get
        public CoexDate getDayInWeek(){
            mCalendar.get(Calendar.DAY_OF_WEEK);
            mBuilderCalendar.append(dateFormat.format(mCalendar.getTime()));
            return new CoexDate(this);
        }

        public CoexDate getDate(){
            mCalendar.get(Calendar.DATE);
            mBuilderCalendar.append(dateFormat.format(mCalendar.getTime()));
            return new CoexDate(this);
        }

        public CoexDate getFirstDayOfWeek(){
            mCalendar.getFirstDayOfWeek();
            mBuilderCalendar.append(dateFormat.format(mCalendar.getTime()));
            return new CoexDate(this);
        }

        public CoexDate getMinimalDayOfFirstWeek(){
            mCalendar.getMinimalDaysInFirstWeek();
            mBuilderCalendar.append(dateFormat.format(mCalendar.getTime()));
            return new CoexDate(this);
        }

        public CoexDate getMonth(){
            mCalendar.get(Calendar.MONTH);
            mBuilderCalendar.append(dateFormat.format(mCalendar.getTime()));
            return new CoexDate(this);
        }

        public CoexDate getYear(){
            mCalendar.get(Calendar.YEAR);
            mBuilderCalendar.append(dateFormat.format(mCalendar.getTime()));
            return new CoexDate(this);
        }

        public CoexDate getHour(){
            mCalendar.get(Calendar.HOUR);
            mBuilderCalendar.append(dateFormat.format(mCalendar.getTime()));
            return new CoexDate(this);
        }

        public CoexDate getMinute(){
            mCalendar.get(Calendar.MINUTE);
            mBuilderCalendar.append(dateFormat.format(mCalendar.getTime()));
            return new CoexDate(this);
        }

        public CoexDate getFullDateTime(){
            mBuilderCalendar.append(dateFormat.format(mCalendar.getTime()));
            return new CoexDate(this);
        }

        public CoexDate getFullTime(){
            mBuilderCalendar.append(mCalendar.getTimeInMillis());
            return new CoexDate(this);
        }
        //=============================================================
    }
}
