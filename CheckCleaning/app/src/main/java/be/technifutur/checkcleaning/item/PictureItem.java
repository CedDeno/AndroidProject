package be.technifutur.checkcleaning.item;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import be.technifutur.checkcleaning.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PictureItem extends AbstractItem<PictureItem, PictureItem.ViewHolder> {

    private Bitmap imageBitmap;

    public PictureItem(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    @NonNull
    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public int getType() {
        return R.id.picture_imageView;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.cell_picture;
    }

    public class ViewHolder extends FastAdapter.ViewHolder<PictureItem> implements View.OnClickListener {

        @BindView(R.id.picture_imageView)
        ImageView pictureImageView;

        private boolean isImageFitToScreen;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            pictureImageView.setOnClickListener(this);
            isImageFitToScreen = false;
        }

        @Override
        public void onClick(View v) {

            if(isImageFitToScreen) {
                isImageFitToScreen=false;
                pictureImageView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));
                pictureImageView.setAdjustViewBounds(true);
            }else{
                isImageFitToScreen=true;
                pictureImageView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
                pictureImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }

        @Override
        public void bindView(PictureItem item, List<Object> payloads) {
            pictureImageView.setImageBitmap(item.getImageBitmap());
        }

        @Override
        public void unbindView(PictureItem item) {
            pictureImageView.setImageResource(0);
        }
    }
}
