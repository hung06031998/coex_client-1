package com.upit.coex.user.screen.vnpay;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.upit.coex.user.R;
import com.upit.coex.user.repository.model.data.base.BaseErrorData;
import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.booking.PaymentRequest;
import com.upit.coex.user.repository.model.data.booking.VnpayCreatePayment;
import com.upit.coex.user.repository.model.remote.booking.BookingApiRequest;
import com.upit.coex.user.screen.detail.activity.DetailActivity;
import com.upit.coex.user.screen.dialog.DialogLoading;
import com.upit.coex.user.service.fetchandsave.ApiRepository;
import com.upit.coex.user.service.sharepreference.CoexSharedPreference;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.upit.coex.user.constants.register.RegisterConstant.VNPAY_URL;

public class VnpayPaymentActivity extends AppCompatActivity {
    private WebView webView;
    private DialogLoading mDialogLoading;
    private ImageView mbtnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vnpay_payment);
        webView = (WebView) findViewById(R.id.vnpay_webview);
        mbtnBack = (ImageView) findViewById(R.id.activity_vnpay_btn_back_webview);

        mbtnBack.setOnClickListener(view -> {
            finish();
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                if (url.contains("vnpay/return")) // replace xxx to your back url
                {
                    finish();
                }
                else
                {
                    super.onLoadResource(view, url);
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                try {
                    mDialogLoading.startLoadingDialog();
                }catch (Exception e){}
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                try {
                    mDialogLoading.dissLoadingDialog();
                }catch (Exception e){}
                super.onPageFinished(view, url);
            }
        });
        mDialogLoading = new DialogLoading(VnpayPaymentActivity.this);
        openVnpayWeb();
    }

    private void openVnpayWeb(){
        mDialogLoading.startLoadingDialog();
        Intent intent = this.getIntent();
        ApiRepository.getInstance().
                setUrl(VNPAY_URL).
                createRetrofit().
                create(BookingApiRequest.class).
                vnpayCreatePayment("Bearer " + CoexSharedPreference.getInstance().get("token", String.class),new PaymentRequest(intent.getStringExtra("transid"))).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<BaseResponce<VnpayCreatePayment>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(BaseResponce<VnpayCreatePayment> value) {
                        if (value.getCode() == 200) {
                            webView.loadUrl(value.getMdata().getVnpayUrl());
                            mDialogLoading.dissLoadingDialog();
                        } else {
                            new SweetAlertDialog(VnpayPaymentActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                            new SweetAlertDialog(VnpayPaymentActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setConfirmButtonBackgroundColor(Color.parseColor("#FF0055"))
                                    .setContentText(err.getMessage())
                                    .show();
                        else
                            new SweetAlertDialog(VnpayPaymentActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setConfirmButtonBackgroundColor(Color.parseColor("#FF0055"))
                                    .setContentText(e.getMessage())
                                    .show();
                    }
                });
    }
}
