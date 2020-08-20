package com.upit.coex.user.screen.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.upit.coex.user.R;

public class DialogConfirm {
    private Activity activity;
    private AlertDialog dialog;
    private onClickYes mOnClickYes;
    private String title, message;

    public void setmOnClickYes(onClickYes mOnClickYes) {
        this.mOnClickYes = mOnClickYes;
    }

    public DialogConfirm(Activity activity, String title, String mes) {
        this.title = title;
        this.message = mes;
        this.activity = activity;
    }

    public void startLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_confirm, null);
        builder.setView(view);
        builder.setCancelable(false);
        //
        Button btnCancel, btnAccepted;
        TextView txtTitle, txtMessage;

        txtMessage = view.findViewById(R.id.confirm_dialog_txt_description);
        txtTitle = view.findViewById(R.id.confirm_dialog_txt_title);
        btnAccepted = view.findViewById(R.id.confirm_dialog_btn_accept);
        btnCancel = view.findViewById(R.id.confirm_dialog_btn_cancel);

        txtMessage.setText(message);
        txtTitle.setText(title);

        btnAccepted.setOnClickListener(v -> {
            mOnClickYes.onYesClick();
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog = builder.create();
        dialog.show();
    }

    public interface onClickYes {
        void onYesClick();
    }
}