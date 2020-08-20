package com.upit.coex.user.screen.wallet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.upit.coex.user.R;
import com.upit.coex.user.contract.wallet.WalletContract;
import com.upit.coex.user.repository.model.data.card.Card;
import com.upit.coex.user.screen.base.activity.BaseActivity;
import com.upit.coex.user.screen.dialog.DialogConfirm;
import com.upit.coex.user.screen.dialog.DialogLoading;
import com.upit.coex.user.screen.wallet.adapter.CardItemAdapter;
import com.upit.coex.user.screen.withdraw.WithdrawActivity;
import com.upit.coex.user.service.logger.L;
import com.upit.coex.user.viewmodel.wallet.ActivityWalletViewModel;

import java.util.List;

public class WalletActivity extends BaseActivity<ActivityWalletViewModel> implements WalletContract.WalletView, CardItemAdapter.onClickMenuOption, DialogConfirm.onClickYes {
    private static final int REQUEST_OK = 1665;
    private static final int REQUEST_CODE_EDIT_CARD = 4455;
    private TextView mTxtCoin;
    private Button mBtnWithDraw;
    private RelativeLayout mBtnAddCard;
    private ImageView mBtnBack;
    private RecyclerView mRecyclerCard;
    private CardItemAdapter mCardItemAdapter;
    private float coinNow;
    private DialogLoading mDialogLoading;
    private TextView btnHistory;
    private List<Card> data;
    private int posNow = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        mRecyclerCard = findViewById(R.id.activity_my_wallet_rcy_card);
        mBtnWithDraw = findViewById(R.id.activity_my_wallet_btn_with_draw);
        btnHistory = findViewById(R.id.activity_my_wallet_btn_history);
        mDialogLoading = new DialogLoading(this);
        mDialogLoading.startLoadingDialog();

        mBtnWithDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalletActivity.this, WithdrawActivity.class);
                L.d("ahiuhiu", coinNow + "");
                intent.putExtra("coinNow", coinNow);
                startActivityForResult(intent, REQUEST_OK);
            }
        });
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalletActivity.this, HistoryExchangeActivity.class);
                intent.putExtra("coinNow", coinNow);
                startActivity(intent);
            }
        });
        mViewModal = ViewModelProviders.of(this).get(ActivityWalletViewModel.class);
        mViewModal.setView(this);
        mViewModal.getData();
        mViewModal.getListCard();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewModal.getListCard();
    }

    @Override
    public void observeLifeCycle() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_OK && resultCode == Activity.RESULT_OK) {
            L.d("ahiuhiu", coinNow + "");
            coinNow = data.getFloatExtra("result", 0);
            L.d("ahiuhiu", coinNow + "");
            setCoin(coinNow);
        }
        if (requestCode == REQUEST_CODE_EDIT_CARD && resultCode == Activity.RESULT_OK) {
            L.d("ahiuhiu", coinNow + "");

        }
    }

    @Override
    public void bindView() {
        mBtnAddCard = findViewById(R.id.activity_my_wallet_btn_add_card);
        mTxtCoin = findViewById(R.id.activity_my_wallet_txt_show_coin);
        mBtnBack = findViewById(R.id.activity_my_wallet_btn_back);
        mBtnBack.setOnClickListener(view -> finish());
        mBtnAddCard.setOnClickListener(view -> {
            Intent intent = new Intent(WalletActivity.this, AddToCardActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void setCoin(float coin) {
        this.coinNow = coin;
        mTxtCoin.setText(coin + " Coin");
        mDialogLoading.dissLoadingDialog();
    }

    @Override
    public void bindListCard(List<Card> listCard) {
        this.data = listCard;
        mCardItemAdapter = new CardItemAdapter(this, listCard,1);
        mCardItemAdapter.setmOnClickMenuOption(this);
        mRecyclerCard.setAdapter(mCardItemAdapter);
    }

    @Override
    public void setFalse() {
        Toast.makeText(this, "False !", Toast.LENGTH_SHORT).show();
        mDialogLoading.dissLoadingDialog();
    }

    @Override
    public void resultDelete(boolean result) {
        mViewModal.getListCard();
        mDialogLoading.dissLoadingDialog();
    }

    @Override
    public void onClickDelete(int pos) {
        posNow = pos;
        DialogConfirm dialogConfirm = new DialogConfirm(this, "Delete card", "Are you sure wanna to delete this card?");
        dialogConfirm.setmOnClickYes(this);
        dialogConfirm.startLoadingDialog();
    }

    @Override
    public void onClickEdit(int pos) {
        Intent moveToEditCard = new Intent(WalletActivity.this, AddToCardActivity.class);
        moveToEditCard.putExtra("type", 2);
        moveToEditCard.putExtra("name", data.get(pos).getName());
        moveToEditCard.putExtra("address", data.get(pos).getAddress());
        startActivityForResult(moveToEditCard, REQUEST_CODE_EDIT_CARD);
    }

    @Override
    public void onYesClick() {
        mDialogLoading.startLoadingDialog();
        mViewModal.deleteCard(data.get(posNow).getName());
    }
}
