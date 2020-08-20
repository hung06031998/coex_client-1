package com.upit.coex.user.screen.forgetpassword;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.upit.coex.user.R;
import com.upit.coex.user.contract.login.LoginContract;
import com.upit.coex.user.repository.model.data.login.LoginResponce;
import com.upit.coex.user.screen.base.activity.BaseActivity;
import com.upit.coex.user.screen.login.activity.LoginActivity;
import com.upit.coex.user.service.toast.CoexToast;
import com.upit.coex.user.viewmodel.login.LoginActivityViewModel;

import butterknife.ButterKnife;

public class ForgetPasswordActivity extends BaseActivity<LoginActivityViewModel> implements LoginContract.LoginInterfaceView  {

    EditText edtForgotPassEmail;
    Button btnResetPass;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mViewModal = ViewModelProviders.of(this).get(LoginActivityViewModel.class);
        bindView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModal.getSuccessMutableLiveData().observe(this, new Observer<LoginResponce>() {
            @Override
            public void onChanged(LoginResponce loginData) {

            }
        });

        mViewModal.getFailedMutableLiveData().observe(this, s -> {
//            loginFailed();
        });

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
        ButterKnife.bind(this);
        edtForgotPassEmail = findViewById(R.id.edtForgotPassEmail);
        btnResetPass = findViewById(R.id.btnForgotPassword);
        btnBack = findViewById(R.id.btnBackForgotPass);
        //CoexHelper.loadImageFromResource(this,"background",lnBackground,"mydiposal");

        btnResetPass.setOnClickListener(v -> {
            Toast.makeText(this, "CLICK RESET PASS", Toast.LENGTH_LONG).show();
        });

        btnBack.setOnClickListener(v -> {
            this.finish();
        });
    }

    @Override
    public void loginFailed(String temp) {

        CoexToast.makeToast(this, Toast.LENGTH_LONG, mViewModal.getFailedMutableLiveData().getValue() + "");
        // quay lai login
        Intent intent = new Intent(getApplication(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginSuccess() {

        Intent intent = new Intent(getApplication(), ForgetPasswordActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mViewModal.removeDisposal();
        //mViewModal.destroyView();
    }
}
