package com.upit.coex.user.contract.map;

import com.google.android.gms.maps.GoogleMap;
import com.upit.coex.user.contract.base.fragment.BaseInterfaceFragmentView;
import com.upit.coex.user.contract.base.fragment.BaseInterfaceFragmentViewModel;
import com.upit.coex.user.repository.model.data.map.CoWokingResponce;
import com.upit.coex.user.screen.map.fragment.HomeFragment;

public class HomeFragmentContract {
    public interface HomeInterfaceFragmentViewModel extends BaseInterfaceFragmentViewModel<HomeFragment> {

        void initMap();

        void setMap(GoogleMap googleMap);

        void getCurrentLocation(double distance);

        void moveCamera(double lat, double lng);

        void addMarker(double lat, double lng, String title);

        void googleApiClientStart();

        void googleApiClientStop();

        void removeAllMarker();

        void checkToken();

        void removeDisposal();

        void sendData(double lat, double lng, double distance);

        void AddMakerCOEX(CoWokingResponce data);

        void requestPermission();

        void showCoex();

        void searchRoom(String query);
    }

    public interface HomeInterfaceFragmentView extends BaseInterfaceFragmentView {
        void getAllCOexWoking(boolean result, CoWokingResponce data, String message);

        void showAllCoex();

        void showAllSearchResult(boolean result, CoWokingResponce data);
    }
}
