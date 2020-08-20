package com.upit.coex.user.template.viewhelper;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.upit.coex.user.R;
import com.upit.coex.user.template.datahelper.ItemCalendar;


public class ItemCalendarAdapter extends BaseAdapter {

    private ItemCalendar[] mDatas;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private int mHeight;

    public ItemCalendarAdapter(Context context, ItemCalendar[] datas, int height) {
        this.mDatas = datas;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mHeight = height;
    }

    @Override
    public int getCount() {
        return mDatas.length;
    }

    @Override
    public Object getItem(int i) {
        return mDatas[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.item_grid_calendar, null);
            viewHolder = new ViewHolder();
            viewHolder.textViewItem = (TextView) view.findViewById(R.id.tvItemCalendar);
            viewHolder.imgBackground = (ImageView) view.findViewById(R.id.imgBackground);
            view.setTag(viewHolder);
            if (mHeight > 0) {
//                FrameLayout.LayoutParams flRootLayoutParams = (FrameLayout.LayoutParams) view.findViewById(R.id.flRoot).getLayoutParams();
//                flRootLayoutParams.height = mHeight;
//                view.findViewById(R.id.flRoot).setBackgroundColor(view.getResources().getColor(R.color.colorAccent,null));
//                Log.d("bao.nt", ""+(FrameLayout.LayoutParams) view.findViewById(R.id.flRoot).getLayoutParams());
                view.setMinimumHeight(mHeight);
            }
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Resources resources = (Resources) view.getResources();

        ItemCalendar data = mDatas[i];
        if (data.mDay != "") {
            viewHolder.textViewItem.setText(data.mDay);
            viewHolder.textViewItem.setTextColor(resources.getColor(R.color.black, null));
            viewHolder.imgBackground.setVisibility(View.GONE);
            if (data.isChoose) {
                viewHolder.imgBackground.setVisibility(View.VISIBLE);
                viewHolder.imgBackground.setBackground(view.getResources().getDrawable(R.drawable.ic_ellipse_13, null));
            }
            if (data.isClick) {
                viewHolder.imgBackground.setVisibility(View.VISIBLE);
                viewHolder.imgBackground.setBackground(resources.getDrawable(R.drawable.ic_ellipse_18, null));
                viewHolder.textViewItem.setTextColor(resources.getColor(R.color.white, null));
            }
        }

        return view;
    }

    class ViewHolder {
        TextView textViewItem;
        ImageView imgBackground;
    }
}
