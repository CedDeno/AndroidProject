package be.technifutur.checkcleaning.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

import be.technifutur.checkcleaning.R;
import be.technifutur.checkcleaning.activity.BottomBarActivity;
import be.technifutur.checkcleaning.entity.Building;
import be.technifutur.checkcleaning.entity.Control;
import be.technifutur.checkcleaning.entity.ControlDB;

public class RootControlFragment extends Fragment {

    private BottomBarActivity mActivity;
    private Building mBuilding;

    public RootControlFragment() {
        // Required empty public constructor
    }

    public static RootControlFragment newInstance(BottomBarActivity activity, Building building) {
        RootControlFragment fragment = new RootControlFragment();
        fragment.mActivity = activity;
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

        View view = inflater.inflate(R.layout.fragment_root_control, container, false);

        FragmentTransaction transaction = getFragmentManager().beginTransaction().replace(R.id.fragment_root_control_id, ControlFragment.newInstance(mActivity, mBuilding));
        transaction.commit();

        return view;
    }
}
