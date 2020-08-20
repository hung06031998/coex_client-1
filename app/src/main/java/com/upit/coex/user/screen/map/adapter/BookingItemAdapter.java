package com.upit.coex.user.screen.map.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.upit.coex.user.R;
import com.upit.coex.user.repository.model.data.booking.BookingInfoRespone;
import com.upit.coex.user.repository.model.data.booking.Coworking;
import com.upit.coex.user.service.logger.L;

import java.util.List;

import static com.upit.coex.user.constants.CommonConstants.IMAGE_LINK_BASE;

public class BookingItemAdapter extends RecyclerView.Adapter<BookingItemAdapter.itemViewHolder> {
    private List<BookingInfoRespone> mDatarespone;
    private Context mContext;
    private LayoutInflater inflater;
    private int day = 0, month = 0;
    private OnItemClick itemClick;

    public void setItemClick(OnItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public void setData(List<BookingInfoRespone> data, int day, int month) {
        this.mDatarespone = data;
        this.day = day;
        this.month = month;
        notifyDataSetChanged();
    }

    public BookingItemAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.item_schedule, parent, false);
        return new itemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        holder.bindData(mDatarespone.get(position), day, month, mDatarespone.get(position).getStatus().equals("cancelled"));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.itemClick(mDatarespone.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatarespone == null ? 0 : mDatarespone.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgMain;
        private TextView txtCooName, txtRoomName, txtDes;
        private TextView txtTranID, txtDate;
        private LinearLayout itemMain;

        public itemViewHolder(@NonNull View v) {
            super(v);
            imgMain = v.findViewById(R.id.item_schedule_img_main);
            txtCooName = v.findViewById(R.id.item_schedule_txt_name_coo);
            txtDate = v.findViewById(R.id.item_schedule_txt_date);
            txtDes = v.findViewById(R.id.item_schedule_txt_description);
            txtTranID = v.findViewById(R.id.item_schedule_txt_booking_refe);
            itemMain = v.findViewById(R.id.item_shcedule_item_main);
        }

        public void bindData(BookingInfoRespone bookingInfoRespone, int day, int month, boolean status) {
            L.d("ahiuhiu", status + "");
            if (status) {
                itemMain.setAlpha(0.3f);
            } else {
                itemMain.setAlpha(1f);
            }
            Coworking coworking = bookingInfoRespone.getCoworking();
            if (coworking.getPhoto() != null && coworking.getPhoto().size() != 0) {
                Glide.with(mContext).load(IMAGE_LINK_BASE + coworking.getPhoto().get(0)).into(imgMain);
            }
            txtCooName.setText(coworking.getName());
            txtTranID.setText("#" + bookingInfoRespone.getBookingReference());
            txtDes.setText(coworking.getAddress());
            txtDate.setText("Time: " + bookingInfoRespone.getStartTimeDate() + "h ~ " +
                    (bookingInfoRespone.getStartTimeDate() + bookingInfoRespone.getDurationDate()) + "h");
        }
    }

    public interface OnItemClick {
        void itemClick(BookingInfoRespone data);
    }
}
