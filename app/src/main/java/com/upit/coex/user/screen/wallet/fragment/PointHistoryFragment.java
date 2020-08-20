package com.upit.coex.user.screen.wallet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.upit.coex.user.R;
import com.upit.coex.user.repository.model.data.coin.ListExchangePoint;
import com.upit.coex.user.screen.wallet.adapter.HistoryItemAdapterPoint;

import java.util.List;

public class PointHistoryFragment extends Fragment {
    private List<ListExchangePoint> data;
    private View v;
    private HistoryItemAdapterPoint adapterCoin;
    private RecyclerView rcyMain;

    public PointHistoryFragment(List<ListExchangePoint> data) {
        this.data = data;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_history_coin, container, false);
        rcyMain = v.findViewById(R.id.fragment_history_exchange_rcy_coin);
        adapterCoin = new HistoryItemAdapterPoint(getContext());
        adapterCoin.setData(data);
        rcyMain.setAdapter(adapterCoin);
        return v;
    }
}
