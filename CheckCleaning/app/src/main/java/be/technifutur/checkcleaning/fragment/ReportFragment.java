package be.technifutur.checkcleaning.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.mindorks.paracamera.Camera;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import be.technifutur.checkcleaning.R;
import be.technifutur.checkcleaning.activity.BottomBarActivity;
import be.technifutur.checkcleaning.entity.Building;
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
    @BindView(R.id.report_generate_button)
    Button reportGenerateButton;
    private BottomBarActivity mActivity;
    private Building mBuilding;
    private Camera mCamera;
    //For opening current page, render it, and close the page
    private PdfRenderer.Page mCurrentPage;
    //Currently rendered Pdf file
    private File openedPdfFile;
    private SimpleDateFormat sdf;

    public ReportFragment() {

    }

    public static ReportFragment newInstance(BottomBarActivity activity, Building building) {
        ReportFragment fragment = new ReportFragment();
        fragment.mBuilding = building;
        fragment.mActivity = activity;
        fragment.sdf = new SimpleDateFormat("dd-MM-yyyy");
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
                .setImageHeight(190)// it will try to achieve this height as close as possible maintaining the aspect ratio;
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

    public void loadPDFFragment(){

        Fragment fragment = PDFReaderFragment.newInstance(mActivity, openedPdfFile);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.animator.fade_in_bottom, android.R.animator.fade_out);
        ft.replace(R.id.fragment_root_report_id, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
        mActivity.setFragmentIsOpen(true);
        mActivity.getViewPager().setEnable(false);
        reportGenerateButton.setEnabled(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.report_generate_button)
    public void onGeneratePDF(){

        new PdfGenerationTask().execute();
        reportGenerateButton.setEnabled(false);
    }

    private class PdfGenerationTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            PdfDocument document = new PdfDocument();
            // repaint the user's text into the page
            String roomTitle = "Lieu";
            String roomDesc = reportPlaceEditText.getText().toString();
            String commentTitle = "Commentaire";
            String commentDesc = reportCommentEditText.getText().toString();
            View view = getView();

            // create a page description
            int pageNumber = 2;

            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(view.getWidth() - 16,
                    view.getHeight() - 36, pageNumber).create();

            // create a new page from the PageInfo
            PdfDocument.Page page = document.startPage(pageInfo);
            Canvas canvas = page.getCanvas();
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            paint.setARGB(255, 255, 255, 255);

            //Logo of app
            Bitmap bitmapLogo = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            canvas.drawBitmap(bitmapLogo, canvas.getWidth() - 230, 50, paint);

            //Name of application
            paint.setTextSize(55);
            paint.setColor(getResources().getColor(R.color.buttonColor));
            canvas.drawText("CheckClean",
                    canvas.getWidth() - 320, 260, paint);

            //Date of Report
            paint.setTextSize(30);
            paint.setColor(getResources().getColor(R.color.md_black_1000));
            canvas.drawText(sdf.format(Calendar.getInstance().getTime()),
                    canvas.getWidth() - 240, 300, paint);

            //Building's name of Report
            paint.setTextSize(50);
            paint.setColor(getResources().getColor(R.color.primary));
            canvas.drawText(mBuilding.getName(),60, 320, paint);

            //Room title
            paint.setTextSize(40);
            paint.setColor(getResources().getColor(R.color.md_black_1000));
            canvas.drawText(roomTitle, 60, 420, paint);

            //Room desc
            paint.setTextSize(30);
            paint.setColor(getResources().getColor(R.color.md_black_1000));
            canvas.drawText(roomDesc, 120, 510, paint);

            //Comment title
            paint.setTextSize(40);
            paint.setColor(getResources().getColor(R.color.md_black_1000));
            canvas.drawText(commentTitle, 60, 690, paint);

            //Comment desc
            paint.setTextSize(30);
            paint.setColor(getResources().getColor(R.color.md_black_1000));
            canvas.drawText(commentDesc, 120, 810, paint);

            //Picture title
            paint.setTextSize(40);
            paint.setColor(getResources().getColor(R.color.md_black_1000));
            canvas.drawText("Photo", 60, 1210, paint);

            //Picture image
            reportPictureImageView.buildDrawingCache();
            Bitmap bitmapPicture = reportPictureImageView.getDrawingCache();
            canvas.drawBitmap(bitmapPicture, 120, 1330, paint);

            // do final processing of the page
            document.finishPage(page);

            String pdfName = mBuilding.getName() + "_rapport_journalier_" + sdf.format(Calendar.getInstance().getTime()) + ".pdf";

            openedPdfFile = new File(getActivity().getFilesDir(), pdfName);

            try {
                openedPdfFile.createNewFile();
                OutputStream out = new FileOutputStream(openedPdfFile);
                document.writeTo(out);
                document.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return openedPdfFile.getPath();
        }

        @Override
        protected void onPostExecute(String filePath) {
            if (filePath != null) {

                loadPDFFragment();
            } else {
            }
        }

    }
}
