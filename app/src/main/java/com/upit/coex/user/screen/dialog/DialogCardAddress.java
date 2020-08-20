package com.upit.coex.user.screen.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.upit.coex.user.R;
import com.upit.coex.user.repository.model.data.card.Card;
import com.upit.coex.user.screen.wallet.activity.HistoryExchangeActivity;
import com.upit.coex.user.screen.wallet.adapter.CardItemAdapter;
import com.upit.coex.user.screen.wallet.fragment.CoinHistoryFragment;
import com.upit.coex.user.screen.wallet.fragment.PointHistoryFragment;
import com.upit.coex.user.service.logger.L;

import java.util.List;

public class DialogCardAddress implements CardItemAdapter.getText {
    private Activity activity;
    private Context context;
    private AlertDialog dialog;
    private onClickYes2 mOnClickYes;
    private List<Card> data;
    private String card = "";
    private int type = 0;
    private CardItemAdapter adapter;


    public void setmOnClickYes(onClickYes2 mOnClickYes) {
        this.mOnClickYes = mOnClickYes;
    }

    public DialogCardAddress(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void setData(List<Card> data) {
        this.data = data;
    }

    public void startLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_card_address, null);
        builder.setView(view);
        builder.setCancelable(false);
        //
        Button btnAccepted, btnListCard, btnCustomCard;
        RecyclerView rcyData;
        RelativeLayout layoutListCard;
        EditText edtInput;

        btnAccepted = view.findViewById(R.id.dialog_card_address_btn_choose);
        btnCustomCard = view.findViewById(R.id.dialog_card_address_btn_custom);
        btnListCard = view.findViewById(R.id.dialog_card_address_btn_list);
        rcyData = view.findViewById(R.id.dialog_card_address_rcy_main);
        layoutListCard = view.findViewById(R.id.dialog_card_address_layout_custom);
        edtInput = view.findViewById(R.id.dialog_card_address_edt_input);
        adapter = new CardItemAdapter(context, data, 2);
        adapter.setmGetText(this);
        rcyData.setAdapter(adapter);
        layoutListCard.setVisibility(View.GONE);

        btnListCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 0;
                rcyData.setVisibility(View.VISIBLE);
                layoutListCard.setVisibility(View.GONE);
                btnListCard.setBackgroundResource(R.drawable.btn_normal_background);
                btnCustomCard.setBackgroundResource(R.drawable.btn_cancel_button);
                btnCustomCard.setTextColor(Color.parseColor("#FF0055"));
                btnListCard.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });
        btnCustomCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 1;
                rcyData.setVisibility(View.GONE);
                layoutListCard.setVisibility(View.VISIBLE);
                btnListCard.setBackgroundResource(R.drawable.btn_cancel_button);
                btnCustomCard.setBackgroundResource(R.drawable.btn_normal_background);
                btnCustomCard.setTextColor(Color.parseColor("#FFFFFF"));
                btnListCard.setTextColor(Color.parseColor("#FF0055"));
            }
        });

        btnAccepted.setOnClickListener(v -> {
            dialog.dismiss();
            if (type == 0) {
                if (card.equals("")) {
                    Toast.makeText(activity, "Choose card !", Toast.LENGTH_SHORT).show();
                } else {
                    mOnClickYes.onYesClick2(card);
                }
            } else {
                card = edtInput.getText().toString().trim();
                mOnClickYes.onYesClick2(card);
            }
        });

        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void getCard(String address) {
        Log.d("pbm", "getCard: "+address);
        card = address;
        mOnClickYes.onYesClick2(card);
        dialog.dismiss();
    }

    public interface onClickYes2 {
        void onYesClick2(String data);
    }
}
