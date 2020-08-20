package com.upit.coex.user.screen.detail.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.upit.coex.user.R;
import com.upit.coex.user.repository.model.data.map.Data;
import com.upit.coex.user.repository.model.data.map.Room;
import com.upit.coex.user.screen.booking.BookingActivity;
import com.upit.coex.user.screen.detail.adapter.DetailItemAdapter;
import com.upit.coex.user.screen.detail.adapter.ListPhotoAdapter;
import com.upit.coex.user.service.logger.L;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class DetailCOEXActivity extends AppCompatActivity implements DetailItemAdapter.OnItemClickListener {
    private static final String TAG = "DetailCOEXActivity";
    private static final int REQUEST_DATA_BOOKING = 1254;
    private TextView txtTitle;
    private TextView txtDescription;
    private TextView txtPlace;
    private TextView txtShowMore, txtDistance;
    private RelativeLayout layoutWifi, layoutDrink, layoutPrinter, layoutAirCon, layoutConCall;
    private RecyclerView rcyRoom;
    private Button btnSubmit;
    private ImageView imgBack;
    private DetailItemAdapter adapter;
    private Data data;
    private ViewPager mViewPaper;
    private CircleIndicator indicator;
    private Dialog myDialog;

    public void bindView() {
        txtTitle = findViewById(R.id.activity_detail_coex_txt_title);
        txtDescription = findViewById(R.id.activity_detail_coex_txt_description);
        txtPlace = findViewById(R.id.activity_detail_coex_txt_place);
        txtShowMore = findViewById(R.id.activity_detail_txt_show_more);
        layoutAirCon = findViewById(R.id.activity_detail_coex_item_air_conditioning);
        layoutConCall = findViewById(R.id.activity_detail_coex_item_conversion_call);
        layoutDrink = findViewById(R.id.activity_detail_coex_item_drink);
        layoutPrinter = findViewById(R.id.activity_detail_coex_item_printer);
        layoutWifi = findViewById(R.id.activity_detail_coex_item_wifi);
        rcyRoom = findViewById(R.id.activity_detail_coex_recycview_style_room);
        btnSubmit = findViewById(R.id.activity_detail_coex_btn_confirm_booking);
        imgBack = findViewById(R.id.activity_detail_coex_btn_back);
        indicator = findViewById(R.id.activity_detail_coex_indicator);
        mViewPaper = findViewById(R.id.activity_detail_coex_view_paper);
        txtDistance = findViewById(R.id.activity_detail_coex_txt_distance);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_coex);

        bindView();
        data = (Data) getIntent().getSerializableExtra("key");
        bindData(data);
        initWindow();
        myDialog.dismiss();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.getRooms() != null) {
                    if (data.getRooms().size() > 0) {
                        Intent moveData = new Intent(DetailCOEXActivity.this, BookingActivity.class);
                        moveData.putExtra("type", 1);
                        moveData.putExtra("key", data);
                        startActivityForResult(moveData, REQUEST_DATA_BOOKING);
                    }
                }
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txtShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.show();
            }
        });
    }

    private void initWindow() {
        myDialog = new Dialog(this);
        LayoutInflater inflater = getLayoutInflater();
        View v2 = inflater.inflate(R.layout.dialog_view_more, null);
        myDialog.setContentView(v2);
        myDialog.findViewById(R.id.dialog_view_more_btn_close).setOnClickListener(
                v -> myDialog.dismiss());

        RelativeLayout layoutWifi2, layoutDrink2, layoutPrinter2, layoutConCall2, layoutAirCon2;
        TextView txtOrther;
        layoutWifi2 = v2.findViewById(R.id.dialog_show_more_item_wifi);
        layoutConCall2 = v2.findViewById(R.id.dialog_show_more_item_wifi);
        layoutAirCon2 = v2.findViewById(R.id.dialog_show_more_item_wifi);
        layoutDrink2 = v2.findViewById(R.id.dialog_show_more_item_wifi);
        layoutPrinter2 = v2.findViewById(R.id.dialog_show_more_item_wifi);
        txtOrther = v2.findViewById(R.id.dialog_show_more_txt_orther);
        if (data.getService().getDrink()) {
            layoutDrink2.setVisibility(View.VISIBLE);
        } else {
            layoutDrink2.setVisibility(View.GONE);
        }
        if (data.getService().getAirConditioning()) {
            layoutAirCon2.setVisibility(View.VISIBLE);
        } else {
            layoutAirCon2.setVisibility(View.GONE);
        }
        if (data.getService().getConversionCall()) {
            layoutConCall2.setVisibility(View.VISIBLE);
        } else {
            layoutConCall2.setVisibility(View.GONE);
        }
        if (data.getService().getPrinter()) {
            layoutPrinter2.setVisibility(View.VISIBLE);
        } else {
            layoutPrinter2.setVisibility(View.GONE);
        }
        if (data.getService().getWifi()) {
            layoutWifi2.setVisibility(View.VISIBLE);
        } else {
            layoutWifi2.setVisibility(View.GONE);
        }
        for (int i = 0; i < data.getService().getOther().size(); i++) {
            txtOrther.setText(data.getService().getOther().get(i).toString() + "\n");
        }

        myDialog.setCanceledOnTouchOutside(false);
        myDialog.setCancelable(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_DATA_BOOKING && resultCode == Activity.RESULT_OK) {
            String result = data.getStringExtra("result");
            Calendar calenderTemp = (Calendar) data.getSerializableExtra("date");
            Intent resultIntent = new Intent();
            resultIntent.putExtra("result", result);
            resultIntent.putExtra("date", calenderTemp);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    }

    private void bindData(Data data) {
        this.data = data;
        txtTitle.setText(data.getName());
        txtPlace.setText(data.getAddress());
        txtDistance.setText((double) Math.round(data.getDistance() * 100) / 100 + "km");
        txtDescription.setText(data.getAbout());
        if (data.getPhoto().size() != 0) {
            L.d("ahiuhiu", data.getPhoto().size() + "");
            ArrayList<String> listImage = (ArrayList<String>) data.getPhoto();
            ListPhotoAdapter adapter = new ListPhotoAdapter(this, listImage);
            mViewPaper.setAdapter(adapter);
            indicator.setViewPager(mViewPaper);
            adapter.registerDataSetObserver(indicator.getDataSetObserver());
        }
        if (data.getService().getDrink()) {
            layoutDrink.setVisibility(View.VISIBLE);
        } else {
            layoutDrink.setVisibility(View.GONE);
        }
        if (data.getService().getAirConditioning()) {
            layoutAirCon.setVisibility(View.VISIBLE);
        } else {
            layoutAirCon.setVisibility(View.GONE);
        }
        if (data.getService().getConversionCall()) {
            layoutConCall.setVisibility(View.VISIBLE);
        } else {
            layoutConCall.setVisibility(View.GONE);
        }
        if (data.getService().getPrinter()) {
            layoutPrinter.setVisibility(View.VISIBLE);
        } else {
            layoutPrinter.setVisibility(View.GONE);
        }
        if (data.getService().getWifi()) {
            layoutWifi.setVisibility(View.VISIBLE);
        } else {
            layoutWifi.setVisibility(View.GONE);
        }
        List<Room> roomData = data.getRooms();
        adapter = new DetailItemAdapter();
        adapter.setData(roomData);
        adapter.setType(1);
        adapter.setItemClickListener(this);
        rcyRoom.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int item) {
        Intent moveData = new Intent(DetailCOEXActivity.this, BookingActivity.class);
        moveData.putExtra("type", 3);
        moveData.putExtra("key", data);
        moveData.putExtra("room", item);
        startActivityForResult(moveData, REQUEST_DATA_BOOKING);
    }
}
