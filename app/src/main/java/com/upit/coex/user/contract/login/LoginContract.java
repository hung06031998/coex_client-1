package com.upit.coex.user.contract.login;

import com.upit.coex.user.contract.base.BaseInterfaceView;
import com.upit.coex.user.contract.base.BaseInterfaceViewModel;
import com.upit.coex.user.screen.login.activity.LoginActivity;

/**
 * Created by chien.lx on 3/9/2020.
 */

public class LoginContract {

    public interface LoginInterfaceViewModel extends BaseInterfaceViewModel<LoginActivity> {
        void doLogin(String inputPhone, String passWord, String mFirebaseToken);
    }

    public interface LoginInterfaceView extends BaseInterfaceView {
        void loginFailed(String temp);

        void loginSuccess();
    }
}
