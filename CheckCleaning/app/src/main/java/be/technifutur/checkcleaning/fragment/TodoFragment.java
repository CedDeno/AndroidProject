package be.technifutur.checkcleaning.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewStubCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.ISelectionListener;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.listeners.OnClickListener;
import com.mikepenz.fastadapter.listeners.OnLongClickListener;
import com.mikepenz.fastadapter_extensions.ActionModeHelper;
import com.mikepenz.fastadapter_extensions.UndoHelper;
import com.mikepenz.itemanimators.SlideDownAlphaAnimator;
import com.mikepenz.materialize.MaterializeBuilder;
import com.mikepenz.materialize.util.UIUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import be.technifutur.checkcleaning.R;
import be.technifutur.checkcleaning.activity.BottomBarActivity;
import be.technifutur.checkcleaning.entity.Building;
import be.technifutur.checkcleaning.entity.TaskData;
import be.technifutur.checkcleaning.entity.User;
import be.technifutur.checkcleaning.item.TaskItem;
import be.technifutur.checkcleaning.presenter.TodoPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodoFragment extends Fragment{

    @BindView(R.id.todo_recyclerView)
    RecyclerView todoRecyclerView;

    private BottomBarActivity mActivity;
    private User mUser;
    private Building mBuilding;
    private TodoPresenter mPresenter;
    private FastItemAdapter<TaskItem> taskItemAdapter;
    private UndoHelper mUndoHelper;
    private ActionModeHelper<TaskItem> mActionModeHelper;
    Unbinder unbinder;

    public TodoFragment() {
    }

    public static TodoFragment newInstance(BottomBarActivity activity, User user, Building building) {
        TodoFragment fragment = new TodoFragment();
        fragment.mUser = user;
        fragment.mBuilding = building;
        fragment.mActivity = activity;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new TodoPresenter(this, mUser);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_todo, container, false);
        ButterKnife.bind(this, view);

        //style our ui
        new MaterializeBuilder().withActivity(getActivity()).build();

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        todoRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(todoRecyclerView.getContext(),
                layoutManager.getOrientation());

        todoRecyclerView.addItemDecoration(dividerItemDecoration);
        //todoRecyclerView.setClickable(true);

        taskItemAdapter = new FastItemAdapter<>();
        for (TaskData task : mUser.getTasks()) {
            taskItemAdapter.add(new TaskItem(task));
        }

        //configure our mFastAdapter
        //as we provide id's for the items we want the hasStableIds enabled to speed up things
        taskItemAdapter.setHasStableIds(true);
        taskItemAdapter.withSelectable(true);
        taskItemAdapter.withMultiSelect(true);
        taskItemAdapter.withSelectOnLongClick(true);
        taskItemAdapter.withSelectionListener(new ISelectionListener<TaskItem>() {
            @Override
            public void onSelectionChanged(TaskItem item, boolean selected) {
                Log.i("FastAdapter", "SelectedCount: " + taskItemAdapter.getSelections().size() + " ItemsCount: " + taskItemAdapter.getSelectedItems().size());
            }
        });
        taskItemAdapter.withOnPreClickListener(new OnClickListener<TaskItem>() {
            @Override
            public boolean onClick(View v, IAdapter<TaskItem> adapter, @NonNull TaskItem item, int position) {
                //we handle the default onClick behavior for the actionMode. This will return null if it didn't do anything and you can handle a normal onClick
                Boolean res = mActionModeHelper.onClick(item);
                return res != null ? res : false;
            }
        });
        taskItemAdapter.withOnClickListener(new OnClickListener<TaskItem>() {
            @Override
            public boolean onClick(View v, IAdapter<TaskItem> adapter, @NonNull TaskItem item, int position) {
                return false;
            }
        });
        taskItemAdapter.withOnPreLongClickListener(new OnLongClickListener<TaskItem>() {
            @Override
            public boolean onLongClick(View v, IAdapter<TaskItem> adapter, TaskItem item, int position) {
                ActionMode actionMode = mActionModeHelper.onLongClick((AppCompatActivity)getActivity(), position);

                if (actionMode != null) {
                    //we want color our CAB
                    //getActivity().findViewById(R.id.action_mode_bar).setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(mActivity, R.attr.colorPrimary, R.color.buttonColor));
                }

                //if we have no actionMode we do not consume the event
                return actionMode != null;
            }
        });

        //
        mUndoHelper = new UndoHelper<>(taskItemAdapter, new UndoHelper.UndoListener<TaskItem>() {
            @Override
            public void commitRemove(Set<Integer> positions, ArrayList<FastAdapter.RelativeInfo<TaskItem>> removed) {
                mPresenter.removeTasks(positions);
            }
        });

        //we init our ActionModeHelper
        mActionModeHelper = new ActionModeHelper<>(taskItemAdapter, R.menu.drop_down, new ActionBarCallBack());

        todoRecyclerView.setAdapter(taskItemAdapter);
        todoRecyclerView.setItemAnimator(new SlideDownAlphaAnimator());
        //restore selections (this has to be done after the items were added
        taskItemAdapter.withSavedInstanceState(savedInstanceState);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.todo_add_button)
    public void onAddTask(){

        Fragment fragment = CreateTaskFragment.newInstance(this, mUser, mBuilding);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.animator.fade_in_bottom, android.R.animator.fade_out);
        ft.replace(R.id.fragment_root_todo_id, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
        mActivity.setFragmentIsOpen(true);
        mActivity.getViewPager().setEnable(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setRecyclerView(List<TaskData> tasks) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the adapter to the bundle
        outState = taskItemAdapter.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle the click on the back arrow click
        Toast.makeText(this.getContext(), "item.getItemId()", Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {

            case android.R.id.home:
                getActivity().onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void reloadItemAdapter() {
        Collections.sort(mUser.getTasks());
        taskItemAdapter = new FastItemAdapter<>();
        for (TaskData task : mUser.getTasks()) {
            taskItemAdapter.add(new TaskItem(task));
        }

        taskItemAdapter.notifyAdapterDataSetChanged();
        //getActivity().setTitle("Notes");
        getActivity().getSupportFragmentManager().popBackStackImmediate();
        mActivity.setFragmentIsOpen(false);
        mActivity.getViewPager().setEnable(true);

    }

    /**
     * Our ActionBarCallBack to showcase the CAB
     */
    class ActionBarCallBack implements ActionMode.Callback {

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            mUndoHelper.remove(getActivity().findViewById(android.R.id.content), "Item removed", "Undo", Snackbar.LENGTH_LONG, taskItemAdapter.getSelections());
            mode.finish();
            //we consume the event
            return true;
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }
    }
}
