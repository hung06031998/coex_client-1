package com.upit.coex.user.contract.profile;

import com.upit.coex.user.contract.base.BaseInterfaceView;
import com.upit.coex.user.contract.base.BaseInterfaceViewModel;
import com.upit.coex.user.screen.profile.ChangeDataAccountActivity;

public class ProfileContract {
    public interface ProfileViewModel extends BaseInterfaceViewModel<ChangeDataAccountActivity> {
        void sendDataChangeInfo(String name, String phoneNumber);

        void sendDataChangePass(String newPass, String oldPass);
    }

    public interface ProfileView extends BaseInterfaceView {
        void checkDOne(boolean result, String name, String phoneNumber, String message);
        void checkDoneChangePass(boolean result, String message);
    }
}
