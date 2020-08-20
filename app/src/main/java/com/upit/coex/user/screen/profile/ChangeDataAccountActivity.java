package com.upit.coex.user.screen.profile;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.upit.coex.user.R;
import com.upit.coex.user.contract.profile.ProfileContract;
import com.upit.coex.user.repository.model.data.profile.ProfileRespone;
import com.upit.coex.user.screen.base.activity.BaseActivity;
import com.upit.coex.user.screen.detail.activity.DetailActivity;
import com.upit.coex.user.screen.dialog.DialogLoading;
import com.upit.coex.user.screen.withdraw.WithdrawActivity;
import com.upit.coex.user.service.logger.L;
import com.upit.coex.user.viewmodel.profile.ChangeDataAccountViewModel;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ChangeDataAccountActivity extends BaseActivity<ChangeDataAccountViewModel> implements ProfileContract.ProfileView {
    private TextView txtTitle, txtTitle1, txtTitle2, txtTitle3;
    private EditText edtInput1, edtInput2, edtInput3;
    private Button btnSubmit;
    private ImageView imgBack;
    private int type;
    private TextView txtTitleDialog, txtDesDialog;
    private ProfileRespone data = new ProfileRespone();
    private Dialog myDialog2;
    private DialogLoading mDialogLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mViewModal = ViewModelProviders.of(this).get(ChangeDataAccountViewModel.class);
        mViewModal.setView(this);
        getData();
    }

    private void getData() {
        Intent getData = getIntent();
        type = getData.getIntExtra("type", 0);
        data = (ProfileRespone) getData.getSerializableExtra("account");
        L.d("ahiuhiu", type + " : " + data.getId());
        setTitle();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == 1) {
                    if (edtInput1.getText().toString().equals("") || edtInput2.getText().toString().equals("") || edtInput3.getText().toString().equals("")) {
                        Toast.makeText(ChangeDataAccountActivity.this, "Add field !", Toast.LENGTH_SHORT).show();
                    } else {
                        if (edtInput2.getText().toString().equals(edtInput3.getText().toString())) {
                            showDialog();
                        } else {
                            Toast.makeText(ChangeDataAccountActivity.this, "Check pass", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    if (edtInput1.getText().toString().equals("") || edtInput2.getText().toString().equals("")) {
                        Toast.makeText(ChangeDataAccountActivity.this, "Add field !", Toast.LENGTH_SHORT).show();
                    } else {
                        showDialog();
                    }
                }
            }
        });
    }

    private void setTitle() {
        if (type == 1) {
            txtTitle.setText(R.string.change_data_account_type_1_txt_title);
            txtTitle1.setText(R.string.change_data_account_type_1_txt_title_2);
            edtInput1.setHint("Old password");
            edtInput2.setHint("Password");
            txtTitle2.setText(R.string.change_data_account_type_1_txt_title_3);
            btnSubmit.setText(R.string.change_data_account_type_1_btn_title);
        } else if (type == 2) {
            txtTitle.setText(R.string.change_data_account_type_2_txt_title);
            txtTitle1.setText(R.string.change_data_account_type_2_txt_title_2);
            txtTitle2.setText(R.string.change_data_account_type_2_txt_title_3);
            btnSubmit.setText(R.string.change_data_account_type_2_btn_title);
            edtInput2.setInputType(InputType.TYPE_CLASS_NUMBER);
            txtTitle3.setVisibility(View.GONE);
            edtInput3.setVisibility(View.GONE);
        }
    }

    @Override
    public void observeLifeCycle() {

    }

    private void showDialog() {
        myDialog2 = new Dialog(ChangeDataAccountActivity.this);
        myDialog2.setContentView(R.layout.dialog_confirm);
        txtTitleDialog = myDialog2.findViewById(R.id.confirm_dialog_txt_title);
        txtDesDialog = myDialog2.findViewById(R.id.confirm_dialog_txt_description);

        if (type == 1) {
            txtDesDialog.setText(R.string.activity_change_data_account_dialog_txt_des);
            txtTitleDialog.setText(R.string.activity_change_data_account_dialog_txt_title_type_1);
        } else {
            txtDesDialog.setText(R.string.activity_change_data_account_dialog_txt_des);
            txtTitleDialog.setText(R.string.activity_change_data_account_dialog_txt_title_type_2);
        }

        myDialog2.findViewById(R.id.confirm_dialog_btn_cancel).setOnClickListener(
                v -> myDialog2.dismiss());
        myDialog2.findViewById(R.id.confirm_dialog_btn_accept).setOnClickListener(v -> {
            if (type == 1) {
                mDialogLoading.startLoadingDialog();
                mViewModal.sendDataChangePass(edtInput2.getText().toString(), edtInput1.getText().toString());
            } else {
                mDialogLoading.startLoadingDialog();
                mViewModal.sendDataChangeInfo(edtInput1.getText().toString(), edtInput2.getText().toString());
            }
            myDialog2.cancel();
        });
        myDialog2.show();
        myDialog2.setCanceledOnTouchOutside(false);//bam ra ngoai
        myDialog2.setCancelable(false);//bam nut back
    }

    @Override
    public void bindView() {
        txtTitle = findViewById(R.id.activity_edit_profile_txt_title);
        txtTitle1 = findViewById(R.id.activity_edit_profile_txt_1);
        txtTitle2 = findViewById(R.id.activity_edit_profile_txt_title_2);
        btnSubmit = findViewById(R.id.activity_edit_profile_btn_submit);
        edtInput1 = findViewById(R.id.activity_edit_profile_edt_1);
        edtInput2 = findViewById(R.id.activity_edit_profile_edt_2);
        edtInput3 = findViewById(R.id.activity_edit_profile_edt_3);
        txtTitle3 = findViewById(R.id.activity_edit_profile_txt_title_3);
        imgBack = findViewById(R.id.activity_edit_profile_btn_back);
        mDialogLoading = new DialogLoading(this);
    }

    @Override
    public void checkDOne(boolean result, String name, String phoneNumber, String message) {
        if (result) {
            SweetAlertDialog dialog = new SweetAlertDialog(ChangeDataAccountActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Change done")
                    .setContentText("Edit your profile done !")
                    .setConfirmButtonBackgroundColor(Color.parseColor("#66E66A"))
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent returnData = new Intent();
                            setResult(Activity.RESULT_OK, returnData);
                            returnData.putExtra("name", name);
                            returnData.putExtra("phone_number", phoneNumber);
                            finish();
                        }
                    });
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } else {
            new SweetAlertDialog(ChangeDataAccountActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setConfirmButtonBackgroundColor(Color.parseColor("#FF0055"))
                    .setContentText(message)
                    .show();
        }
        mDialogLoading.dissLoadingDialog();
    }

    @Override
    public void checkDoneChangePass(boolean result, String message) {
        if (result) {
            SweetAlertDialog dialog = new SweetAlertDialog(ChangeDataAccountActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Change done")
                    .setContentText("Your password has change !")
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
            new SweetAlertDialog(ChangeDataAccountActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setConfirmButtonBackgroundColor(Color.parseColor("#FF0055"))
                    .setContentText(message)
                    .show();
        }
        mDialogLoading.dissLoadingDialog();
    }
}
