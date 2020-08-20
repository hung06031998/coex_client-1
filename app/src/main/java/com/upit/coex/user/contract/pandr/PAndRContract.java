package com.upit.coex.user.contract.pandr;

import com.upit.coex.user.contract.base.BaseInterfaceView;
import com.upit.coex.user.contract.base.BaseInterfaceViewModel;
import com.upit.coex.user.screen.pandr.ActivityPAndR;

public class PAndRContract {
    public interface PAndRContractViewModel extends BaseInterfaceViewModel<ActivityPAndR> {
        void getData();
    }

    public interface PAndRContractView extends BaseInterfaceView {
        void setPoint(long point, boolean result);
    }
}
