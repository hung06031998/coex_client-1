package com.upit.coex.user.screen.addinfomation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.upit.coex.user.R;
import com.upit.coex.user.contract.register.RegisterContract;
import com.upit.coex.user.screen.base.activity.BaseActivity;
import com.upit.coex.user.screen.dialog.DialogLoading;
import com.upit.coex.user.screen.login.activity.LoginActivity;
import com.upit.coex.user.viewmodel.addinfomation.AddInfomationViewModel;

public class AddInfomationActivity extends BaseActivity<AddInfomationViewModel> implements RegisterContract.RegisterInterfaceView {
    private String email, pass;
    private EditText edtName, edtPhone;
    private Button btnSubmit, btnSkip;
    private DialogLoading mDialogLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_infomation);
        mViewModal = ViewModelProviders.of(this).get(AddInfomationViewModel.class);
        mViewModal.setView(this);
        getData();
        bindView();
    }

    private void getData() {
        Intent getData = getIntent();
        email = getData.getStringExtra("email");
        pass = getData.getStringExtra("pass");
    }

    @Override
    protected void onStart() {
        super.onStart();

        mViewModal.getSuccessMutableLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                registerSuccess();
            }
        });

        mViewModal.getFailedMutableLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                registerFailed(s);
            }
        });

    }

    @Override
    public void registerSuccess() {
        mDialogLoading.dissLoadingDialog();
        Toast.makeText(this, "Register Successful! ", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplication(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void registerFailed(String message) {
        mDialogLoading.dissLoadingDialog();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void observeLifeCycle() {
        mViewModal.getMutableLiveData().observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                // L.d(TAG,TAG_ACTIVITY,);
            }
        });
    }

    @Override
    public void bindView() {
        btnSkip = findViewById(R.id.activity_add_info_btn_skip);
        btnSubmit = findViewById(R.id.activity_add_info_btn_submit);
        edtName = findViewById(R.id.activity_add_info_edt_input_name);
        edtPhone = findViewById(R.id.activity_add_info_edt_input_phone_number);
        mDialogLoading = new DialogLoading(this);
        btnSubmit.setOnClickListener(v -> {
            mDialogLoading.startLoadingDialog();
            mViewModal.doRegister(email, pass, edtName.getText().toString(), edtPhone.getText().toString(), true);
        });
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mViewModal.destroyView();
    }
}
