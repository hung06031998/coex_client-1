package com.upit.coex.user.screen.wallet.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.upit.coex.user.R;
import com.upit.coex.user.repository.model.data.coin.ListExchangeCoin;
import com.upit.coex.user.service.logger.L;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryItemAdapterCoin extends RecyclerView.Adapter<HistoryItemAdapterCoin.viewHolder> {
    private List<ListExchangeCoin> dataCoin;
    private Context mContext;

    public HistoryItemAdapterCoin(Context context) {
        this.mContext = context;
    }


    public void setData(List<ListExchangeCoin> data) {
        this.dataCoin = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View walletCardView = inflater.inflate(R.layout.item_history_exchange, parent, false);
        return new viewHolder(walletCardView);
    }

    @Override
    public int getItemCount() {
        return dataCoin == null ? 0 : dataCoin.size();
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.bindView(dataCoin.get(position));
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private ImageView imgMain;
        private TextView txtTIme, txtDate;
        private TextView txtMoney;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imgMain = itemView.findViewById(R.id.item_history_img_main);
            txtTIme = itemView.findViewById(R.id.item_history_txt_time);
            txtMoney = itemView.findViewById(R.id.item_history_txt_money);
            txtDate = itemView.findViewById(R.id.item_history_txt_date);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @SuppressLint("ResourceAsColor")
        public void bindView(ListExchangeCoin item) {
            if (item.getCoin() > 0) {
                imgMain.setImageResource(R.drawable.ic_money_in);
                txtMoney.setTextColor(Color.parseColor("#66E66A"));
            } else {
                imgMain.setImageResource(R.drawable.ic_money_out);
                txtMoney.setTextColor(Color.parseColor("#FF0055"));
            }
            NumberFormat nf = DecimalFormat.getInstance(Locale.ENGLISH);
            DecimalFormat decimalFormatter = (DecimalFormat) nf;
            decimalFormatter.applyPattern("#,###,###.##########");
            txtMoney.setText(decimalFormatter.format(item.getCoin()) + " coin");
            try {
                Instant formatter = Instant.parse(item.getCreateAt());
                SimpleDateFormat outputFormatDate = new SimpleDateFormat("yyyy/MM/dd");
                SimpleDateFormat outputFormatHour = new SimpleDateFormat("HH:mm");
                try {
                    Date date = Date.from(formatter);
                    txtDate.setText("Date : " + outputFormatDate.format(date));
                    txtTIme.setText("Time : " + outputFormatHour.format(date));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                L.d("Ahiuhiu", e.toString());
            }
        }
    }

}
