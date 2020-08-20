package com.upit.coex.user.screen.wallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.upit.coex.user.R;
import com.upit.coex.user.repository.model.data.base.BaseResponce;
import com.upit.coex.user.repository.model.data.coin.HistoryExchangeRespone;
import com.upit.coex.user.repository.model.data.coin.ListExchangeCoin;
import com.upit.coex.user.repository.model.data.coin.ListExchangePoint;
import com.upit.coex.user.repository.model.remote.withdraw.GetHistoryExchangeApiRequest;
import com.upit.coex.user.screen.dialog.DialogLoading;
import com.upit.coex.user.screen.wallet.fragment.CoinHistoryFragment;
import com.upit.coex.user.screen.wallet.fragment.PointHistoryFragment;
import com.upit.coex.user.service.fetchandsave.ApiRepository;
import com.upit.coex.user.service.logger.L;
import com.upit.coex.user.service.sharepreference.CoexSharedPreference;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.upit.coex.user.constants.register.RegisterConstant.POINT_URL;

public class HistoryExchangeActivity extends AppCompatActivity {
    private DialogLoading mDialogLoading;
    private List<ListExchangeCoin> mListExchangeCoin;
    private List<ListExchangePoint> mListExchangePoint;
    private ImageView imgBack;
    private TextView txtCoin;
    private float coinNow;
    private ViewPager pager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histoty_exchange);
        init();
        getData();
    }

    private void addControl() {
        pager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        FragmentManager manager = getSupportFragmentManager();
        PagerAdapter adapter = new PagerAdapter(manager);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(adapter);//deprecated
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.item_tab_layout_title, null);
            tabLayout.getTabAt(i).setCustomView(tv);
        }
    }

    private void getData() {
        mDialogLoading = new DialogLoading(this);
        mDialogLoading.startLoadingDialog();
        Intent intent = getIntent();
        coinNow = intent.getFloatExtra("coinNow", 1);
        txtCoin.setText(coinNow + " COIN");

        ApiRepository.getInstance().
                setUrl(POINT_URL).
                createRetrofit().
                create(GetHistoryExchangeApiRequest.class).
                getHistoryExchange("Bearer " + CoexSharedPreference.getInstance().get("token", String.class)).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<BaseResponce<HistoryExchangeRespone>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseResponce<HistoryExchangeRespone> value) {
                        if (value.getCode() == 200) {
                            mListExchangeCoin = value.getMdata().getListExchangeCoin();
                            mListExchangePoint = value.getMdata().getListExchangePoint();
                            addControl();
                        } else {
                            L.d("ahiuhiu", value.getMessage());
                            Toast.makeText(HistoryExchangeActivity.this, "Fail !", Toast.LENGTH_SHORT).show();
                        }
                        mDialogLoading.dissLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(HistoryExchangeActivity.this, "Fail !", Toast.LENGTH_SHORT).show();
                        L.d("ahiuhiu", e.toString());
                        mDialogLoading.dissLoadingDialog();
                    }
                });
    }

    private void init() {
        txtCoin = findViewById(R.id.activity_history_exchange_txt_show_coin);
        imgBack = findViewById(R.id.activity_history_exchange_btn_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {

        PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment frag = null;
            switch (position) {
                case 0:
                    frag = new CoinHistoryFragment(mListExchangeCoin);
                    break;
                case 1:
                    frag = new PointHistoryFragment(mListExchangePoint);
                    break;
            }
            return frag;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "";
            switch (position) {
                case 0:
                    title = "Coin";
                    break;
                case 1:
                    title = "Point";
                    break;
            }
            return title;
        }
    }
}
