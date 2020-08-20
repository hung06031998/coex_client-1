package com.upit.coex.user.screen.map.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.upit.coex.user.R;
import com.upit.coex.user.repository.model.data.map.Data;

import java.util.List;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.viewHolder> {
    private Context mContext;
    private List<Data> mData;
    private setOnClick onClick;
    private LayoutInflater inflater;

    public void setOnClick(setOnClick onClick) {
        this.onClick = onClick;
    }

    public void setData(List<Data> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public SearchItemAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_searching, parent, false);
        return new viewHolder(v);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.bindView(mData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClickItem(position);
            }
        });
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private ImageView imgMain;
        private TextView txtName, txtDes, txtDistance;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imgMain = itemView.findViewById(R.id.item_searching_img_main);
            txtDes = itemView.findViewById(R.id.item_schedule_txt_description);
            txtDistance = itemView.findViewById(R.id.item_searching_txt_distance);
            txtName = itemView.findViewById(R.id.item_searching_txt_name_coo);
        }

        public void bindView(Data data) {
            if (data.getPhoto() != null) {
                if (data.getPhoto().size() != 0) {
                    Glide.with(mContext).load(data.getPhoto().get(0)).into(imgMain);
                }
            }
            txtName.setText(data.getName());
            txtDes.setText(data.getAddress());
            txtDistance.setText((double) Math.round(data.getDistance() * 100) / 100 + "km");
        }
    }

    public interface setOnClick {
        void onClickItem(int pos);
    }
}
