package com.upit.coex.user.contract.register;

import com.upit.coex.user.contract.base.BaseInterfaceView;
import com.upit.coex.user.contract.base.BaseInterfaceViewModel;
import com.upit.coex.user.screen.addinfomation.AddInfomationActivity;

public class RegisterContract {
    public interface RegisterInterfaceViewModel extends BaseInterfaceViewModel<AddInfomationActivity>{
        void doRegister(String email, String password, String name, String phone, boolean typeUser);
    }

    public interface RegisterInterfaceView extends BaseInterfaceView{
        void registerSuccess();

        void registerFailed(String message);
    }
}
