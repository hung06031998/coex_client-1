package com.upit.coex.user.contract.wallet;

import com.upit.coex.user.contract.base.BaseInterfaceView;
import com.upit.coex.user.contract.base.BaseInterfaceViewModel;
import com.upit.coex.user.repository.model.data.card.Card;
import com.upit.coex.user.screen.wallet.activity.WalletActivity;

import java.util.List;

public class WalletContract {
    public interface WalletViewModel extends BaseInterfaceViewModel<WalletActivity> {
        void getData();

        void deleteCard(String nane);
    }

    public interface WalletView extends BaseInterfaceView {
        void setCoin(float coin);

        void bindListCard(List<Card> listCard);

        void setFalse();

        void resultDelete(boolean result);
    }
}
