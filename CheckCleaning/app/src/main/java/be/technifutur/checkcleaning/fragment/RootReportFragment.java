package be.technifutur.checkcleaning.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.technifutur.checkcleaning.R;
import be.technifutur.checkcleaning.activity.BottomBarActivity;
import be.technifutur.checkcleaning.entity.Building;

/**
 * A simple {@link Fragment} subclass.
 */
public class RootReportFragment extends Fragment {

    private BottomBarActivity mActivity;
    private Building mBuilding;

    public RootReportFragment() {
        // Required empty public constructor
    }

    public static RootReportFragment newInstance(BottomBarActivity activity, Building building) {
        RootReportFragment fragment = new RootReportFragment();
        fragment.mActivity = activity;
        fragment.mBuilding = building;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_root_report, container, false);

        FragmentTransaction transaction = getFragmentManager().beginTransaction().replace(R.id.fragment_root_report_id, ReportFragment.newInstance(mActivity, mBuilding));
        transaction.commit();
        return inflater.inflate(R.layout.fragment_root_report, container, false);
    }


}
