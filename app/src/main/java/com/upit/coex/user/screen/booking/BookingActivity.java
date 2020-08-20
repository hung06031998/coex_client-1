package com.upit.coex.user.screen.booking;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.upit.coex.user.R;
import com.upit.coex.user.contract.booking.BookingConrtact;
import com.upit.coex.user.repository.model.data.base.BaseErrorData;
import com.upit.coex.user.repository.model.data.booking.BookingInfoRespone;
import com.upit.coex.user.repository.model.data.booking.ListDate;
import com.upit.coex.user.repository.model.data.map.Data;
import com.upit.coex.user.repository.model.data.map.Room;
import com.upit.coex.user.screen.base.activity.BaseActivity;
import com.upit.coex.user.screen.detail.adapter.DetailItemAdapter;
import com.upit.coex.user.screen.dialog.DialogLoading;
import com.upit.coex.user.screen.dialog.DialogTimePicker;
import com.upit.coex.user.service.logger.L;
import com.upit.coex.user.viewmodel.booking.BookingActivityViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BookingActivity extends BaseActivity<BookingActivityViewModel> implements BookingConrtact.BookingView, DetailItemAdapter.OnItemClickListener, DialogTimePicker.onClickYes {
    private EditText edtNumberGuest, edtDuration;
    private Button btnSubmit;
    private ImageView btnBack;
    private TextView txtCost;
    private TextView edtStyleRoom, edtTime;
    private Data data;
    private Room roomNow;
    private int type = -1;
    private String bookingId;
    private Dialog myDialog;
    private long costNow = -1;
    private int style = -1;
    private ArrayList<ListDate> listDate;
    private BookingInfoRespone bookingRespone;
    private DialogTimePicker mDialogTimePicker;
    private DialogLoading mDialogLoading;
    private Calendar mCalender;
    private TextView txtOversizePeople;
    private boolean checkShowOverPeople = false;
    private LinearLayout layoutShowPrice;
    private ProgressBar proShowPrice;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        mViewModal = ViewModelProviders.of(this).get(BookingActivityViewModel.class);
        mViewModal.setView(this);
        style = getIntent().getIntExtra("type", -1);
        if (style == 1) {
            data = (Data) getIntent().getSerializableExtra("key");
            createDialog();
        } else if (style == 2) {
            bookingRespone = (BookingInfoRespone) getIntent().getSerializableExtra("key");
            edtDuration.setText(bookingRespone.getDuration() + "");
            edtNumberGuest.setText(bookingRespone.getNumPerson() + "");
            edtStyleRoom.setText(bookingRespone.getRoom().getName());
            edtDuration.setHint(" hour(s)");
            edtNumberGuest.setHint(bookingRespone.getRoom().getMaxPerson() + " Guest(s)");

            Date d = new Date(bookingRespone.getDateTime());
            this.day = d.getDate();
            this.mount = d.getMonth();
            this.year = d.getYear() + 1900;
            this.hour = Integer.parseInt(bookingRespone.getStartTimeDate() + "");
            String gg = d.getDate() + "/" + (d.getMonth() + 1) + "/" + (d.getYear() + 1900) + " - " + bookingRespone.getStartTimeDate() + "h";
            checkAllFillData(edtDuration.getText().toString(), edtNumberGuest.getText().toString(), type);
            edtTime.setText(gg);

            bookingId = bookingRespone.getId();
            edtStyleRoom.setClickable(false);
            edtTime.setHint("Time");
            Room temp = new Room();
            temp.setAbout(bookingRespone.getRoom().getAbout());
            temp.setId(bookingRespone.getRoom().getId());
            temp.setMaxPerson(bookingRespone.getRoom().getMaxPerson());
            temp.setPrice(bookingRespone.getRoom().getPrice());
            temp.setCoworkingId(bookingRespone.getRoom().getCoworkingId());
            temp.setName(bookingRespone.getRoom().getName());
            btnSubmit.setText("EDIT");
            roomNow = temp;
            type = 1000;
        } else {
            data = (Data) getIntent().getSerializableExtra("key");
            createDialog();
            type = getIntent().getIntExtra("room", 0);
            onItemClick(type);
        }
        txtCost.setText("- VND");

        onDIalogTouch();
    }

    private void onDIalogTouch() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        edtStyleRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (style == 1 || style == 3) {
                    myDialog.show();
                }
            }
        });
        edtNumberGuest.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                checkAllFillData(edtDuration.getText().toString(), edtNumberGuest.getText().toString(), type);
            }
        });
        edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogTimePicker.startLoadingDialog();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtDuration.getText().toString().equals("") || edtTime.getText().toString().equals("") || edtNumberGuest.getText().toString().equals("") || type == -1) {
                    Toast.makeText(BookingActivity.this, "Require data !", Toast.LENGTH_SHORT).show();
                } else {
                    showDialog();
                }
            }
        });
        edtNumberGuest.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!edtNumberGuest.getText().toString().equals("") && roomNow != null) {
                    if (Integer.parseInt(edtNumberGuest.getText().toString().trim() + "") > roomNow.getMaxPerson()) {
                        if (!checkShowOverPeople) {
                            checkShowOverPeople = true;
                            txtOversizePeople.setVisibility(View.VISIBLE);
                        }
                    } else {
                        if (checkShowOverPeople) {
                            checkShowOverPeople = false;
                            txtOversizePeople.setVisibility(View.GONE);
                        }
                        checkAllFillData(edtDuration.getText().toString(), edtNumberGuest.getText().toString(), type);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtDuration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkAllFillData(edtDuration.getText().toString(), edtNumberGuest.getText().toString(), type);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void showDialog() {
        Dialog myDialog2 = new Dialog(BookingActivity.this);
        myDialog2.setContentView(R.layout.dialog_confirm);
        TextView txtTitle = myDialog2.findViewById(R.id.confirm_dialog_txt_title);
        TextView txtDes = myDialog2.findViewById(R.id.confirm_dialog_txt_description);
        if (style == 1 || style == 3) {
            txtTitle.setText(R.string.activity_booking_dialog_txt_title);
            txtDes.setText(R.string.activity_booking_dialog_txt_des);
        } else {
            txtTitle.setText(R.string.activity_booking_dialog_txt_title_style_2);
            txtDes.setText(R.string.activity_booking_dialog_txt_des_style_2);
        }
        myDialog2.findViewById(R.id.confirm_dialog_btn_cancel).setOnClickListener(
                v -> myDialog2.dismiss());
        myDialog2.findViewById(R.id.confirm_dialog_btn_accept).setOnClickListener(v -> {
            mDialogLoading.startLoadingDialog();
            caculateDate();
            if (style == 1 || style == 3) {
                mViewModal.booking(listDate, Integer.parseInt(edtNumberGuest.getText().toString()), roomNow.getId());
            } else {
                mViewModal.editBooking(listDate, Integer.parseInt(edtNumberGuest.getText().toString()), roomNow.getId(), bookingId);
            }
            myDialog2.cancel();
        });
        myDialog2.show();
        myDialog2.setCanceledOnTouchOutside(false);//bam ra ngoai
        myDialog2.setCancelable(false);//bam nut back
    }

    private void caculateDate() {
        listDate = new ArrayList<>();
        int duration = Integer.parseInt(edtDuration.getText().toString());
        int startTime = hour;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, mount, day);
        while (duration > 0) {
            ListDate temp = new ListDate();
            Date dateOne = calendar.getTime();
            temp.setDate((dateOne.getDate() < 10 ? "0" + dateOne.getDate() : dateOne.getDate()) + "-" + ((dateOne.getMonth() + 1) < 10 ? "0" + (dateOne.getMonth() + 1) : (dateOne.getMonth() + 1)) + "-" + (dateOne.getYear() + 1900));
            temp.setStartTime(startTime);
            if (duration < 24 - startTime) {
                temp.setDuration(duration);
            } else {
                temp.setDuration(24 - startTime);
            }
            listDate.add(temp);

            duration -= 24 - startTime;
            calendar.add(Calendar.DATE, 1);
            startTime = 0;
        }
//        for (int i = 0; i < listDate.size(); i++) {
//            L.d("ahiuhiu",listDate.get(i).getDuration()+" : " + listDate.get(i).getDate() + " : " + listDate.get(i).getStartTime());
//        }
    }

    private void createDialog() {
        DetailItemAdapter adapter = new DetailItemAdapter();
        adapter.setData(data.getRooms());
        adapter.setType(1);
        adapter.setItemClickListener(this);

        myDialog = new Dialog(BookingActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View v2 = inflater.inflate(R.layout.dialog_style_room, null);
        myDialog.setContentView(v2);
        myDialog.findViewById(R.id.dialog_style_room_btn_close).setOnClickListener(
                v -> myDialog.dismiss());
        TextView t = myDialog.findViewById(R.id.dialog_style_room_txt_title);
        t.setText("Style room");
        RecyclerView temp = myDialog.findViewById(R.id.dialog_style_room_rcy);
        temp.setAdapter(adapter);
        myDialog.setCanceledOnTouchOutside(false);//bam ra ngoai
        myDialog.setCancelable(false);//bam nut back
    }

    private void checkAllFillData(String duration, String numGuest, int type) {
        if (!duration.equals("") && !numGuest.equals("") && type != -1 && day != -1 && mount != -1 && year != -1 && hour != -1) {
            proShowPrice.setVisibility(View.VISIBLE);
            layoutShowPrice.setVisibility(View.GONE);
            caculateDate();
            mViewModal.getPrice(listDate, Integer.parseInt(edtNumberGuest.getText().toString()), roomNow.getId());
        }
    }

    @Override
    public void observeLifeCycle() {

    }

    @Override
    public void bindView() {
        edtDuration = findViewById(R.id.activity_booking_edt_duration);
        edtNumberGuest = findViewById(R.id.activity_booking_edt_number_guest);
        edtStyleRoom = findViewById(R.id.activity_booking_edt_style_room);
        edtTime = findViewById(R.id.activity_booking_txt_time);
        btnSubmit = findViewById(R.id.activity_booking_btn_ok);
        btnBack = findViewById(R.id.activity_booking_btn_back);
        txtCost = findViewById(R.id.activity_booking_edt_cost);
        txtOversizePeople = findViewById(R.id.activity_booking_txt_oversize_people);
        layoutShowPrice = findViewById(R.id.layout_show_price);
        proShowPrice = findViewById(R.id.layout_show_price_loading);
        proShowPrice.setVisibility(View.GONE);
        txtOversizePeople.setVisibility(View.GONE);
        mDialogLoading = new DialogLoading(this);
        mDialogTimePicker = new DialogTimePicker(this);
        mDialogTimePicker.setmOnClickYes(this);
    }

    @Override
    public void onItemClick(int item) {
        myDialog.dismiss();
        type = item;
        roomNow = data.getRooms().get(type);
        edtStyleRoom.setText(roomNow.getName());
        edtNumberGuest.setHint("Max person : " + roomNow.getMaxPerson());
        checkAllFillData(edtDuration.getText().toString(), edtNumberGuest.getText().toString(), type);
    }

    private int day = -1, mount = -1, year = -1, hour = -1;

    @Override
    public void booingRespone(int number, String respone) {
        mDialogLoading.dissLoadingDialog();
        if (number == 0) {
            BaseErrorData errorData = new BaseErrorData(respone);
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText(errorData.getMessage())
                    .show();
        } else {
            new SweetAlertDialog(BookingActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Booking done !")
                    .setContentText("Your booking is successful !")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result", "ok");
                            returnIntent.putExtra("date", mCalender);
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
                        }
                    })
                    .show();
        }
    }

    @Override
    public void editBooking(boolean result, String message) {
        mDialogLoading.dissLoadingDialog();
        if (!result) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setConfirmButtonBackgroundColor(R.color.main_color)
                    .setContentText(message)
                    .show();
        } else {
            SweetAlertDialog dialog = new SweetAlertDialog(BookingActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Edit done !")
                    .setContentText("Your booking is successful editing!")
                    .setConfirmButtonBackgroundColor(R.color.greemn)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result", "ok");
                            returnIntent.putExtra("date", mCalender);
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();
                        }
                    });
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
    }

    @Override
    public void showPrice(long cost, boolean result,String message) {
        proShowPrice.setVisibility(View.GONE);
        layoutShowPrice.setVisibility(View.VISIBLE);
        if (result) {
            txtCost.setText(cost + " VND");
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onYesClick(int hour, int date, int month, int year, Calendar calender) {
        this.mCalender = calender;
        this.day = date;
        this.mount = month;
        this.year = year;
        this.hour = hour;
        String temp = day + "/" + (mount + 1) + "/" + year + " - " + hour + "h";
        checkAllFillData(edtDuration.getText().toString(), edtNumberGuest.getText().toString(), type);
        edtTime.setText(temp);
        L.d("ahiuhiu", mCalender.toString());
    }
}
