package com.upit.coex.user.screen.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.upit.coex.user.R;
import com.upit.coex.user.contract.login.LoginContract;
import com.upit.coex.user.screen.base.activity.BaseActivity;
import com.upit.coex.user.screen.dialog.DialogLoading;
import com.upit.coex.user.screen.forgetpassword.ForgetPasswordActivity;
import com.upit.coex.user.screen.map.activity.MapActivity;
import com.upit.coex.user.screen.register.RegisterActivity;
import com.upit.coex.user.service.logger.L;
import com.upit.coex.user.service.sharepreference.CoexSharedPreference;
import com.upit.coex.user.service.toast.CoexToast;
import com.upit.coex.user.viewmodel.login.LoginActivityViewModel;

import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity<LoginActivityViewModel> implements LoginContract.LoginInterfaceView {
    private LinearLayout lnBackground;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;
    private TextView txtRegister;
    private TextView txtFogotPass;
    private DialogLoading mDialogLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mViewModal = ViewModelProviders.of(this).get(LoginActivityViewModel.class);
        mViewModal.setView(this);
        bindView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(LoginActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                L.d("fcmToken", newToken);
                CoexSharedPreference.getInstance().put("fcmToken", newToken);
            }
        });

        mViewModal.getMutableLiveData().observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {

            }
        });

        mViewModal.getSuccessMutableLiveData().observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                Intent move = new Intent(LoginActivity.this, MapActivity.class);
                startActivity(move);
                finish();
            }
        });


        mViewModal.getFailedMutableLiveData().observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                CoexToast.makeToast(LoginActivity.this, Toast.LENGTH_LONG, o.toString());
                mDialogLoading.dissLoadingDialog();
            }
        });

    }

    @Override
    public void observeLifeCycle() {
        mViewModal.getMutableLiveData().observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {

            }
        });
    }


    @Override
    public void bindView() {
        ButterKnife.bind(this);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);
        txtFogotPass = findViewById(R.id.txtForgotPassword);
        mDialogLoading = new DialogLoading(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogLoading.startLoadingDialog();
                mViewModal.doLogin(edtEmail.getText().toString(), edtPassword.getText().toString(), CoexSharedPreference.getInstance().get("fcmToken", String.class));
            }
        });

        txtRegister.setOnClickListener(v -> {
            Intent registerIntent = new Intent(this, RegisterActivity.class);
            startActivity(registerIntent);
        });

        txtFogotPass.setOnClickListener(v -> {
            Intent forgotIntent = new Intent(this, ForgetPasswordActivity.class);
            startActivity(forgotIntent);
        });

    }

    @Override
    public void loginFailed(String temp) {
//        BaseErrorData errorData = new BaseErrorData(temp);
        Toast.makeText(this, "Login fail", Toast.LENGTH_LONG).show();
        mDialogLoading.dissLoadingDialog();
    }

    @Override
    public void loginSuccess() {
        mDialogLoading.dissLoadingDialog();
        Intent move = new Intent(LoginActivity.this, MapActivity.class);
        startActivity(move);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
