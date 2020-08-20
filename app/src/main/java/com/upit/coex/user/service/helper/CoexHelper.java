package com.upit.coex.user.service.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.upit.coex.user.R;
import com.upit.coex.user.service.compositedisposal.CoexCommonCompositeDisposal;
import com.upit.coex.user.service.executor.CoexExecutor;

import com.upit.coex.user.service.glide.SvgSoftwareLayerSetter;
import com.upit.coex.user.service.logger.L;
import com.upit.coex.user.service.runnable.CoexRunnable;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by chien.lx on 3/9/2020.
 */

public class CoexHelper {

    public static String parseDateToddMMyyyy(String inputPattern, String outputPattern, String time) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        java.util.Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    //from resource to uri
    private static Uri resourceIdToUri(Context context, int resourceId) {
        String ANDROID_RESOURCE = "android.resource://";
        String FOREWARD_SLASH = "/";
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }

    public static void loadImagePNGJPGFromUrl(Activity act, String url, View container) {
        Glide.with(act).load(url).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                container.setBackground(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
    }

    public static void loadImagePNGJPGFromFile(Activity act, String path, View container) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), path);
        Glide.with(act).load(file).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                container.setBackground(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
    }

    public static void loadImagePNGJPGFromName(Activity act, String drawableName, View container) {
        Glide.with(act).load(getImage(act, drawableName)).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                container.setBackground(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
    }

    public static void loadImageSVGromUrl(Activity act, String url, View container) {
        Glide.with(act)
                .as(PictureDrawable.class)
                .transition(withCrossFade())
                .listener(new SvgSoftwareLayerSetter())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
                .load(url).
                into((ImageView) container);
    }

    public static void loadImageSVGFromFile(Activity act, String path, View container) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), path);
        Glide.with(act)
                .as(PictureDrawable.class)
                .transition(withCrossFade())
                .listener(new SvgSoftwareLayerSetter())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
                .load(file).
                into((ImageView) container);
    }

    public static void loadImageSVGFromName(Activity act, String drawableName, View container, String diposalName) {
        CoexRunnable.runFunctionSingle(()->{
            Bitmap newBM = Bitmap.createBitmap(360,
                    360,
                    Bitmap.Config.ARGB_8888);
            try {
                SVG svg = SVG.getFromAsset(act.getAssets(),drawableName+".svg");

                // Create a bitmap and canvas to draw onto
                float svgWidth = (svg.getDocumentWidth() != -1) ? svg.getDocumentWidth() : 500f;
                float svgHeight = (svg.getDocumentHeight() != -1) ? svg.getDocumentHeight() : 500f;


                newBM = Bitmap.createBitmap((int)svgWidth,
                        (int)svgHeight,
                        Bitmap.Config.ARGB_8888);

                Canvas bmcanvas = new Canvas(newBM);

                // Clear background to white if you want
                bmcanvas.drawRGB(255, 255, 255);

                // Render our document onto our canvas
                svg.renderToCanvas(bmcanvas);

                return newBM;

            } catch(SVGParseException e) {

            }catch(IOException e) {

            }
            return newBM;
        })
                .subscribe(new SingleObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                CoexCommonCompositeDisposal.getInstance().addDisposal(diposalName,d);
            }

            @Override
            public void onSuccess(Object value) {
                L.d("hehe","onSuccess",value+"");
                Glide.with(act)
                        .load(value)
                        .into((ImageView)container );
            }

            @Override
            public void onError(Throwable e) {
            }
        });
    }

    public static void loadImageSVGFromNameExecutor(Activity act, String drawableName, View container) {
        CoexExecutor.executeThenPostOnMainThread(()->{
           // L.d("hehe","runnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
            Bitmap newBM = Bitmap.createBitmap(360,
                    360,
                    Bitmap.Config.ARGB_8888);
            try {
                SVG svg = SVG.getFromAsset(act.getAssets(),drawableName+".svg");

                // Create a bitmap and canvas to draw onto
                float svgWidth = (svg.getDocumentWidth() != -1) ? svg.getDocumentWidth() : 500f;
                float svgHeight = (svg.getDocumentHeight() != -1) ? svg.getDocumentHeight() : 500f;


                newBM = Bitmap.createBitmap((int)svgWidth,
                        (int)svgHeight,
                        Bitmap.Config.ARGB_8888);

                Canvas bmcanvas = new Canvas(newBM);

                // Clear background to white if you want
                bmcanvas.drawRGB(255, 255, 255);

                // Render our document onto our canvas
                svg.renderToCanvas(bmcanvas);



            } catch(SVGParseException e) {

            }catch(IOException e) {

            }

        });


    }

    public static void loadFromUri(Activity act, View container){
        String ANDROID_RESOURCE = "file:///android_asset/background.svg";
        Uri imageUri = Uri.fromFile(new File("//android_asset/background.svg"));

        Glide.with(act)
                .as(PictureDrawable.class)
                .listener(new SvgSoftwareLayerSetter())
                .load(imageUri)
                .into((ImageView) container);
    }

    public static int getImage(Context context, String imageName) {
        int drawableResourceId = context.getResources().getIdentifier(imageName, "raw", context.getPackageName());
        return drawableResourceId;
    }

    public static void exampleGetFullDateTime() {
        //        CoexDate m = new CoexDate.BuilderDate().getBuilder().setDateFormat(COMMON_DATE_FORMAT).getFullDateTime();
        //        Date m = new Date.BuilderDate().getBuilder().setDateFormat(COMMON_DATE_FORMAT).getFullTime();
        //        Date m = new Date.BuilderDate().getBuilder().setDateFormat(COMMON_DATE_FORMAT).getDate();
        //        Date m = new Date.BuilderDate().getBuilder().setDateFormat(COMMON_DATE_FORMAT).getMonth();
        //        Date m = new Date.BuilderDate().getBuilder().setDateFormat(COMMON_DATE_FORMAT).getYear();
        //        Date m = new Date.BuilderDate().getBuilder().setDateFormat(COMMON_DATE_FORMAT).getMinimalDayOfFirstWeek();
        //        Date m = new Date.BuilderDate().getBuilder().setDateFormat(COMMON_DATE_FORMAT).getHour();
        //        Date m = new Date.BuilderDate().getBuilder().setDateFormat(COMMON_DATE_FORMAT).getMinute();
    }

    public static String getStringResource(Context context, int id) {
        //int stringResourceId = context.getResources().getIdentifier(name, "string", context.getPackageName());
        return context.getResources().getString(id);
    }

    public static int getDrawableResource(Context context,String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

//    public class BlurTransformation extends BitmapTransformation {
//
//        private RenderScript rs;
//
//        public BlurTransformation(Context context) {
//            //super(context);
//            rs = RenderScript.create(context);
//        }
//
//        @Override
//        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
//            Bitmap blurredBitmap = toTransform.copy(Bitmap.Config.ARGB_8888, true);
//
//            // Allocate memory for Renderscript to work with
//            Allocation input = Allocation.createFromBitmap(rs, blurredBitmap, Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SHARED);
//            Allocation output = Allocation.createTyped(rs, input.getType());
//
//            // Load up an instance of the specific script that we want to use.
//            ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
//            script.setInput(input);
//
//            // Set the blur radius
//            script.setRadius(10);
//
//            // Start the ScriptIntrinisicBlur
//            script.forEach(output);
//
//            // Copy the output to the blurred bitmap
//            output.copyTo(blurredBitmap);
//
//            return blurredBitmap;
//        }
//
//        @Override
//        public void updateDiskCacheKey(MessageDigest messageDigest) {
//            messageDigest.update("blur transformation".getBytes());
//        }
//
//    }
}
