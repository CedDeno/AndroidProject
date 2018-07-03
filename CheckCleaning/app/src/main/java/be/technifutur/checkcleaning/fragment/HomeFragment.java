package be.technifutur.checkcleaning.fragment;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import be.technifutur.checkcleaning.R;
import be.technifutur.checkcleaning.entity.Building;
import be.technifutur.checkcleaning.entity.ControlDB;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    @BindView(R.id.home_barChart)
    LineChart homeLineChart;
    Unbinder unbinder;
    @BindView(R.id.home_building_address)
    TextView homeBuildingAddress;
    private Building mBuilding;
    private List<ControlDB> mControlDBs;
    private LatLng mBuildingPosition;
    private BarDataSet dataSet;
    private List<String> labels;
    private LineDataSet lineDataSet;

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
        createLineDataset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        LineData lineData = new LineData(labels, lineDataSet);
        lineData.setValueTextSize(11);
        homeLineChart.setData(lineData);
        homeLineChart.setDescription("");
        homeLineChart.setClickable(false);
        homeLineChart.setTouchEnabled(false);
        homeLineChart.setDrawGridBackground(false);
        homeLineChart.setDrawBorders(false);
        homeLineChart.setBackgroundColor(Color.WHITE);
        homeLineChart.setHighlightLineWidth(5);

        XAxis xAxis = homeLineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(getResources().getColor(R.color.blackLightColor));
        xAxis.setTextSize(12);

        YAxis yAxisRight = homeLineChart.getAxisRight();
        yAxisRight.setValueFormatter(new MyFormatter());
        yAxisRight.setTextColor(getResources().getColor(R.color.blackLightColor));

        YAxis yAxisLeft = homeLineChart.getAxisLeft();
        yAxisLeft.setValueFormatter(new MyFormatter());
        yAxisLeft.setTextColor(getResources().getColor(R.color.blackLightColor));

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

    public void createLineDataset(){

        LineChart lineChart = new LineChart(getContext());
        ArrayList<Entry> entries = new ArrayList<>();
        labels = new ArrayList();
        for (int i = 0; i < mControlDBs.size(); i++) {
            ControlDB ctrl = mControlDBs.get(i);
            entries.add(new Entry(ctrl.getRating(), i));
            labels.add(getMonth(ctrl.getDate().getMonth()));
        }

        lineDataSet = new LineDataSet(entries, "Qualité mensuelle du nettoyage du bâtiment en %");
        lineDataSet.setCircleSize(6);
        lineDataSet.setLineWidth(2);
        lineDataSet.setDrawCubic(true);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setCircleColor(getResources().getColor(R.color.colorAccent));
        lineDataSet.setColor(getResources().getColor(R.color.colorAccent));
        lineDataSet.setFillColor(getResources().getColor(R.color.colorAccentLight));
        lineDataSet.setValueFormatter(new MyFormatter());
        lineDataSet.setValueTextColor(getResources().getColor(R.color.blackLightColor));
        lineDataSet.setDrawValues(false);
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


