package com.upit.coex.user.screen.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;

import com.upit.coex.user.R;

public class DialogLoading {
    private Activity activity;
    private Dialog myDialog;
    private boolean isStart = false;

    public DialogLoading(Activity activity) {
        this.activity = activity;
    }

    public void startLoadingDialog() {
        if(!isStart){
            isStart = true;
            myDialog = new Dialog(activity,
                    R.style.Theme_AppCompat_Light_Dialog_Alert);
            myDialog.setContentView(R.layout.dialog_load);
            myDialog.show();
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.setCanceledOnTouchOutside(false);//bam ra ngoai
            myDialog.setCancelable(false);//bam nut back
        }
    }

    public void dissLoadingDialog() {
        if(isStart) {
            isStart = false;
            Log.d("hehe", "dissLoadingDialog");
            if (myDialog != null && myDialog.isShowing()) {
                Log.d("hehe", "dissLoadingDialog != null");
                myDialog.dismiss();
            }
        }
    }

    public boolean checkLoading() {
        return isStart;
    }
}