package be.technifutur.checkcleaning.fragment;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mindorks.paracamera.Camera;

import be.technifutur.checkcleaning.R;
import be.technifutur.checkcleaning.activity.BottomBarActivity;
import be.technifutur.checkcleaning.entity.Building;
import be.technifutur.checkcleaning.item.PictureItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportFragment extends Fragment {

    @BindView(R.id.report_place_editText)
    EditText reportPlaceEditText;
    @BindView(R.id.report_comment_editText)
    EditText reportCommentEditText;
    @BindView(R.id.report_picture_imageView)
    ImageView reportPictureImageView;
    Unbinder unbinder;
    private BottomBarActivity mActivity;
    private Building mBuilding;
    private Camera mCamera;

    public ReportFragment() {

    }

    public static ReportFragment newInstance(BottomBarActivity activity, Building building) {
        ReportFragment fragment = new ReportFragment();
        fragment.mBuilding = building;
        fragment.mActivity = activity;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_report, container, false);
        //getActivity().setTitle("Rapport journalier");

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.report_picture_button)
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
    public void onPause() {
        reportCommentEditText.clearFocus();
        reportPlaceEditText.clearFocus();
        super.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Camera.REQUEST_TAKE_PHOTO) {
            Bitmap bitmap = mCamera.getCameraBitmap();
            if (bitmap != null) {
                reportPictureImageView.setImageBitmap(bitmap);
            } else {
                Toast.makeText(getContext(), "La photo n'a pas été validée.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
