package com.upit.coex.user.screen.detail.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.upit.coex.user.R;
import com.upit.coex.user.repository.model.data.base.BaseErrorData;
import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.booking.Booking;
import com.upit.coex.user.repository.model.data.booking.BookingInfoRespone;
import com.upit.coex.user.repository.model.data.booking.CancelBookingRespone;
import com.upit.coex.user.repository.model.data.booking.CheckResultResponse;
import com.upit.coex.user.repository.model.data.booking.Coworking;
import com.upit.coex.user.repository.model.data.booking.CreatePaymentResponse;
import com.upit.coex.user.repository.model.data.booking.PaymentRequest;
import com.upit.coex.user.repository.model.data.booking.Room;
import com.upit.coex.user.repository.model.data.booking.VnpayCreatePayment;
import com.upit.coex.user.repository.model.remote.booking.BookingApiRequest;
import com.upit.coex.user.repository.model.remote.booking.CancelBookingApiRequest;
import com.upit.coex.user.screen.booking.BookingActivity;
import com.upit.coex.user.screen.detail.adapter.ListPhotoAdapter;
import com.upit.coex.user.screen.dialog.DialogLoading;
import com.upit.coex.user.screen.vnpay.VnpayPaymentActivity;
import com.upit.coex.user.service.fetchandsave.ApiRepository;
import com.upit.coex.user.service.logger.L;
import com.upit.coex.user.service.sharepreference.CoexSharedPreference;
import com.upit.coex.user.template.widget.QRCode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.relex.circleindicator.CircleIndicator;
import vn.zalopay.listener.ZaloPayListener;
import vn.zalopay.sdk.MerchantReceiver;
import vn.zalopay.sdk.ZaloPayErrorCode;
import vn.zalopay.sdk.ZaloPaySDK;

import static com.upit.coex.user.constants.register.RegisterConstant.CANCEL_BOOKING_URL;
import static com.upit.coex.user.constants.register.RegisterConstant.PAYMENT_URL;
import static com.upit.coex.user.constants.register.RegisterConstant.VNPAY_URL;

public class DetailActivity extends AppCompatActivity {

    private static final int RESULT_EDIT_BOOKING = 1255;
    private static final int RESULT_VNPAY = 1200;
    private RelativeLayout layoutWifi2, layoutDrink2, layoutPrinter2, layoutConCall2, layoutAirCon2, layoutLoading;
    private TextView txtName, txtDes, txtNumberPerson, txtTime, txtDuration, txtCost, txtAbout, txtRoomName, txtRoomCOst, txtRoomAbout;
    private QRCode qrCode;
    private Button btnEdit, btnDelete, btnZaloPayment, btnVnpayPayment;
    private BookingInfoRespone bookingNow;
    private ViewPager mViewPaper;
    private CircleIndicator indicator;
    private TextView txtStatus, txtBookingRefe;
    private ImageView btnBack;
    private DialogLoading mDialogLoading;
    private LinearLayout paymentLayout, paidLayout;
    private boolean showDialog = false;

    MerchantReceiver receiver;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
        showData(bookingNow);
    }

    private void showData(BookingInfoRespone pos) {
        Coworking coo = pos.getCoworking();
        Room room = pos.getRoom();
        if (coo != null && room != null) {
            txtName.setText(coo.getName());
            txtDes.setText(coo.getAddress());
            txtNumberPerson.setText(pos.getNumPerson() + " Guest(s)");
            txtDuration.setText(pos.getDuration() + " hour(s)");
            Date date = new Date(pos.getDateTime());
            SimpleDateFormat df2 = new SimpleDateFormat("MMM dd - yyyy");
            String dateText = df2.format(date);
            txtTime.setText(dateText);
            txtCost.setText(pos.getPrice() + " VND");
            txtAbout.setText(coo.getAbout());
            if (coo.getService().getDrink()) {
                layoutDrink2.setVisibility(View.VISIBLE);
            } else {
                layoutDrink2.setVisibility(View.GONE);
            }
            if (coo.getService().getAirConditioning()) {
                layoutAirCon2.setVisibility(View.VISIBLE);
            } else {
                layoutAirCon2.setVisibility(View.GONE);
            }
            if (coo.getService().getConversionCall()) {
                layoutConCall2.setVisibility(View.VISIBLE);
            } else {
                layoutConCall2.setVisibility(View.GONE);
            }
            if (coo.getService().getPrinter()) {
                layoutPrinter2.setVisibility(View.VISIBLE);
            } else {
                layoutPrinter2.setVisibility(View.GONE);
            }
            if (coo.getService().getWifi()) {
                layoutWifi2.setVisibility(View.VISIBLE);
            } else {
                layoutWifi2.setVisibility(View.GONE);
            }
            if (pos.getCoworking().getPhoto().size() != 0) {
                L.d("ahiuhiu", pos.getCoworking().getPhoto().size() + "");
                ArrayList<String> listImage = (ArrayList<String>) pos.getCoworking().getPhoto();
                ListPhotoAdapter adapter = new ListPhotoAdapter(this, listImage);
                mViewPaper.setAdapter(adapter);
                indicator.setViewPager(mViewPaper);
                adapter.registerDataSetObserver(indicator.getDataSetObserver());
            }
            txtRoomName.setText(room.getName());
            txtRoomAbout.setText(room.getAbout());
            txtRoomCOst.setText(room.getPrice() + "/1 person/1 hour");
            txtBookingRefe.setText("#" + pos.getBookingReference());
            if (!pos.getStatus().equals("cancelled")) {
                qrCode.setVisibility(View.VISIBLE);
                qrCode.setText(pos.getId());
                if (pos.getCheckIn()) {
                    if (pos.getCheckOut()) {
                        txtStatus.setText("Done");
                    } else {
                        txtStatus.setText("Checked in");
                    }
                } else {
                    txtStatus.setText("Waiting to check in");
                    txtStatus.setTextColor(Color.parseColor("#66E66A"));
                }
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent moveData = new Intent(DetailActivity.this, BookingActivity.class);
                        moveData.putExtra("type", 2);
                        moveData.putExtra("key", bookingNow);
                        startActivityForResult(moveData, RESULT_EDIT_BOOKING);
                    }
                });
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDialogDelete(bookingNow.getId());
                    }
                });
            } else {
                qrCode.setVisibility(View.GONE);
                txtStatus.setText("Cancel");
                txtStatus.setTextColor(Color.parseColor("#FF0055"));
                btnEdit.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
                btnZaloPayment.setVisibility(View.GONE);
                btnVnpayPayment.setVisibility(View.GONE);
                showDialog = true;
                layoutLoading.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_EDIT_BOOKING && resultCode == Activity.RESULT_OK) {
            finish();
        }else if(requestCode == RESULT_VNPAY){
            Log.d("pbm", "onActivityResult: ");
            showDialog = true;
            checkPaymentSatus();
        }else{
            ZaloPaySDK.getInstance().onActivityResult(requestCode, resultCode, data);
        }
    }

    private void init() {
        txtName = findViewById(R.id.item_schedule_txt_title);
        txtDes = findViewById(R.id.item_schedule_txt_description);
        txtAbout = findViewById(R.id.item_schedule_txt_about);
        txtCost = findViewById(R.id.item_schedule_txt_cost);
        txtDuration = findViewById(R.id.item_schedule_txt_duration);
        txtNumberPerson = findViewById(R.id.item_schedule_txt_number_of_guest);
        txtRoomCOst = findViewById(R.id.item_schedule_txt_room_cost);
        txtRoomName = findViewById(R.id.item_schedule_txt_room_name);
        txtTime = findViewById(R.id.item_schedule_txt_time_appointment);
        txtRoomAbout = findViewById(R.id.item_schedule_txt_room_description);
        layoutAirCon2 = findViewById(R.id.item_schedule_item_air_con);
        layoutConCall2 = findViewById(R.id.item_schedule_item_con_call);
        layoutDrink2 = findViewById(R.id.item_schedule_item_drink);
        txtBookingRefe = findViewById(R.id.item_schedule_txt_booking_refe);
        btnBack = findViewById(R.id.activity_detail_btn_back);
        layoutPrinter2 = findViewById(R.id.item_schedule_item_printer);
        layoutWifi2 = findViewById(R.id.item_schedule_item_wifi);
        btnDelete = findViewById(R.id.item_schedule_btn_delete);
        btnEdit = findViewById(R.id.item_schedule_btn_edit);
        qrCode = findViewById(R.id.item_schedule_img_qr_code);
        indicator = findViewById(R.id.activity_detail_indicator);
        mViewPaper = findViewById(R.id.activity_detail_view_paper);
        txtStatus = findViewById(R.id.item_schedule_txt_status);
        btnZaloPayment = findViewById(R.id.item_schedule_btn_zalo_payment);
        btnVnpayPayment = findViewById(R.id.item_schedule_btn_vnpay_payment);
        paymentLayout = findViewById(R.id.activity_detail_payment_layout);
        paidLayout = findViewById(R.id.activity_detail_payment_done);
        layoutLoading = findViewById(R.id.layout_show_loading);
        mDialogLoading = new DialogLoading(this);
        ZaloPaySDK.getInstance().initWithAppId(553);
        receiver = new MerchantReceiver();
        intentFilter = new IntentFilter("vn.zalopay.sdk.ZP_ACTION");

        btnVnpayPayment.setOnClickListener(view -> {
            vnpayPayment();
        });

        btnZaloPayment.setOnClickListener(view -> {
            getTransToken();
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent getData = getIntent();
        bookingNow = (BookingInfoRespone) getData.getSerializableExtra("data");
    }

    private void vnpayPayment() {
        Intent intent = new Intent(DetailActivity.this, VnpayPaymentActivity.class);
        intent.putExtra("transid",bookingNow.getId());
        startActivityForResult(intent,RESULT_VNPAY);
    }

    private void showDialogDelete(String cancelId) {
        Dialog myDialog2 = new Dialog(this);
        myDialog2.setContentView(R.layout.dialog_confirm);
        TextView txtTitle = myDialog2.findViewById(R.id.confirm_dialog_txt_title);
        TextView txtDes = myDialog2.findViewById(R.id.confirm_dialog_txt_description);
        txtTitle.setText(R.string.fragment_schedule_txt_dialog_title);
        txtDes.setText(R.string.fragment_schedule_txt_dialog_des);
        myDialog2.findViewById(R.id.confirm_dialog_btn_cancel).setOnClickListener(
                v -> myDialog2.dismiss());
        myDialog2.findViewById(R.id.confirm_dialog_btn_accept).setOnClickListener(v -> {
            cancelBooking(cancelId);
            myDialog2.cancel();
        });
        myDialog2.show();
        myDialog2.setCanceledOnTouchOutside(false);//bam ra ngoai
        myDialog2.setCancelable(false);//bam nut back
    }

    private void openZaloPayApp(String zaloPayToken){
        ZaloPaySDK.getInstance().payOrder(DetailActivity.this, zaloPayToken, new MyZaloPayListener());
    }

    private void getTransToken(){
        mDialogLoading.startLoadingDialog();
        ApiRepository.getInstance().
                setUrl(PAYMENT_URL).
                createRetrofit().
                create(BookingApiRequest.class).
                paymentBooking("Bearer " + CoexSharedPreference.getInstance().get("token", String.class),new PaymentRequest(bookingNow.getId())).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<BaseResponce<CreatePaymentResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseResponce<CreatePaymentResponse> value) {
                        if (value.getCode() == 200) {
                            openZaloPayApp(value.getMdata().getZptranstoken());
                        } else {
                            new SweetAlertDialog(DetailActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setConfirmButtonBackgroundColor(Color.parseColor("#FF0055"))
                                    .setContentText(value.getMessage())
                                    .show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mDialogLoading.dissLoadingDialog();
                        BaseErrorData err = new BaseErrorData(e);
                        if (err.getMessage() != null)
                            new SweetAlertDialog(DetailActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setConfirmButtonBackgroundColor(Color.parseColor("#FF0055"))
                                    .setContentText(err.getMessage())
                                    .show();
                        else
                            new SweetAlertDialog(DetailActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setConfirmButtonBackgroundColor(Color.parseColor("#FF0055"))
                                    .setContentText(e.getMessage())
                                    .show();
                    }
                });
    }

    private void cancelBooking(String cancelId) {
        ApiRepository.getInstance().
                setUrl(CANCEL_BOOKING_URL + cancelId + "/").
                createRetrofit().
                create(CancelBookingApiRequest.class).
                doCancelBooking("Bearer " + CoexSharedPreference.getInstance().get("token", String.class)).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<BaseResponce<CancelBookingRespone>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseResponce<CancelBookingRespone> value) {
                        if (value.getCode() == 200) {
                            SweetAlertDialog dialog = new SweetAlertDialog(DetailActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Cancel done !")
                                    .setContentText("Your booking is successful cancel!")
                                    .setConfirmButtonBackgroundColor(Color.parseColor("#66E66A"))
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            finish();
                                        }
                                    });
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.show();
                        } else {
                            new SweetAlertDialog(DetailActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setConfirmButtonBackgroundColor(Color.parseColor("#FF0055"))
                                    .setContentText(value.getMessage())
                                    .show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        BaseErrorData err = new BaseErrorData(e);
                        if (err.getMessage() != null)
                            new SweetAlertDialog(DetailActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setConfirmButtonBackgroundColor(Color.parseColor("#FF0055"))
                                    .setContentText(err.getMessage())
                                    .show();
                        else
                            new SweetAlertDialog(DetailActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setConfirmButtonBackgroundColor(Color.parseColor("#FF0055"))
                                    .setContentText(e.getMessage())
                                    .show();
                    }
                });
    }


    private class MyZaloPayListener implements ZaloPayListener {
        @Override
        public void onPaymentSucceeded(String transactionId, String zpTranstoken) {
            mDialogLoading.dissLoadingDialog();
            Log.d("pbm", "onSuccess: On successful with transactionId: " + transactionId + "- zpTransToken: " + zpTranstoken);
            new SweetAlertDialog(DetailActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Success")
                    .setConfirmButtonBackgroundColor(R.color.main_color)
                    .setContentText("Payment success")
                    .show();
        }
        @Override
        public void onPaymentError(ZaloPayErrorCode errorCode, int paymentErrorCode, String zpTranstoken) {
            mDialogLoading.dissLoadingDialog();
            new SweetAlertDialog(DetailActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setConfirmButtonBackgroundColor(Color.parseColor("#FF0055"))
                    .setContentText("Cannot payment")
                    .show();
            Log.d("pbm", String.format("onPaymentError: payment error with [error: %s, paymentError: %d], zptranstoken: %s", errorCode, paymentErrorCode, zpTranstoken));
        }
    }

    private void checkPaymentSatus(){
        boolean showLoadingTemp = showDialog;
        layoutLoading.setVisibility(View.VISIBLE);
        paymentLayout.setVisibility(View.GONE);
        paidLayout.setVisibility(View.GONE);
        if(showLoadingTemp) mDialogLoading.startLoadingDialog();
        ApiRepository.getInstance().
                setUrl(PAYMENT_URL).
                createRetrofit().
                create(BookingApiRequest.class).
                checkResult("Bearer " + CoexSharedPreference.getInstance().get("token", String.class),new PaymentRequest(bookingNow.getId())).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<BaseResponce<CheckResultResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseResponce<CheckResultResponse> value) {
                        if (value.getCode() == 200) {
                            if(showLoadingTemp){
                                if(value.getMdata().getCode())
                                    new SweetAlertDialog(DetailActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Success")
                                            .setConfirmButtonBackgroundColor(R.color.main_color)
                                            .setContentText("Payment success")
                                            .show();
                                else
                                    new SweetAlertDialog(DetailActivity.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops...")
                                            .setConfirmButtonBackgroundColor(Color.parseColor("#FF0055"))
                                            .setContentText("Cannot payment")
                                            .show();
                                mDialogLoading.dissLoadingDialog();
                            }
                            showPayment(value.getMdata().getCode());
                        } else {
                            new SweetAlertDialog(DetailActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setConfirmButtonBackgroundColor(Color.parseColor("#FF0055"))
                                    .setContentText(value.getMessage())
                                    .show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mDialogLoading.dissLoadingDialog();
                        BaseErrorData err = new BaseErrorData(e);
                        if (err.getMessage() != null)
                            new SweetAlertDialog(DetailActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setConfirmButtonBackgroundColor(Color.parseColor("#FF0055"))
                                    .setContentText(err.getMessage())
                                    .show();
                        else
                            new SweetAlertDialog(DetailActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setConfirmButtonBackgroundColor(Color.parseColor("#FF0055"))
                                    .setContentText(e.getMessage())
                                    .show();
                    }
                });
    }




    private void showPayment(boolean value){
        layoutLoading.setVisibility(View.GONE);
        if(value){
            paymentLayout.setVisibility(View.GONE);
            paidLayout.setVisibility(View.VISIBLE);
        }else{
            paymentLayout.setVisibility(View.VISIBLE);
            paidLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(showDialog)
            showDialog = false;
        else
            checkPaymentSatus();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
