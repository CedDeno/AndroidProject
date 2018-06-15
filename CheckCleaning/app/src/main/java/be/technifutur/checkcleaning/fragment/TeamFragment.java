package be.technifutur.checkcleaning.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import java.util.List;
import be.technifutur.checkcleaning.R;
import be.technifutur.checkcleaning.entity.User;
import be.technifutur.checkcleaning.item.ContactItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TeamFragment extends Fragment {

    @BindView(R.id.TeamRecyclerView)
    RecyclerView teamRecyclerView;

    private FastItemAdapter<ContactItem> contactItemAdapter;
    private User mUser;
    private List<User> mBuildingUsers;

    Unbinder unbinder;

    public TeamFragment() {}

    public static TeamFragment newInstance(User user, List<User> buildingUsers) {
        TeamFragment fragment = new TeamFragment();
        fragment.mUser = user;
        fragment.mBuildingUsers = buildingUsers;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_team, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        teamRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(teamRecyclerView.getContext(),
                layoutManager.getOrientation());
        teamRecyclerView.addItemDecoration(dividerItemDecoration);
        teamRecyclerView.setClickable(true);
        teamRecyclerView.setAdapter(contactItemAdapter);
        contactItemAdapter = new FastItemAdapter<>();
        for (User user : mBuildingUsers) {
            contactItemAdapter.add(new ContactItem(user, this.getActivity()));
        }
        contactItemAdapter.withSelectable(true);
        contactItemAdapter.withSavedInstanceState(savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
