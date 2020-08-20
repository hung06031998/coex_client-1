package com.upit.coex.user.screen.map.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.upit.coex.user.R;
import com.upit.coex.user.contract.map.MapContract;
import com.upit.coex.user.repository.model.data.base.BaseErrorData;
import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.logout.LogoutRequest;
import com.upit.coex.user.repository.model.data.logout.LogoutRespone;
import com.upit.coex.user.repository.model.data.profile.ProfileRespone;
import com.upit.coex.user.repository.model.remote.logout.LogoutApiRequest;
import com.upit.coex.user.repository.model.remote.profile.ProfileApiRequest;
import com.upit.coex.user.screen.base.activity.BaseActivity;
import com.upit.coex.user.screen.dialog.DialogConfirm;
import com.upit.coex.user.screen.login.activity.LoginActivity;
import com.upit.coex.user.screen.map.fragment.HomeFragment;
import com.upit.coex.user.screen.map.fragment.ScheduleFragment;
import com.upit.coex.user.screen.map.fragment.SettingFragment;
import com.upit.coex.user.service.fetchandsave.ApiRepository;
import com.upit.coex.user.service.logger.L;
import com.upit.coex.user.service.rxjavasenddata.CoexSendData;
import com.upit.coex.user.service.sharepreference.CoexSharedPreference;
import com.upit.coex.user.viewmodel.map.MapActivityViewModel;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.upit.coex.user.constants.login.LoginConstant.LOGIN_URL;
import static com.upit.coex.user.constants.map.MapConstant.mListFragment;

public class MapActivity extends BaseActivity<MapActivityViewModel> implements MapContract.MapView, DialogConfirm.onClickYes {

    private BottomNavigationView mBottomNavigationView;
    private Fragment fragmentSchedule, fragmentSetting;
    private HomeFragment fragmentHome;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        checkToken();

        CoexSendData.getInstance().receive().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Pair<String, Object>>() {
                    @Override
                    public void accept(Pair<String, Object> stringObjectPair) throws Exception {
                        if (stringObjectPair.first!=null && stringObjectPair.first.equals("bao.nt")) {
                            if(stringObjectPair.second != null)
                                mBottomNavigationView.setSelectedItemId((Integer) stringObjectPair.second);
                        }
                    }
                });
    }

    private void intiData() {
        fragmentHome = new HomeFragment();
        fragmentHome.setData(this);
        mListFragment.add(R.id.nav_main_home);

        L.d("bao.nt", R.id.nav_main_home + "");

        fragmentSchedule = new ScheduleFragment();
        mListFragment.add(R.id.nav_main_schedule);

        fragmentSetting = new SettingFragment();
        mListFragment.add(R.id.nav_main_setting);

        mViewModal = ViewModelProviders.of(this).get(MapActivityViewModel.class);
        mViewModal.setView(this);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.main_navigation);
        loadFragment(fragmentHome);
    }

    private void checkToken() {
        CoexSharedPreference.getInstance().put("id_tran", "");
        String FCMtoken = CoexSharedPreference.getInstance().get("fcmToken", String.class).toString();
        L.d("fcmToken", FCMtoken);
        String token = CoexSharedPreference.getInstance().get("token", String.class).toString();
        if (token != "") {
            L.d("ahiuhiu", "token " + CoexSharedPreference.getInstance().get("token", String.class).toString());
            intiData();
            checkTypeUser();
        } else {
            Intent moveToLogin = new Intent(MapActivity.this, LoginActivity.class);
            startActivity(moveToLogin);
            finish();
        }
    }

    private void checkTypeUser() {
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
                        if (value.getMdata().getTypeUser()) {
                            CoexSharedPreference.getInstance().put("token", "");
                            logoutAPI();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        BaseErrorData err = new BaseErrorData(e);
                        if (err.getMessage() != null) {
                            CoexSharedPreference.getInstance().put("token", "");
                            Toast.makeText(MapActivity.this, "Het phien dang nhap", Toast.LENGTH_SHORT).show();
                            Intent moveToLogin = new Intent(MapActivity.this, LoginActivity.class);
                            MapActivity.this.startActivity(moveToLogin);
                            finish();
                        } else {
                            Toast.makeText(MapActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void logoutAPI() {
        ApiRepository.getInstance().
                setUrl(LOGIN_URL).createRetrofit().
                create(LogoutApiRequest.class).
                doLogout("Bearer " + CoexSharedPreference.getInstance().get("token", String.class), new LogoutRequest(CoexSharedPreference.getInstance().get("fcmToken", String.class))).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new SingleObserver<BaseResponce<LogoutRespone>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseResponce<LogoutRespone> value) {
                        if (value.getCode() == 200) {
                            Toast.makeText(MapActivity.this, "Wrong type account", Toast.LENGTH_SHORT).show();
                            Intent moveToLogin = new Intent(MapActivity.this, LoginActivity.class);
                            MapActivity.this.startActivity(moveToLogin);
                            finish();
                        } else {
                            L.d("ahiuhiu", " logoutAPI success");
                            Toast.makeText(MapActivity.this, "Fail !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        L.d("ahiuhiu", " logoutAPI error");
                        Toast.makeText(MapActivity.this, "Fail !", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void observeLifeCycle() {

    }

    @Override
    public void onBackPressed() {
        DialogConfirm mDialogCOnfirm = new DialogConfirm(this, "Quit", "Are you sure wanna to quit");
        mDialogCOnfirm.setmOnClickYes(this);
        mDialogCOnfirm.startLoadingDialog();
    }

    public void bindView() {
        mBottomNavigationView = findViewById(R.id.main_navigation);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_main_home:
                        loadFragment(fragmentHome);
                        return true;
                    case R.id.nav_main_schedule:
                        loadFragment(fragmentSchedule);
                        return true;
                    case R.id.nav_main_setting:
                        loadFragment(fragmentSetting);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        fragmentHome.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onYesClick() {
        finish();
    }
}
