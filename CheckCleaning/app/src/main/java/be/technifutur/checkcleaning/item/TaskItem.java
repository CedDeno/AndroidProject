package be.technifutur.checkcleaning.item;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.commons.utils.FastAdapterUIUtils;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.fastadapter.listeners.OnBindViewHolderListener;
import com.mikepenz.fastadapter_extensions.drag.IDraggable;
import com.mikepenz.materialize.util.UIUtils;

import java.util.List;
import be.technifutur.checkcleaning.R;
import be.technifutur.checkcleaning.entity.TaskData;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskItem extends AbstractItem<TaskItem, TaskItem.ViewHolder> {

    private TaskData task;
    private boolean mIsDraggable = true;

    public TaskItem(TaskData task) {
        this.task = task;
    }

    public TaskData getTask() {
        return task;
    }

    @NonNull
    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public int getType() {
        return R.id.task_title_textView;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.cell_task;
    }


    public class ViewHolder extends FastAdapter.ViewHolder<TaskItem>{

        protected View view;
        @BindView(R.id.task_title_textView)
        TextView taskTitleTextView;

        @BindView(R.id.task_content_textView)
        TextView taskContentTextView;

        public Runnable swipedActionRunnable;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindView(TaskItem item, List<Object> payloads) {

            UIUtils.setBackground(view, FastAdapterUIUtils.getSelectableBackground(itemView.getContext(), view.getResources().getColor(R.color.selectedItemColor), true));
            taskTitleTextView.setText(item.task.getBuilding_name());
            taskTitleTextView.setTextColor(itemView.getResources().getColor(R.color.buttonColor));
            taskContentTextView.setText(item.task.getContent());
        }

        @Override
        public void unbindView(TaskItem item) {

            taskTitleTextView.setText(null);
            taskContentTextView.setText(null);
        }


    }
}
