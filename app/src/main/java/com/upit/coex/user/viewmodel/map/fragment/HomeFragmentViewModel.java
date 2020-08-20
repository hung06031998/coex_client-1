package com.upit.coex.user.viewmodel.map.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.upit.coex.user.CoexApplication;
import com.upit.coex.user.R;
import com.upit.coex.user.contract.map.HomeFragmentContract;
import com.upit.coex.user.repository.model.data.base.BaseErrorData;
import com.upit.coex.user.repository.model.data.map.CoWokingResponce;
import com.upit.coex.user.repository.model.remote.room.GetRoomApi;
import com.upit.coex.user.repository.model.remote.room.SearchRoomApi;
import com.upit.coex.user.screen.map.fragment.HomeFragment;
import com.upit.coex.user.service.fetchandsave.ApiRepository;
import com.upit.coex.user.service.sharepreference.CoexSharedPreference;
import com.upit.coex.user.viewmodel.base.BaseFragmentViewModel;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.upit.coex.user.constants.map.MapConstant.MAP_URL;
import static com.upit.coex.user.constants.splash.SplashConstant.REQUEST_CODE_LOCATION_PERMISSION;
import static com.upit.coex.user.constants.splash.SplashConstant.lat;
import static com.upit.coex.user.constants.splash.SplashConstant.lng;

public class HomeFragmentViewModel extends BaseFragmentViewModel<HomeFragment> implements HomeFragmentContract.HomeInterfaceFragmentViewModel {

    private GoogleMap mMap;
    private double longitude;
    private double latitude;
    private GoogleApiClient googleApiClient;

    public HomeFragmentViewModel() {
    }


    @Override
    public void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) this.mView.getChildFragmentManager()
                .findFragmentById(R.id.frmap);
        if (mapFragment != null) {
            mapFragment.getMapAsync(mView);
        } else {
            mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(mView);
        }
    }

    @Override
    public void setMap(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        mView.getActivity(), R.raw.coex_map_style));
        // Move the camera to Viet nam
        moveCamera(lat, lng);

        //mMap.setOnMapLongClickListener(mView);
        mMap.setOnMarkerClickListener(mView);

        Fragment fragment = (this.mView.getChildFragmentManager().findFragmentById(R.id.frmap));
        ViewGroup v1 = (ViewGroup) fragment.getView();
        if (v1 != null) {
            ViewGroup v2 = (ViewGroup) v1.getChildAt(0);
            ViewGroup v3 = (ViewGroup) v2.getChildAt(2);

            View currentPosition = (View) v3.getChildAt(0);
            int positionWidth = currentPosition.getLayoutParams().width;
            int positionHeight = currentPosition.getLayoutParams().height;
            RelativeLayout.LayoutParams positionParams = new RelativeLayout.LayoutParams(positionWidth, positionHeight);
            positionParams.setMargins(dpToPx(24), dpToPx(90), 0, 0);
            positionParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            positionParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            currentPosition.setLayoutParams(positionParams);

            View currentPosition2 = (View) v3.getChildAt(4);
            int positionWidth2 = currentPosition2.getLayoutParams().width;
            int positionHeight2 = currentPosition2.getLayoutParams().height;
            RelativeLayout.LayoutParams positionParams2 = new RelativeLayout.LayoutParams(positionWidth2, positionHeight2);
            positionParams2.setMargins(dpToPx(24), dpToPx(140), 0, 0);
            positionParams2.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            positionParams2.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            currentPosition2.setLayoutParams(positionParams2);

            View currentPosition3 = (View) v3.getChildAt(2);
            int positionWidth3 = currentPosition3.getLayoutParams().width;
            int positionHeight3 = currentPosition3.getLayoutParams().height;
            RelativeLayout.LayoutParams positionParams3 = new RelativeLayout.LayoutParams(positionWidth3, positionHeight3);
            positionParams3.setMargins(0, dpToPx(90), dpToPx(24), 0);
            positionParams3.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            positionParams3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            currentPosition3.setLayoutParams(positionParams3);
        }

        //Initializing googleApiClient
        googleApiClient = new GoogleApiClient.Builder(mView.getActivity())
                .addConnectionCallbacks(mView)
                .addOnConnectionFailedListener(mView)
                .addApi(LocationServices.API)
                .build();

        googleApiClientStart();
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    @Override
    public void getCurrentLocation(double distance) {
        latitude = 21.0241208;
        longitude = 105.7890571;
        mMap.clear();
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(mView.getActivity());
        try {
            mMap.setMyLocationEnabled(true);
            fusedLocationClient.getLastLocation().addOnSuccessListener(mView.getActivity(), location -> {
                if (location != null) {
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                }
            });
        } catch (SecurityException e) {
        }
        moveCamera(latitude, longitude);
        sendData(latitude, longitude, distance);
    }


    @Override
    public void moveCamera(double lat, double lng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public void addMarker(double lat, double lng, String title) {
        LatLng india = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(india).title(title));
        moveCamera(lat, lng);
    }

    @Override
    public void AddMakerCOEX(CoWokingResponce data) {
        for (int i = 0; i < data.getData().size(); i++) {
            LatLng india = new LatLng(data.getData().get(i).getLocation().get(0), data.getData().get(i).getLocation().get(1));
            mMap.addMarker(new MarkerOptions().position(india)
                    .title(data.getData().get(i).getName()));
        }

    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void googleApiClientStart() {
        Log.d("aaa", "googleApiClientStart");
        if (googleApiClient != null) {
            Log.d("aaa", "googleApiClientStart");
            googleApiClient.connect();
        }
    }

    @Override
    public void googleApiClientStop() {
        Log.d("aaa", "googleApiClientStop");
        if (googleApiClient != null) {
            Log.d("aaa", "googleApiClientStop");
            googleApiClient.disconnect();
        }
    }

    public void setPosNow(double lat, double lng) {
        addMarker(lat, lng, "You just clicked here");
    }

    @Override
    public void removeAllMarker() {

    }

    @Override
    public void checkToken() {
    }

    @Override
    public void removeDisposal() {

    }

    @Override
    public void sendData(double lat, double lng, double distance) {
        ApiRepository.getInstance().
                setUrl(MAP_URL).
                createRetrofit().
                create(GetRoomApi.class).
                doQuery("Bearer " +CoexSharedPreference.getInstance().get("token", String.class),distance, latitude, longitude, lat, lng).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new SingleObserver<CoWokingResponce>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(CoWokingResponce value) {
                        if (value.getCode() == 200) {
                            mLive.setValue(value);
                            mView.getAllCOexWoking(true, value, value.getMessage());
                            AddMakerCOEX(value);
                        } else {
                            mView.getAllCOexWoking(false, null, value.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        BaseErrorData err = new BaseErrorData(e);
                        if (err.getMessage() != null)
                            mView.getAllCOexWoking(false, null, err.getMessage());
                        else
                            mView.getAllCOexWoking(false, null, e.getMessage());

                    }
                });
    }

    @Override
    public void setView(HomeFragment view) {
        mView = view;
        mView.bindView();
        initMap();

        mLive = new MutableLiveData();
        mView.observeLifeCycle();
    }

    @Override
    public void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int accessCoarsePermission
                    = ContextCompat.checkSelfPermission(mView.getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
            int accessFinePermission
                    = ContextCompat.checkSelfPermission(mView.getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
            if (accessCoarsePermission != PackageManager.PERMISSION_GRANTED
                    || accessFinePermission != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION};
                ActivityCompat.requestPermissions(mView.getActivity(), permissions,
                        REQUEST_CODE_LOCATION_PERMISSION);

                return;
            }
        }
    }

    @Override
    public void showCoex() {
        mView.showAllCoex();
    }

    @Override
    public void searchRoom(String query) {
        ApiRepository.getInstance().
                setUrl(MAP_URL).
                createRetrofit().
                create(SearchRoomApi.class).
                doSearchRoom("Bearer " +CoexSharedPreference.getInstance().get("token", String.class),query, latitude, longitude).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new SingleObserver<CoWokingResponce>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(CoWokingResponce value) {
                        if (value.getCode() == 200) {
                            if (value.getData().size() > 0) {
                                mView.showAllSearchResult(true, value);
                            } else {
                                mView.showAllSearchResult(false, null);
                            }
                        } else {
                            mView.showAllSearchResult(true, null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showAllSearchResult(false, null);
                    }
                });
    }

    @Override
    public MutableLiveData getMutableLiveData() {
        return mLive;
    }
}
