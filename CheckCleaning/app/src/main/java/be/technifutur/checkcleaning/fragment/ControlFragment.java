package be.technifutur.checkcleaning.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.technifutur.checkcleaning.R;

public class ControlFragment extends Fragment {

    public ControlFragment() {

    }

    public static ControlFragment newInstance() {

        ControlFragment fragment = new ControlFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_control, container, false);
    }
}
