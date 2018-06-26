package be.technifutur.checkcleaning.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mindorks.paracamera.Camera;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import be.technifutur.checkcleaning.R;
import be.technifutur.checkcleaning.Util.CustomProgress;
import be.technifutur.checkcleaning.entity.Building;
import be.technifutur.checkcleaning.entity.Control;
import be.technifutur.checkcleaning.item.PictureItem;
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
    @BindView(R.id.picture_recyclerView)
    RecyclerView pictureRecyclerView;

    private String mKey;
    private String mTitle;
    private int mNbMaxInCategory;
    private CustomProgress mCustomProgress;
    private Map<String, List<Control>> mControls;
    private ControlFragment mFragmentParent;
    private Camera mCamera;
    private List<Bitmap> mPictures;
    private FastItemAdapter<PictureItem> pictureItemAdapter;
    Unbinder unbinder;

    public CreateControlFragment() {
        // Required empty public constructor
    }

    public static CreateControlFragment newInstance(ControlFragment fragmentParent, CustomProgress customProgress, String key, String title, int nbMaxInCategory, Map<String, List<Control>> controls) {

        CreateControlFragment fragment = new CreateControlFragment();
        fragment.mFragmentParent = fragmentParent;
        fragment.mCustomProgress = customProgress;
        fragment.mKey = key;
        fragment.mTitle = title;
        fragment.mNbMaxInCategory = nbMaxInCategory;
        fragment.mControls = controls;
        fragment.mPictures = new ArrayList<>();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_create_control, container, false);
        ButterKnife.bind(this, view);

        createControlTitleTextView.setText(mTitle);
        createControlRatingBar.setRating(1);
        createControlRatingBar.setStepSize(1);
        createControlRatingTextView.setText("Très mauvais");
        createControlRatingBar.setOnRatingBarChangeListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        pictureRecyclerView.setLayoutManager(layoutManager);
        pictureItemAdapter = new FastItemAdapter<>();
        pictureRecyclerView.setAdapter(pictureItemAdapter);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.create_control_validate_button)
    public void onValidate() {

        String detail = createControlDetailEditText.getText().toString();
        String notice = createControlNoticeEditText.getText().toString();

        if (!detail.isEmpty() && !notice.isEmpty()) {

            Control newControl = new Control();
            newControl.setDetail(detail);
            newControl.setComment(notice);
            newControl.setRating(createControlRatingBar.getNumStars());
            if (mControls.get(mKey) == null) {
                mControls.put(mKey, new ArrayList<Control>());
            }
            mControls.get(mKey).add(newControl);
            mFragmentParent.setCustomProgress(mCustomProgress, mTitle, mControls.get(mKey), mNbMaxInCategory);
            mFragmentParent.updateAfterCreateControl();
            getActivity().getSupportFragmentManager().popBackStackImmediate();
        }
    }

    @OnClick(R.id.create_control_take_picture_button)
    public void onTakeAPicture() {

        PackageManager packageManager = getContext().getPackageManager();
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA) == false) {
            Toast.makeText(getActivity(), "Ce smarphone n'a pas de caméra.", Toast.LENGTH_SHORT)
                    .show();
            return;
        }


        mCamera = new Camera.Builder()
                .resetToCorrectOrientation(true)// it will rotate the camera bitmap to the correct orientation from meta data
                .setTakePhotoRequestCode(1)
                .setDirectory("pics")
                .setName("ali_" + System.currentTimeMillis())
                .setImageFormat(Camera.IMAGE_JPEG)
                .setCompression(75)
                .setImageHeight(250)// it will try to achieve this height as close as possible maintaining the aspect ratio;
                .build(this);

        try {
            mCamera.takePicture();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Camera.REQUEST_TAKE_PHOTO) {
            Bitmap bitmap = mCamera.getCameraBitmap();
            if (bitmap != null) {
                mPictures.add(bitmap);
                pictureItemAdapter.add(new PictureItem(bitmap));
                pictureItemAdapter.notifyAdapterDataSetChanged();
            } else {
                Toast.makeText(getContext(), "La photo n'a pas été validée.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        if (mCamera != null){
            mCamera.deleteImage();
        }
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

        int intRating = (int) rating;

        switch (intRating) {

            case 1:
                createControlRatingTextView.setText("Très mauvais");
                break;
            case 2:
                createControlRatingTextView.setText("Mauvais");
                break;
            case 3:
                createControlRatingTextView.setText("Acceptable");
                break;
            case 4:
                createControlRatingTextView.setText("Bon");
                break;
            case 5:
                createControlRatingTextView.setText("Excellent");
                break;
        }
    }

    public void updateViewFromParent() {

        //mFragmentParent.setCustomProgress(mCustomProgress, mTitle, mControls.get(mKey), mNbMaxInCategory);
    }

}
