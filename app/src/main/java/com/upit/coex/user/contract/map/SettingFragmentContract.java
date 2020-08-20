package com.upit.coex.user.contract.map;

import com.upit.coex.user.contract.base.fragment.BaseInterfaceFragmentView;
import com.upit.coex.user.contract.base.fragment.BaseInterfaceFragmentViewModel;
import com.upit.coex.user.screen.map.fragment.SettingFragment;

public class SettingFragmentContract {
    public interface SettingInterfaceFragmentViewModel extends BaseInterfaceFragmentViewModel<SettingFragment> {
        void logout(String fcmToken);
    }

    public interface SettingInterfaceFragmentView extends BaseInterfaceFragmentView {
        void getResult(boolean result, String message);
    }
}
