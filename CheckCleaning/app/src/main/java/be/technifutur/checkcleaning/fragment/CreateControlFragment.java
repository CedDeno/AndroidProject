package be.technifutur.checkcleaning.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import be.technifutur.checkcleaning.R;
import be.technifutur.checkcleaning.entity.Control;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateControlFragment extends Fragment implements RatingBar.OnRatingBarChangeListener {


    @BindView(R.id.create_control_title_textView)
    TextView createControlTitleTextView;
    @BindView(R.id.create_control_detail_editText)
    EditText createControlDetailEditText;
    @BindView(R.id.create_control_ratingBar)
    RatingBar createControlRatingBar;
    @BindView(R.id.create_control_rating_textView)
    TextView createControlRatingTextView;
    @BindView(R.id.create_control_notice_textView)
    TextView createControlNoticeTextView;
    @BindView(R.id.create_control_notice_editText)
    EditText createControlNoticeEditText;
    @BindView(R.id.create_control_validate_button)
    Button createControlValidateButton;

    private String mKey;
    private Map<String, List<Control>> mControls;
    Unbinder unbinder;

    public CreateControlFragment() {
        // Required empty public constructor
    }

    public static CreateControlFragment newInstance(String key, Map<String, List<Control>> controls) {

        CreateControlFragment fragment = new CreateControlFragment();
        fragment.mKey = key;
        fragment.mControls = controls;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_create_control, container, false);
        ButterKnife.bind(this, view);

        createControlTitleTextView.setText(mKey);
        createControlRatingBar.setIsIndicator(true);
        createControlRatingBar.setNumStars(1);
        createControlRatingTextView.setText("Très mauvais");
        createControlRatingBar.setOnRatingBarChangeListener(this);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.create_control_validate_button)
    public void onValidate(){

        String detail = createControlDetailEditText.getText().toString();
        String notice = createControlNoticeEditText.getText().toString();

        if (!detail.isEmpty() && !notice.isEmpty()){

            Control c = new Control();
            c.setDetail(detail);
            c.setComment(notice);
            c.setRating(createControlRatingBar.getNumStars());


        }
     }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

        int intRating = (int)rating;

        switch (intRating){

            case 1 :
                createControlRatingTextView.setText("Très mauvais");
                break;
            case 2 :
                createControlRatingTextView.setText("Mauvais");
                break;
            case 3 :
                createControlRatingTextView.setText("Acceptable");
                break;
            case 4 :
                createControlRatingTextView.setText("Bon");
                break;
            case 5 :
                createControlRatingTextView.setText("Excellent");
                break;
        }
    }

    public void updateViewFromParent() {

        //TODO update 1 controle en plus dans la catégorie selectionnnée..
    }

}
