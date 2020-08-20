package com.upit.coex.user.screen.withdraw;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.upit.coex.user.R;
import com.upit.coex.user.repository.model.data.base.BaseErrorData;
import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.base.BaseResponceList;
import com.upit.coex.user.repository.model.data.card.Card;
import com.upit.coex.user.repository.model.data.coin.WithdrawRequest;
import com.upit.coex.user.repository.model.data.coin.WithdrawRespone;
import com.upit.coex.user.repository.model.remote.card.GetListCardApiRequest;
import com.upit.coex.user.repository.model.remote.withdraw.WithdrawCoinApiRequest;
import com.upit.coex.user.screen.detail.activity.DetailActivity;
import com.upit.coex.user.screen.dialog.DialogCardAddress;
import com.upit.coex.user.screen.dialog.DialogConfirm;
import com.upit.coex.user.screen.dialog.DialogLoading;
import com.upit.coex.user.service.fetchandsave.ApiRepository;
import com.upit.coex.user.service.logger.L;
import com.upit.coex.user.service.sharepreference.CoexSharedPreference;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.upit.coex.user.constants.CommonConstants.BASE_URL;
import static com.upit.coex.user.constants.register.RegisterConstant.POINT_URL;

public class WithdrawActivity extends AppCompatActivity implements DialogConfirm.onClickYes, DialogCardAddress.onClickYes2 {
    private List<Card> listData;
    private TextView txtCoin, txtETH;
    private EditText edtInputCoin;
    private RelativeLayout btnWithdraw;
    private TextView spnList;
    private ImageView imgBack;
    private DialogLoading loadingDialog;
    private float coinNow;
    private int now;
    private DialogConfirm dialogConfirm;
    private DialogCardAddress dialogCardAddress;
    private List<String> listAddress = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        initData();
        getData();
    }

    private void initData() {
        loadingDialog = new DialogLoading(this);
        loadingDialog.startLoadingDialog();
        btnWithdraw = findViewById(R.id.activity_withdraw_btn_add_card);
        spnList = findViewById(R.id.nice_spinner);
        edtInputCoin = findViewById(R.id.activity_withdraw_edt_coin);
        txtCoin = findViewById(R.id.activity_withdraw_txt_show_coin);
        txtETH = findViewById(R.id.activity_withdraw_txt_eth);
        imgBack = findViewById(R.id.activity_withdraw_btn_back);
        dialogConfirm = new DialogConfirm(this, "Withdraw", "Are you sure want to witdraw to your wallet");
        dialogConfirm.setmOnClickYes(WithdrawActivity.this);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        spnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCardAddress.startLoadingDialog();
            }
        });
        btnWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtInputCoin.getText().toString().equals("")) {
                    Toast.makeText(WithdrawActivity.this, "Input coin !", Toast.LENGTH_SHORT).show();
                } else {
                    dialogConfirm.startLoadingDialog();
                }
            }
        });
        edtInputCoin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtETH.setText(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void getData() {
        Intent intent = getIntent();
        coinNow = intent.getFloatExtra("coinNow", 1);
        txtCoin.setText(coinNow + " Coin");
        List<String> list = new ArrayList<>();

        ApiRepository.getInstance().setUrl(BASE_URL)
                .createRetrofit().
                create(GetListCardApiRequest.class).
                getListCard("Bearer " + CoexSharedPreference.getInstance().get("token", String.class)).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new SingleObserver<BaseResponceList<Card>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseResponceList<Card> value) {
                        if (value.getCode() == 200) {
                            listData = value.getMdata();
                            for (int i = 0; i < listData.size(); i++) {
                                list.add(listData.get(i).getName());
                                listAddress.add(listData.get(i).getAddress());
                            }
                            dialogCardAddress = new DialogCardAddress(WithdrawActivity.this, WithdrawActivity.this);
                            dialogCardAddress.setData(listData);
                            dialogCardAddress.setmOnClickYes(WithdrawActivity.this);
                            now = 0;
                        } else {
                            Toast.makeText(WithdrawActivity.this, "Fail : " + value.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        loadingDialog.dissLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        BaseErrorData err = new BaseErrorData(e);
                        if (err.getMessage() != null) {
                            L.d("ahiuhiu", err.getMessage());
                            Toast.makeText(WithdrawActivity.this, "Fail : " + err.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            L.d("ahiuhiu", e.getMessage());
                            Toast.makeText(WithdrawActivity.this, "Fail : " + err.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        loadingDialog.dissLoadingDialog();
                    }
                });
    }

    @Override
    public void onYesClick() {
        if (spnList.getText().toString().equals("")) {
            Toast.makeText(this, "Choose address first", Toast.LENGTH_SHORT).show();
        } else {
            loadingDialog.startLoadingDialog();
            WithdrawRequest mWithdrawRequest = new WithdrawRequest();
            mWithdrawRequest.setCoin(Double.parseDouble(edtInputCoin.getText().toString()));
            mWithdrawRequest.setAddress(spnList.getText().toString());

            ApiRepository.getInstance().setUrl(POINT_URL)
                    .createRetrofit().
                    create(WithdrawCoinApiRequest.class).
                    withdrawCoin("Bearer " + CoexSharedPreference.getInstance().get("token", String.class), mWithdrawRequest).
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(new SingleObserver<BaseResponce<WithdrawRespone>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(BaseResponce<WithdrawRespone> value) {
                            if (value.getCode() == 200) {
                                coinNow = coinNow - Float.parseFloat(edtInputCoin.getText().toString());
                                txtCoin.setText(coinNow + " Coin");
                                SweetAlertDialog dialog = new SweetAlertDialog(WithdrawActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Withdraw done !")
                                        .setContentText("Your withdraw was successful !")
                                        .setConfirmButtonBackgroundColor(Color.parseColor("#66E66A"));
                                dialog.show();
                            } else {
                                L.d("ahiuhiu", value.getMessage());
                                new SweetAlertDialog(WithdrawActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setConfirmButtonBackgroundColor(Color.parseColor("#FF0055"))
                                        .setContentText(value.getMessage())
                                        .show();
                            }
                            loadingDialog.dissLoadingDialog();
                        }

                        @Override
                        public void onError(Throwable e) {
                            BaseErrorData err = new BaseErrorData(e);
                            if (err.getMessage() != null) {
                                L.d("ahiuhiu", err.getMessage());
                                new SweetAlertDialog(WithdrawActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setConfirmButtonBackgroundColor(Color.parseColor("#FF0055"))
                                        .setContentText(err.getMessage())
                                        .show();
                            } else {
                                L.d("ahiuhiu", e.getMessage());
                                new SweetAlertDialog(WithdrawActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setConfirmButtonBackgroundColor(Color.parseColor("#FF0055"))
                                        .setContentText(e.getMessage())
                                        .show();
                            }
                            loadingDialog.dissLoadingDialog();
                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("result", coinNow);
        setResult(Activity.RESULT_OK, resultIntent);
        super.onBackPressed();
    }

    @Override
    public void onYesClick2(String data) {
        spnList.setText(data);
    }
}
