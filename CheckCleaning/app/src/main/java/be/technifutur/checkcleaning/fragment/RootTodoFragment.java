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
import be.technifutur.checkcleaning.entity.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class RootTodoFragment extends Fragment {
    private BottomBarActivity mActivity;
    private User mUser;
    private Building mBuilding;

    public RootTodoFragment() {
        // Required empty public constructor
    }

    public static RootTodoFragment newInstance(BottomBarActivity activity, User user, Building building) {
        RootTodoFragment fragment = new RootTodoFragment();
        fragment.mUser = user;
        fragment.mBuilding = building;
        fragment.mActivity = activity;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_root_todo, container, false);

        FragmentTransaction transaction = getFragmentManager().beginTransaction().replace(R.id.fragment_root_todo_id, TodoFragment.newInstance(mActivity, mUser, mBuilding));
        transaction.commit();

        return view;
    }

}
