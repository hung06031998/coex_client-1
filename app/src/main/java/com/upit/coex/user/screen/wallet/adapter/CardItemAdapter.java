package com.upit.coex.user.screen.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.upit.coex.user.R;
import com.upit.coex.user.repository.model.data.card.Card;

import java.util.List;

public class CardItemAdapter extends RecyclerView.Adapter<CardItemAdapter.itemViewHolder> {
    private Context mContext;
    private List<Card> mListCard;
    private onClickMenuOption mOnClickMenuOption;
    private getText mGetText;

    public void setmGetText(getText mGetText) {
        this.mGetText = mGetText;
    }

    private int type;

    public CardItemAdapter(Context mContext, List<Card> mListCard, int type) {
        this.mContext = mContext;
        this.mListCard = mListCard;
        this.type = type;
    }

    public void setmOnClickMenuOption(onClickMenuOption mOnClickMenuOption) {
        this.mOnClickMenuOption = mOnClickMenuOption;
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View walletCardView = inflater.inflate(R.layout.item_wallet_card, parent, false);
        itemViewHolder viewHolder = new itemViewHolder(walletCardView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        Card itemCard = mListCard.get(position);
        holder.mTxtCardName.setText(itemCard.getName());
        holder.mTxtAddress.setText(itemCard.getAddress());
        if (type == 2) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mGetText.getCard(mListCard.get(position).getAddress());
                }
            });
        } else {
            holder.mTxtMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    PopupMenu popup = new PopupMenu(mContext, holder.mTxtMenu);
                    //inflating menu from xml resource
                    popup.inflate(R.menu.item_option_menu_card);
                    //adding click listener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.item_option_1:
                                    //handle menu1 click
                                    mOnClickMenuOption.onClickEdit(position);
                                    break;
                                case R.id.item_option_2:
                                    //handle menu2 click
                                    mOnClickMenuOption.onClickDelete(position);
                                    break;
                            }
                            return false;
                        }
                    });
                    popup.show();

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mListCard.size();
    }

    public class itemViewHolder extends RecyclerView.ViewHolder {
        private TextView mTxtAddress;
        private TextView mTxtCardName;
        private TextView mTxtMenu;

        public itemViewHolder(@NonNull View itemView) {
            super(itemView);
            mTxtAddress = itemView.findViewById(R.id.item_wallet_card_txt_address);
            mTxtCardName = itemView.findViewById(R.id.item_wallet_card_txt_card_name);
            mTxtMenu = itemView.findViewById(R.id.item_wallet_card_text_option);
            if (type == 2) {
                mTxtMenu.setVisibility(View.GONE);
            }
        }
    }

    public interface onClickMenuOption {
        void onClickDelete(int pos);

        void onClickEdit(int pos);
    }

    public interface getText {
        void getCard(String address);
    }
}
