package com.upit.coex.user.screen.map.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.upit.coex.user.R;
import com.upit.coex.user.repository.model.data.map.CoWokingResponce;
import com.upit.coex.user.repository.model.data.map.Data;

import static com.upit.coex.user.constants.CommonConstants.IMAGE_LINK_BASE;

public class CoexItemAdapter extends RecyclerView.Adapter<CoexItemAdapter.ItemAdapterViewHolder> {
    private CoWokingResponce data;
    private onItemClick itemClick;
    private Context context;

    public CoexItemAdapter(Context context) {
        this.context = context;
    }

    public onItemClick getItemClick() {
        return itemClick;
    }

    public void setItemClick(onItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public void setData(CoWokingResponce data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_coex_detail, null);
        return new ItemAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapterViewHolder holder, int position) {
        holder.bindData(data.getData().get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.itemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.getData().size();
    }

    public class ItemAdapterViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgMain;
        private TextView txtTitle, txtDes;
        private RelativeLayout layoutWifi, layoutDrink, layoutPrinter, layoutAirCon, layoutConCall;
        private TextView txtCost;

        public ItemAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMain = itemView.findViewById(R.id.detail_coex_type_1_image_main);
            txtTitle = itemView.findViewById(R.id.detail_coex_txt_title);
            txtCost = itemView.findViewById(R.id.detail_coex_cost);
            txtDes = itemView.findViewById(R.id.detail_coex_txt_place);
            layoutAirCon = itemView.findViewById(R.id.detail_coex_item_air_conditioning);
            layoutConCall = itemView.findViewById(R.id.detail_coex_item_conversion_call);
            layoutDrink = itemView.findViewById(R.id.detail_coex_item_drink);
            layoutWifi = itemView.findViewById(R.id.detail_coex_item_wifi);
            layoutPrinter = itemView.findViewById(R.id.detail_coex_item_printer);
        }

        public void bindData(Data data) {
            if (data != null) {
                txtTitle.setText(data.getName());
                txtDes.setText(data.getAbout());

                if(data.getRooms() != null && data.getRooms().size() >  0){
                    txtCost.setText(data.getRooms().get(0).getPrice() + " VND/1 hour/1 person");
                }
                if (data.getPhoto() != null && data.getPhoto().size() > 0) {
                    Glide.with(context).load(IMAGE_LINK_BASE + data.getPhoto().get(0)).into(imgMain);
                }
                if (data.getService().getDrink()) {
                    layoutDrink.setVisibility(View.VISIBLE);
                } else {
                    layoutDrink.setVisibility(View.GONE);
                }
                if (data.getService().getAirConditioning()) {
                    layoutAirCon.setVisibility(View.VISIBLE);
                } else {
                    layoutAirCon.setVisibility(View.GONE);
                }
                if (data.getService().getConversionCall()) {
                    layoutConCall.setVisibility(View.VISIBLE);
                } else {
                    layoutConCall.setVisibility(View.GONE);
                }
                if (data.getService().getPrinter()) {
                    layoutPrinter.setVisibility(View.VISIBLE);
                } else {
                    layoutPrinter.setVisibility(View.GONE);
                }
                if (data.getService().getWifi()) {
                    layoutWifi.setVisibility(View.VISIBLE);
                } else {
                    layoutWifi.setVisibility(View.GONE);
                }
            }
        }
    }

    public interface onItemClick {
        void itemClick(int number);
    }
}
