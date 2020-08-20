//package com.upit.coex.user.service.glide;
//
//import android.app.Activity;
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.graphics.drawable.PictureDrawable;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Environment;
//import android.view.View;
//import android.widget.ImageView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.RequestBuilder;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.request.RequestOptions;
//import com.bumptech.glide.request.target.CustomTarget;
//import com.bumptech.glide.request.transition.Transition;
//import com.upit.coex.user.R;
//
//import java.io.File;
//
//import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
//import static com.upit.coex.user.service.helper.CoexHelper.getImage;
//
//public class CoexGlide {
//
//    private static Activity mActivity;
//    private static String mUrl;
//    private static View mView;
//    private static boolean isSVG = false;
//    private static volatile CoexGlide sInstance = null;
//
//    private CoexGlide(){
//
//    }
//
//    public static CoexGlide getInstance(){
//        if(sInstance == null){
//            synchronized (CoexGlide.class){
//                sInstance = new CoexGlide();
//            }
//        }
//        return sInstance;
//    }
//
//    public CoexGlide setContext(Activity _activity){
//        this.mActivity = _activity;
//        return this;
//    }
//
//    public CoexGlide setUrl(String _url){
//        this.mUrl = _url;
//        return this;
//    }
//
//    public CoexGlide loadIntoView(View _view){
//        this.mView = _view;
//        return this;
//    }
//
//    public static void loadFromUrl() {
//        if(isSVG){
//            Glide.with(mActivity)
//                    .as(PictureDrawable.class)
//                    .transition(withCrossFade())
//                    .fitCenter()
//                    .listener(new SvgSoftwareLayerSetter())
//                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
//                    .load(mUrl).
//                    into((ImageView) mView);
//        } else {
//            Glide.with(mActivity).load(mUrl).into((ImageView) mView);
//        }
//    }
//
//    public static void loadImageFromFile() {
//        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), mUrl);
//        if(isSVG){
//            Glide.with(mActivity)
//                    .as(PictureDrawable.class)
//                    .transition(withCrossFade())
//                    .fitCenter()
//                    .listener(new SvgSoftwareLayerSetter())
//                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
//                    .load(file).
//                    into((ImageView) mView);
//        } else {
//            Glide.with(mActivity).load(file).into((ImageView) mView);
//        }
//    }
//
//    public static void loadImageFromName() {
//        if(isSVG){
//            Glide.with(mActivity)
//                    .as(PictureDrawable.class)
//                    .transition(withCrossFade())
//                    .fitCenter()
//                    .listener(new SvgSoftwareLayerSetter())
//                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
//                    .load(getImage(mActivity, mUrl))
//                    .into(new CustomTarget<PictureDrawable>() {
//                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//                        @Override
//                        public void onResourceReady(@NonNull PictureDrawable resource, @Nullable Transition<? super PictureDrawable> transition) {
//                            mView.setBackground(resource);
//                        }
//
//                        @Override
//                        public void onLoadCleared(@Nullable Drawable placeholder) {
//
//                        }
//                    });
//        } else {
//            Glide.with(mActivity).load(getImage(mActivity, mUrl)).into(new CustomTarget<Drawable>() {
//                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//                @Override
//                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//                    mView.setBackground(resource);
//                }
//
//                @Override
//                public void onLoadCleared(@Nullable Drawable placeholder) {
//
//                }
//            });
//        }
//    }
//}
