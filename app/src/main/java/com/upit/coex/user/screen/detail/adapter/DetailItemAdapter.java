package com.upit.coex.user.screen.detail.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.upit.coex.user.R;
import com.upit.coex.user.repository.model.data.map.Room;

import java.util.List;

public class DetailItemAdapter extends RecyclerView.Adapter<DetailItemAdapter.ViewItemViewHolder> {
    private List<Room> data;
    private LayoutInflater inflater;
    private OnItemClickListener itemClickListener;
    private int type =0;

    public void setType(int type) {
        this.type = type;
    }

    public void setData(List<Room> temp) {
        this.data = temp;
        notifyDataSetChanged();
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_style_room, parent, false);
        return new ViewItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewItemViewHolder holder, int position) {
        holder.bindData(data.get(position));
        if(type == 1){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null || data.isEmpty() ? 0 : data.size();
    }

    public class ViewItemViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private TextView txtDes;
        private TextView txtCost;

        public ViewItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.style_room_txt_title);
            txtDes = itemView.findViewById(R.id.style_room_txt_description);
            txtCost = itemView.findViewById(R.id.style_room_txt_cost);
        }

        public void bindData(Room room) {
            txtCost.setText(room.getPrice() + " VND/1 hour/1 person");
            txtDes.setText(room.getAbout());
            txtTitle.setText(room.getName());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int item);
    }
}
