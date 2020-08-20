package com.upit.coex.user.screen.exchange.to.coin;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.upit.coex.user.R;
import com.upit.coex.user.contract.exchangeToCoin.ExchangeToCoinContract;
import com.upit.coex.user.screen.base.activity.BaseActivity;
import com.upit.coex.user.screen.detail.activity.DetailActivity;
import com.upit.coex.user.screen.dialog.DialogLoading;
import com.upit.coex.user.screen.pandr.ActivityPAndR;
import com.upit.coex.user.viewmodel.exchangeToCoin.ExchangeToCoinViewModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ExchangeToCoinActivity extends BaseActivity<ExchangeToCoinViewModel> implements ExchangeToCoinContract.ExchangeToCoinView {
    private TextView mTxtPoint;
    private TextView mTxtCoin;
    private ImageView mBtnBack;
    private EditText mEdtPoint;
    private TextView mEdtCoin;
    private Button mBtnExchangeCoin;
    private Dialog mDialog;
    private DialogLoading mDialogLoading;
    private double mPoint;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_to_coins);
        mViewModal = ViewModelProviders.of(this).get(ExchangeToCoinViewModel.class);
        mViewModal.setView(this);
        mDialogLoading.startLoadingDialog();
        mViewModal.getData();
    }

    @Override
    public void observeLifeCycle() {

    }

    @Override
    public void bindView() {
        mDialogLoading = new DialogLoading(this);
        mTxtCoin = findViewById(R.id.activity_exchange_to_coin_txt_coin);
        mTxtPoint = findViewById(R.id.activity_exchange_to_coin_txt_point);
        mBtnBack = findViewById(R.id.activity_exchange_to_coin_btn_back);
        mEdtCoin = findViewById(R.id.activity_exchange_to_coin_edt_coin);
        mEdtPoint = findViewById(R.id.activity_exchange_to_coin_edt_point);
        mBtnExchangeCoin = findViewById(R.id.activity_exchange_to_coin_btn_exchange);

        mEdtPoint.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    double number = Float.parseFloat(charSequence.toString());
                    if (number > mPoint) {
                        number = mPoint;
                        mEdtPoint.setText(mPoint + "");
                    }
                    NumberFormat nf = DecimalFormat.getInstance(Locale.ENGLISH);
                    DecimalFormat decimalFormatter = (DecimalFormat) nf;
                    decimalFormatter.applyPattern("#,###,###.##########");
                    number /= 20000;
                    mEdtCoin.setText(decimalFormatter.format(number));
                } catch (NumberFormatException e) {
                    mEdtCoin.setText(0 + "");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Intent layoutExchangeIntent = getIntent();
        mPoint = layoutExchangeIntent.getLongExtra("point", 0);
        mTxtPoint.setText(mPoint + " Point");

        mBtnBack.setOnClickListener(view -> {
            finish();
        });
        mBtnExchangeCoin.setOnClickListener(view -> {
            showDialog();
        });
    }

    public void setPoint(long point) {
        Intent moveToWithdraw = new Intent();
        setResult(RESULT_OK, moveToWithdraw);
        finish();
    }

    private void showDialog() {
        mDialog = new Dialog(ExchangeToCoinActivity.this);
        mDialog.setContentView(R.layout.dialog_confirm);
        TextView txtTitleDialog = mDialog.findViewById(R.id.confirm_dialog_txt_title);
        TextView txtDesDialog = mDialog.findViewById(R.id.confirm_dialog_txt_description);

        txtTitleDialog.setText(R.string.activity_exchange_to_coin_til_dialog);
        txtDesDialog.setText(R.string.activity_exchange_to_coin_des_dialog);
        mDialog.findViewById(R.id.confirm_dialog_btn_cancel).setOnClickListener(
                v -> mDialog.dismiss());
        mDialog.findViewById(R.id.confirm_dialog_btn_accept).setOnClickListener(v -> {
            try {
                long point = Integer.parseInt(mEdtPoint.getText().toString());
                mDialogLoading.startLoadingDialog();
                mViewModal.exchangeToCoin(point);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Fail : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            mDialog.hide();
        });
        mDialog.show();
        mDialog.setCanceledOnTouchOutside(true);//bam ra ngoai
        mDialog.setCancelable(true);//bam nut back
    }

    public void sendPoint() {
        Intent mIntent = new Intent(ExchangeToCoinActivity.this, ActivityPAndR.class);
        setResult(RESULT_OK, mIntent);
        finish();
    }

    @Override
    public void setCOin(boolean result, float coin, String message) {
        if (result) {
            mTxtCoin.setText(coin + " Coin");
        } else {
            Toast.makeText(this, "Fail !", Toast.LENGTH_SHORT).show();
        }
        mDialogLoading.dissLoadingDialog();
    }

    @Override
    public void exchangeCoiFromServer(boolean result, float coin, String message) {
        if (result) {
            SweetAlertDialog dialog = new SweetAlertDialog(ExchangeToCoinActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Exchange done !")
                    .setContentText("Your exchange is successful !")
                    .setConfirmButtonBackgroundColor(Color.parseColor("#66E66A"))
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sendPoint();
                        }
                    });
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } else {
            new SweetAlertDialog(ExchangeToCoinActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setConfirmButtonBackgroundColor(Color.parseColor("#FF0055"))
                    .setContentText(message)
                    .show();
        }
    }
}
