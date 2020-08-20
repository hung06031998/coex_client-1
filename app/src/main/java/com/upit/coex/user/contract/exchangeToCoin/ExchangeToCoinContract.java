package com.upit.coex.user.contract.exchangeToCoin;

import com.upit.coex.user.contract.base.BaseInterfaceView;
import com.upit.coex.user.contract.base.BaseInterfaceViewModel;
import com.upit.coex.user.screen.exchange.to.coin.ExchangeToCoinActivity;

public class ExchangeToCoinContract {
    public interface ExchangeToCoinViewModel extends BaseInterfaceViewModel<ExchangeToCoinActivity> {
        void getData();
    }

    public interface ExchangeToCoinView extends BaseInterfaceView {
        void setCOin(boolean result, float coin, String message);
        void exchangeCoiFromServer(boolean result, float coin, String message);
    }
}
