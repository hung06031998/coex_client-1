package com.upit.coex.user.screen.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.upit.coex.user.R;
import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.profile.ProfileRespone;
import com.upit.coex.user.repository.model.remote.profile.ProfileApiRequest;
import com.upit.coex.user.screen.dialog.DialogLoading;
import com.upit.coex.user.service.fetchandsave.ApiRepository;
import com.upit.coex.user.service.logger.L;
import com.upit.coex.user.service.sharepreference.CoexSharedPreference;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.upit.coex.user.constants.login.LoginConstant.LOGIN_URL;

public class ProfileActivity extends AppCompatActivity {
    private static final int CHANGE_PROFILE = 5478;
    private TextView txtChangePass, txtEditProfile, txtEmail, txtPhoneNumber, txtName;
    private ImageView imgBack;
    private ProfileRespone data;
    private TextView txtTitle;
    private TextView txtDes;
    private DialogLoading mDialogLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        bindView();
        getData();

        txtChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToChangePass = new Intent(ProfileActivity.this, ChangeDataAccountActivity.class);
                moveToChangePass.putExtra("type", 1);
                moveToChangePass.putExtra("account", data);
                startActivity(moveToChangePass);
            }
        });
        txtEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToChangePass = new Intent(ProfileActivity.this, ChangeDataAccountActivity.class);
                moveToChangePass.putExtra("type", 2);
                moveToChangePass.putExtra("account", data);
                startActivityForResult(moveToChangePass, CHANGE_PROFILE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHANGE_PROFILE && resultCode == Activity.RESULT_OK) {
            L.d("ahiuhiu", "OK");
            txtName.setText(data.getStringExtra("name"));
            txtPhoneNumber.setText(data.getStringExtra("phone_number"));
        }
    }

    private void bindView() {
        mDialogLoading = new DialogLoading(this);
        mDialogLoading.startLoadingDialog();
        txtChangePass = findViewById(R.id.activity_profile_txt_change_password);
        txtEditProfile = findViewById(R.id.activity_profile_txt_edit_profile);
        txtEmail = findViewById(R.id.activity_profile_txt_email);
        txtPhoneNumber = findViewById(R.id.activity_profile_txt_phone_number);
        imgBack = findViewById(R.id.activity_profile_btn_back);
        txtName = findViewById(R.id.activity_profile_txt_name);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getData() {
        ApiRepository.getInstance().
                setUrl(LOGIN_URL).
                createRetrofit().
                create(ProfileApiRequest.class).
                getProfile("Bearer " + CoexSharedPreference.getInstance().get("token", String.class)).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new SingleObserver<BaseResponce<ProfileRespone>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseResponce<ProfileRespone> value) {
                        if (value.getCode() == 200) {
                            txtEmail.setText(value.getMdata().getEmail());
                            txtPhoneNumber.setText(value.getMdata().getClient().getPhone());
                            txtName.setText(value.getMdata().getClient().getName());
                            data = value.getMdata();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Fail : " + value.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        mDialogLoading.dissLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(ProfileActivity.this, "Fail : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        mDialogLoading.dissLoadingDialog();
                    }
                });


    }
}
