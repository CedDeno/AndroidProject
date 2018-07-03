package be.technifutur.checkcleaning.fragment;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import be.technifutur.checkcleaning.R;
import be.technifutur.checkcleaning.activity.BottomBarActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PDFReaderFragment extends Fragment {

    @BindView(R.id.pdfView)
    ImageView pdfView;
    Unbinder unbinder;
    @BindView(R.id.pdfReader_previous_button)
    Button previousButton;
    @BindView(R.id.pdfReader_next_button)
    Button nextButton;
    private int currentPageIndex;
    private File file;
    private BottomBarActivity mActivity;
    //File Descriptor for rendered Pdf file
    private ParcelFileDescriptor mFileDescriptor;
    //For rendering a PDF document
    private PdfRenderer mPdfRenderer;
    //For opening current page, render it, and close the page
    private PdfRenderer.Page mCurrentPage;

    public PDFReaderFragment() {
        // Required empty public constructor
    }

    public static PDFReaderFragment newInstance(BottomBarActivity activity, File file) {

        PDFReaderFragment fragment = new PDFReaderFragment();
        fragment.currentPageIndex = 0;
        fragment.file = file;
        fragment.mActivity = activity;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pdfreader, container, false);
        ButterKnife.bind(this, view);


        openRenderer(file);
        showPage(currentPageIndex);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void openRenderer(File file) {

        try {
            mFileDescriptor = ParcelFileDescriptor.open(file,
                    ParcelFileDescriptor.MODE_READ_ONLY);
            mPdfRenderer = new PdfRenderer(mFileDescriptor);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeRenderer() {

        try {
            if (mPdfRenderer != null)
                mPdfRenderer.close();
            if (mFileDescriptor != null)
                mFileDescriptor.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void showPage(int index) {
        if (mPdfRenderer == null || mPdfRenderer.getPageCount() <= index
                || index < 0) {
            return;
        }

        // Open page with specified index
        mCurrentPage = mPdfRenderer.openPage(index);
        Bitmap bitmap = Bitmap.createBitmap(mCurrentPage.getWidth(),
                mCurrentPage.getHeight(), Bitmap.Config.ARGB_8888);

        //Pdf page is rendered on Bitmap
        mCurrentPage.render(bitmap, null, null,
                PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        //Set rendered bitmap to ImageView

        BitmapDrawable bd = new BitmapDrawable(bitmap);
        pdfView.setImageDrawable(bd);
        updateActionBarText();
    }

    private void updateActionBarText() {
        int index = mCurrentPage.getIndex();
        int pageCount = mPdfRenderer.getPageCount();
        previousButton.setEnabled(0 != index);
        nextButton.setEnabled(index + 1 < pageCount);
    }

    @Override
    public void onDestroyView() {
        closeRenderer();
        unbinder.unbind();
        super.onDestroyView();
    }
}
