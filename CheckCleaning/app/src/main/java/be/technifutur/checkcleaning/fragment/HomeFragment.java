package be.technifutur.checkcleaning.fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ValueFormatter;
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
import be.technifutur.checkcleaning.entity.ControlDB;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    @BindView(R.id.home_barChart)
    BarChart homeBarChart;
    Unbinder unbinder;
    @BindView(R.id.home_building_address)
    TextView homeBuildingAddress;
    private Building mBuilding;
    private List<ControlDB> mControlDBs;
    private LatLng mBuildingPosition;
    private BarDataSet dataSet;
    private List<String> labels;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(Building building, List<ControlDB> controlDBs) {
        HomeFragment fragment = new HomeFragment();
        fragment.mBuilding = building;
        fragment.mControlDBs = controlDBs;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createDatasetAndLabels();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        BarData data = new BarData(labels, dataSet);
        data.setValueTextSize(10);

        homeBarChart.setData(data);
        homeBarChart.setDescription("");
        homeBarChart.setClickable(false);
        homeBarChart.getAxisLeft().setTextSize(12);
        homeBarChart.getXAxis().setTextSize(15);
        homeBarChart.setTouchEnabled(false);

        XAxis xAxis = homeBarChart.getXAxis();
        xAxis.setDrawGridLines(false);

        YAxis yAxisRight = homeBarChart.getAxisRight();
        yAxisRight.setDrawLabels(false);

        YAxis yAxisLeft = homeBarChart.getAxisLeft();
        yAxisLeft.setLabelCount(5);
        yAxisLeft.setValueFormatter(new MyFormatter());
        yAxisLeft.setAxisMaxValue(100);
        yAxisLeft.setDrawGridLines(false);

        dataSet.setColor(getResources().getColor(R.color.buttonColorLight));
        dataSet.setValueFormatter(new MyFormatter());

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

        homeBuildingAddress.setText(mBuilding.getAddress());

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap map) {

        map.addMarker(new MarkerOptions().position(mBuildingPosition).title(mBuilding.getName()));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mBuildingPosition, 13.0f));
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.getUiSettings().setScrollGesturesEnabled(false);
    }

    public void createDatasetAndLabels() {

        ArrayList<BarEntry> entries = new ArrayList<>();
        labels = new ArrayList();

        for (int i = 0; i < mControlDBs.size(); i++) {
            ControlDB ctrl = mControlDBs.get(i);
            entries.add(new BarEntry((float) ctrl.getRating(), i));
            labels.add(getMonth(ctrl.getDate().getMonth()));
        }

        dataSet = new BarDataSet(entries, "Qualité mensuelle du nettoyage du bâtiment en %");
    }

    public String getMonth(int i) {

        String result = "";

        switch (i) {

            case 0:
                result = "Jan";
                break;
            case 1:
                result = "Féb";
                break;
            case 2:
                result = "Mar";
                break;
            case 3:
                result = "Avr";
                break;
            case 4:
                result = "Mai";
                break;
            case 5:
                result = "Juin";
                break;
            case 6:
                result = "Juil";
                break;
            case 7:
                result = "Aou";
                break;
            case 8:
                result = "Sep";
                break;
            case 9:
                result = "Oct";
                break;
            case 10:
                result = "Nov";
                break;
            case 11:
                result = "Déc";
                break;
        }

        return result;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public class MyFormatter implements ValueFormatter {

        @Override
        public String getFormattedValue(float value) {
            return String.valueOf((int) value);
        }
    }
}


