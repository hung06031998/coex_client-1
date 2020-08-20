package com.upit.coex.user.screen.map.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.VisibleRegion;
import com.upit.coex.user.R;
import com.upit.coex.user.contract.map.HomeFragmentContract;
import com.upit.coex.user.repository.model.data.map.CoWokingResponce;
import com.upit.coex.user.repository.model.data.map.Data;
import com.upit.coex.user.screen.base.fragment.BaseFragment;
import com.upit.coex.user.screen.detail.activity.DetailCOEXActivity;
import com.upit.coex.user.screen.dialog.DialogLoading;
import com.upit.coex.user.screen.map.adapter.CoexItemAdapter;
import com.upit.coex.user.screen.map.adapter.SearchItemAdapter;
import com.upit.coex.user.service.logger.L;
import com.upit.coex.user.service.rxjavasenddata.CoexSendData;
import com.upit.coex.user.service.toast.CoexToast;
import com.upit.coex.user.viewmodel.map.fragment.HomeFragmentViewModel;

import static android.app.Activity.RESULT_OK;
import static com.upit.coex.user.constants.CommonConstants.IMAGE_LINK_BASE;
import static com.upit.coex.user.constants.splash.SplashConstant.REQUEST_CODE_LOCATION_PERMISSION;
import static com.upit.coex.user.constants.splash.SplashConstant.TAG_ACIVITY;

public class HomeFragment extends BaseFragment<HomeFragmentViewModel> implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener,
        LifecycleObserver, HomeFragmentContract.HomeInterfaceFragmentView, CoexItemAdapter.onItemClick, SearchItemAdapter.setOnClick {

    private static final String TAG = "HomeFragment";

    private static final int REQUEST_BOOKING = 1212;
    private SearchView mSearchView;
    private ImageView btnSpaceList;
    private View v;
    private RelativeLayout item;
    private CoWokingResponce data, dataSearching;
    private TextView txtItemTitle;
    private TextView txtItemDes, txtDistance;
    private RelativeLayout layoutItemFreeWifi, layoutItemDrink, layoutItemPrinter, layoutItemConCall, layoutItemAirCon;
    private ImageView imgItemImage;
    private TextView txtItemCost;
    private Data temp = new Data();
    private Dialog myDialog;
    private Context context;
    private DialogLoading mDialogLoaing;
    private CardView cardViewSearch;
    private LinearLayout layoutSearchLoading;
    private RelativeLayout imgSearchNoItem;
    private RecyclerView rcySearchItem;
    private SearchItemAdapter searchItemAdapter;
    private CoexItemAdapter adapter;

    public HomeFragment() {

    }

    public void setData(Context mC) {
        this.context = mC;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        mViewModal = ViewModelProviders.of(this).get(HomeFragmentViewModel.class);
        mViewModal.setView(this);
        return v;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mViewModal.requestPermission();
        mViewModal.getCurrentLocation(1);
        if(mDialogLoaing != null)
            mDialogLoaing.startLoadingDialog();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for (int i = 0; i < data.getData().size(); i++) {
            if (marker.getPosition().latitude == data.getData().get(i).getLocation().get(0) &&
                    marker.getPosition().longitude == data.getData().get(i).getLocation().get(1)) {
                setItemToCard(data.getData().get(i));
            }
        }
        return false;
    }

    private void setItemToCard(Data coex) {
        item.setVisibility(View.VISIBLE);
        txtItemTitle.setText(coex.getName());
        txtDistance.setText((double) Math.round(coex.getDistance() * 100) / 100 + "km");
        txtItemDes.setText(coex.getAbout());
        if (coex.getPhoto() != null && coex.getPhoto().size() != 0) {
            Glide.with(context).load(IMAGE_LINK_BASE + coex.getPhoto().get(0)).into(imgItemImage);
        }
        if (coex.getRooms() != null) {
            long cost = coex.getRooms().get(0).getPrice();
            txtItemCost.setText(cost + " VND/ hour/ 1 person");
        } else {
            txtItemCost.setText("No room to show cost");
        }
        if (coex.getService().getDrink()) {
            layoutItemDrink.setVisibility(View.VISIBLE);
        } else {
            layoutItemDrink.setVisibility(View.GONE);
        }
        if (coex.getService().getAirConditioning()) {
            layoutItemAirCon.setVisibility(View.VISIBLE);
        } else {
            layoutItemAirCon.setVisibility(View.GONE);
        }
        if (coex.getService().getConversionCall()) {
            layoutItemConCall.setVisibility(View.VISIBLE);
        } else {
            layoutItemConCall.setVisibility(View.GONE);
        }
        if (coex.getService().getPrinter()) {
            layoutItemPrinter.setVisibility(View.VISIBLE);
        } else {
            layoutItemPrinter.setVisibility(View.GONE);
        }
        if (coex.getService().getWifi()) {
            layoutItemFreeWifi.setVisibility(View.VISIBLE);
        } else {
            layoutItemFreeWifi.setVisibility(View.GONE);
        }

        temp = coex;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mViewModal.setMap(googleMap);

        googleMap.setOnCameraIdleListener(() -> {
            CameraPosition cameraPosition = googleMap.getCameraPosition();
            double radiusInMeters = getMapVisibleRadius(googleMap);
            double radiusInKilometers = radiusInMeters / 1000;
            double latitude = cameraPosition.target.latitude;
            double longitude = cameraPosition.target.longitude;
            mViewModal.sendData(latitude, longitude, radiusInKilometers);
        });
    }

    private double getMapVisibleRadius(GoogleMap googleMap) {
        VisibleRegion visibleRegion = googleMap.getProjection().getVisibleRegion();

        float[] distanceWidth = new float[1];
        float[] distanceHeight = new float[1];

        LatLng farRight = visibleRegion.farRight;
        LatLng farLeft = visibleRegion.farLeft;
        LatLng nearRight = visibleRegion.nearRight;
        LatLng nearLeft = visibleRegion.nearLeft;

        Location.distanceBetween(
                (farLeft.latitude + nearLeft.latitude) / 2,
                farLeft.longitude,
                (farRight.latitude + nearRight.latitude) / 2,
                farRight.longitude,
                distanceWidth
        );

        Location.distanceBetween(
                farRight.latitude,
                (farRight.longitude + farLeft.longitude) / 2,
                nearRight.latitude,
                (nearRight.longitude + nearLeft.longitude) / 2,
                distanceHeight
        );

        double radiusInMeters = Math.sqrt(Math.pow(distanceWidth[0], 2) + Math.pow(distanceHeight[0], 2)) / 2;
        return radiusInMeters;
    }

    @Override
    public void observeLifeCycle() {
    }

    private boolean statusSearchingNow = false;

    @Override
    public void bindView() {
        mDialogLoaing = new DialogLoading(getActivity());
        mSearchView = v.findViewById(R.id.fragment_home_edt_search);
        btnSpaceList = v.findViewById(R.id.fragment_home_btn_list);
        layoutItemAirCon = v.findViewById(R.id.fragment_home_layout_air_condirioning);
        layoutItemConCall = v.findViewById(R.id.fragment_home_layout_conversion_call);
        layoutItemFreeWifi = v.findViewById(R.id.fragment_home_layout_free_wifi);
        layoutItemDrink = v.findViewById(R.id.fragment_home_layout_drink);
        layoutItemPrinter = v.findViewById(R.id.fragment_home_layout_printer);
        imgItemImage = v.findViewById(R.id.fragment_home_img_coex);
        txtItemCost = v.findViewById(R.id.fragment_home_txt_cost);
        txtItemDes = v.findViewById(R.id.fragment_home_txt_description);
        txtItemTitle = v.findViewById(R.id.fragment_home_txt_name);
        txtDistance = v.findViewById(R.id.fragment_home_txt_distance);
        rcySearchItem = v.findViewById(R.id.fragment_home_search_view_recycleview);
        imgSearchNoItem = v.findViewById(R.id.fragment_home_search_view_no_item);
        layoutSearchLoading = v.findViewById(R.id.fragment_home_search_view_progcess_bar);
        cardViewSearch = v.findViewById(R.id.layout_search_view);
        searchItemAdapter = new SearchItemAdapter(getContext());
        searchItemAdapter.setOnClick(this);
        rcySearchItem.setAdapter(searchItemAdapter);
        adapter = new CoexItemAdapter(context);
        adapter.setItemClick(this);

        btnSpaceList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModal.showCoex();
            }
        });

        item = v.findViewById(R.id.fragment_home_layout_detail);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveData = new Intent(getContext(), DetailCOEXActivity.class);
                moveData.putExtra("key", temp);
                startActivityForResult(moveData, REQUEST_BOOKING);
            }
        });
        item.setVisibility(View.GONE);

        searchInit();
    }

    @Override
    public void onStart() {
        super.onStart();
        cardViewSearch.setVisibility(View.GONE);
    }

    private void searchInit() {
        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mSearchView.on
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                imgSearchNoItem.setVisibility(View.GONE);
                rcySearchItem.setVisibility(View.GONE);
                layoutSearchLoading.setVisibility(View.GONE);
                cardViewSearch.setVisibility(View.GONE);
                statusSearchingNow = false;
                return false;
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mViewModal.searchRoom(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!statusSearchingNow) {
                    cardViewSearch.setVisibility(View.VISIBLE);
                    imgSearchNoItem.setVisibility(View.GONE);
                    rcySearchItem.setVisibility(View.GONE);
                    layoutSearchLoading.setVisibility(View.VISIBLE);
                    mViewModal.searchRoom(newText);
                }
                return false;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_BOOKING && resultCode == RESULT_OK) {
            String result = data.getStringExtra("result");
            L.d("ahiuhiu", result);
            ScheduleFragment nextFrag = new ScheduleFragment();
            CoexSendData.getInstance().sendData(new Pair<String, Object>("bao.nt", R.id.nav_main_schedule));
        }
    }

    private void initWindow() {
        adapter.setData(data);

        myDialog = new Dialog(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View v2 = inflater.inflate(R.layout.dialog_style_room, null);
        myDialog.setContentView(v2);
        myDialog.findViewById(R.id.dialog_style_room_btn_close).setOnClickListener(
                v -> myDialog.dismiss());
        TextView t = myDialog.findViewById(R.id.dialog_style_room_txt_title);
        t.setText("Space list");
        RecyclerView temp = myDialog.findViewById(R.id.dialog_style_room_rcy);
        temp.setAdapter(adapter);

        myDialog.setCanceledOnTouchOutside(true);//bam ra ngoai
        myDialog.setCancelable(true);//bam nut back
        mDialogLoaing.dissLoadingDialog();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        L.d(TAG, TAG_ACIVITY, "onRequestPermissionsResult!");
        switch (requestCode) {
            case REQUEST_CODE_LOCATION_PERMISSION: {
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    L.d(TAG, TAG_ACIVITY, "Permission granted!");
                    CoexToast.makeToast(getActivity(), Toast.LENGTH_LONG, "Permission granted!");
                    mViewModal.getCurrentLocation(3);
                } else {
                    L.d(TAG, TAG_ACIVITY, "Permission denied!");
                    CoexToast.makeToast(getActivity(), Toast.LENGTH_LONG, "Permission denied!");
                }
                break;
            }
        }
    }

    @Override
    public void getAllCOexWoking(boolean result, CoWokingResponce data, String message) {
        if (result) {
            this.data = data;
            initWindow();
        } else {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
        if (mDialogLoaing.checkLoading()) {
            mDialogLoaing.dissLoadingDialog();
        }
    }

    @Override
    public void showAllCoex() {
        myDialog.show();
    }

    @Override
    public void showAllSearchResult(boolean result, CoWokingResponce data) {
        this.dataSearching = data;
        layoutSearchLoading.setVisibility(View.GONE);
        if (result) {
            searchItemAdapter.setData(data.getData());
            rcySearchItem.setVisibility(View.VISIBLE);
            imgSearchNoItem.setVisibility(View.GONE);
        } else {
            rcySearchItem.setVisibility(View.GONE);
            imgSearchNoItem.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void itemClick(int number) {
        temp = data.getData().get(number);
        Intent moveData = new Intent(getContext(), DetailCOEXActivity.class);
        moveData.putExtra("key", temp);
        startActivity(moveData);
    }

    @Override
    public void onClickItem(int pos) {
        temp = dataSearching.getData().get(pos);
        Intent moveData = new Intent(getContext(), DetailCOEXActivity.class);
        moveData.putExtra("key", temp);
        startActivity(moveData);
    }
}
