package be.technifutur.checkcleaning.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import be.technifutur.checkcleaning.R;
import be.technifutur.checkcleaning.entity.Building;
import be.technifutur.checkcleaning.entity.User;
import be.technifutur.checkcleaning.presenter.CreateTaskPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateTaskFragment extends Fragment {

    TodoFragment mFragment;

    @BindView(R.id.createTask_editText)
    EditText createTaskEditText;

    Unbinder unbinder;

    private User mUser;
    private Building mBuilding;
    private CreateTaskPresenter mPresenter;
    private boolean isClickable;

    public CreateTaskFragment() {

        isClickable = true;
    }

    public static CreateTaskFragment newInstance(TodoFragment fragmentParent, User user, Building building) {

        CreateTaskFragment fragment = new CreateTaskFragment();
        fragment.mFragment = fragmentParent;
        fragment.mUser = user;
        fragment.mBuilding = building;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_task, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new CreateTaskPresenter(this, mUser);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.createTask_button)
    public void onValidateTask() {
        if (isClickable) {
            String task = createTaskEditText.getText().toString();
            mPresenter.saveTask(task, mBuilding.getName());
            isClickable = false;
        }
    }

    @Override
    public void onDestroyView() {
        createTaskEditText.clearFocus();
        unbinder.unbind();
        super.onDestroyView();
    }

    public void updateRecyclerViewFromParent() {
        mFragment.reloadItemAdapter();
    }
}
