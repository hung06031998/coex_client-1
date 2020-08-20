package com.upit.coex.user.screen.detail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.upit.coex.user.R;
import com.upit.coex.user.service.logger.L;


import java.util.ArrayList;

import static com.upit.coex.user.constants.CommonConstants.IMAGE_LINK_BASE;

public class ListPhotoAdapter extends PagerAdapter {
    Context mContext;
    ArrayList<String> mListPhoto;


    public ListPhotoAdapter(Context mContext, ArrayList<String> mListPhoto) {
        this.mContext = mContext;
        this.mListPhoto = mListPhoto;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ViewGroup layout = (ViewGroup) LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo, container, false);
        ImageView imgPhoto = layout.findViewById(R.id.item_photo_string);

        String photo = mListPhoto.get(position);
        Glide.with(mContext).load(IMAGE_LINK_BASE + photo).into(imgPhoto);
        L.d("ahiuhiu", IMAGE_LINK_BASE + photo);
        container.addView(layout);
        return layout;
    }

    @Override
    public int getCount() {
        return mListPhoto == null ? 0 : mListPhoto.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}