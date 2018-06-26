package be.technifutur.checkcleaning.fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import be.technifutur.checkcleaning.R;
import be.technifutur.checkcleaning.entity.Building;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private Building mBuilding;
    private LatLng mBuildingPosition;
    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(Building building) {
        HomeFragment fragment = new HomeFragment();
        fragment.mBuilding = building;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        SupportMapFragment mapView = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.home_map);

        Geocoder geocoder = new Geocoder(getContext());
        List<Address> addresses = new ArrayList<>();
        try {
            addresses = geocoder.getFromLocationName(mBuilding.getAddress(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0) {

            mBuildingPosition = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
        }

        mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap map) {

        map.addMarker(new MarkerOptions().position(mBuildingPosition).title(mBuilding.getName()));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mBuildingPosition, 13.0f));
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.getUiSettings().setScrollGesturesEnabled(false);
    }
}
