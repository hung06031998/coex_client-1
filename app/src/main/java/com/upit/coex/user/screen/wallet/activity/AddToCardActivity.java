package com.upit.coex.user.screen.wallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.upit.coex.user.R;
import com.upit.coex.user.contract.base.BaseInterfaceView;
import com.upit.coex.user.screen.base.activity.BaseActivity;
import com.upit.coex.user.screen.dialog.DialogLoading;
import com.upit.coex.user.viewmodel.wallet.AddToCardViewModel;

public class AddToCardActivity extends BaseActivity<AddToCardViewModel> implements BaseInterfaceView {
    private EditText mEdtAddress;
    private EditText mEdtName;
    private ImageView mBtnBack;
    private Button mBtnAdd;
    private int type = 1;
    private DialogLoading mDialogLoading;
    private TextView txtTitle;
    private String cardName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        mViewModal = ViewModelProviders.of(this).get(AddToCardViewModel.class);
        mViewModal.setView(this);
    }

    @Override
    public void observeLifeCycle() {

    }

    @Override
    public void bindView() {
        mDialogLoading = new DialogLoading(this);
        mEdtName = findViewById(R.id.activity_add_card_edt_name);
        mEdtAddress = findViewById(R.id.activity_add_card_edt_address);
        mBtnAdd = findViewById(R.id.activity_add_card_btn_add);
        mBtnBack = findViewById(R.id.activity_add_card_btn_back);
        txtTitle = findViewById(R.id.activity_add_card_txt_title);
        mBtnBack.setOnClickListener(view -> finish());
        mBtnAdd.setOnClickListener(view -> {
            mDialogLoading.startLoadingDialog();
            if (type == 2) {
                mViewModal.editCard(cardName,mEdtName.getText().toString());
            } else {
                mViewModal.addCard(mEdtName.getText().toString(), mEdtAddress.getText().toString());
            }
        });

        Intent getData = getIntent();
        type = getData.getIntExtra("type", 0);
        if (type == 2) {
            cardName = getData.getStringExtra("name");
            String cardAddress = getData.getStringExtra("address");
            mEdtName.setHint(cardName);
            mEdtAddress.setHint(cardAddress);
            mBtnAdd.setText("Edit card");
            txtTitle.setText("Edit card");
            mEdtAddress.setClickable(false);
        }
    }

    public void onFail() {
        Toast.makeText(this, "Add card fail", Toast.LENGTH_SHORT).show();
        mDialogLoading.dissLoadingDialog();
    }

    public void onSuccess() {
        Toast.makeText(this, "Add card success", Toast.LENGTH_SHORT).show();
        mDialogLoading.dissLoadingDialog();
        finish();
    }

    public void EditCardSuccess() {
        Toast.makeText(this, "Success !", Toast.LENGTH_SHORT).show();
        mDialogLoading.dissLoadingDialog();
        finish();
    }

    public void editCardFail() {
        Toast.makeText(this, "Edit card fail !", Toast.LENGTH_SHORT).show();
        mDialogLoading.dissLoadingDialog();
    }
}
