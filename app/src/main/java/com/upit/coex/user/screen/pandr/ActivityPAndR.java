package com.upit.coex.user.screen.pandr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.upit.coex.user.R;
import com.upit.coex.user.contract.pandr.PAndRContract;
import com.upit.coex.user.screen.base.activity.BaseActivity;
import com.upit.coex.user.screen.dialog.DialogLoading;
import com.upit.coex.user.screen.exchange.to.coin.ExchangeToCoinActivity;
import com.upit.coex.user.viewmodel.pandr.ActivityPAndRViewModel;

public class ActivityPAndR extends BaseActivity<ActivityPAndRViewModel> implements PAndRContract.PAndRContractView {
    private TextView txtPoint;
    private ImageView btnBack;
    private RelativeLayout layoutExchange;
    private long mPoint;
    private DialogLoading mDialogLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPoint = 0;
        setContentView(R.layout.activity_pand_r);
        mViewModal = ViewModelProviders.of(this).get(ActivityPAndRViewModel.class);
        mViewModal.setView(this);
        mViewModal.getData();
    }

    @Override
    public void observeLifeCycle() {

    }

    @Override
    public void bindView() {
        txtPoint = findViewById(R.id.activity_p_and_r_txt_point);
        btnBack = findViewById(R.id.activity_p_and_r_btn_back);
        layoutExchange = findViewById(R.id.activity_p_and_r_btn_exchange_to_coin);
        mDialogLoading = new DialogLoading(this);
        mDialogLoading.startLoadingDialog();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        layoutExchange.setOnClickListener(v -> {
            if (mPoint != 0) {
                Intent layoutExchangeIntent = new Intent(ActivityPAndR.this, ExchangeToCoinActivity.class);
                layoutExchangeIntent.putExtra("point", mPoint);
                this.startActivityForResult(layoutExchangeIntent, 100);
            } else {
                Toast.makeText(this, "No point", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void setPoint(long point, boolean result) {
        if(result){
            mPoint = point;
            txtPoint.setText(point + " Point");
        }else{
            Toast.makeText(this, "Fail !", Toast.LENGTH_SHORT).show();
        }
        mDialogLoading.dissLoadingDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            Intent moveToWithdraw = new Intent();
            setResult(RESULT_OK, moveToWithdraw);
            finish();
        }
    }
}
