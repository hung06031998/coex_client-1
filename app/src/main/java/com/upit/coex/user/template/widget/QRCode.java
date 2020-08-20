package com.upit.coex.user.template.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCode extends AppCompatImageView {


    private String mText = "Demo QR code";

    public QRCode(Context context) {
        super(context);
        setView(context, null);
    }

    public QRCode(Context context, AttributeSet attrs) {
        super(context, attrs);
        setView(context, attrs);
    }

    public QRCode(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setView(context, attrs);
    }

    public void setText(String text){
        this.mText = text;
        showQRCode(this.mText);
    }

    void setView(Context context, AttributeSet attrs){
        String text="bao.nt"; // Whatever you need to encode in the QR code
        showQRCode(this.mText);
    }

    private void showQRCode(String text){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,300,300);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            this.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        Log.d("bao.nt", "Vao day roi");
    }
}

