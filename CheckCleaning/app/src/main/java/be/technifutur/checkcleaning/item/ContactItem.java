package be.technifutur.checkcleaning.item;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;
import java.util.List;
import be.technifutur.checkcleaning.R;
import be.technifutur.checkcleaning.entity.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactItem extends AbstractItem<ContactItem, ContactItem.ViewHolder> {

    private final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private final int MY_PERMISSIONS_REQUEST_SEND_SMS = 2;
    private User user;
    private Activity activity;

    public ContactItem(User user, Activity activity) {
        this.user = user;
        this.activity = activity;
    }

    public User getUser() {
        return user;
    }

    @NonNull
    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public int getType() {
        return R.id.PersonTextView;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.cell_contact;
    }

    public class ViewHolder extends FastAdapter.ViewHolder<ContactItem> implements ActivityCompat.OnRequestPermissionsResultCallback{

        @BindView(R.id.PersonTextView)
        TextView personTextView;

        @BindView(R.id.MyCheckBox)
        CheckBox myCheckBox;

        @BindView(R.id.SmsImageButton)
        ImageButton smsImageButton;

        @BindView(R.id.PhoneImageButton)
        ImageButton phoneImageButton;

        private String phoneNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bindView(ContactItem item, List<Object> payloads) {

            personTextView.setText(item.user.getFirst_name() + " " + item.user.getLast_name());
            phoneNumber = item.user.getPhone_number();
        }

        @Override
        public void unbindView(ContactItem item) {

            personTextView.setText(null);
        }

        @OnClick(R.id.SmsImageButton)
        public void onSms() {
            System.out.println("ONSMS");
            Uri msgUri = Uri.parse("smsto:" + phoneNumber);
            Intent intent = new Intent(Intent.ACTION_SENDTO, msgUri);
            if (ActivityCompat.checkSelfPermission(itemView.getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
                return;
            }
            itemView.getContext().startActivity(intent);
        }

        @OnClick(R.id.PhoneImageButton)
        public void onCall() {

            System.out.println("ONCALL");
            Uri uri = Uri.parse("tel:" + phoneNumber);
            Intent intent = new Intent(Intent.ACTION_CALL, uri);
            if (ActivityCompat.checkSelfPermission(itemView.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
                return;
            }
            itemView.getContext().startActivity(intent);
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

            System.out.println("RESQUESTCODE = " + requestCode );

            System.out.println("GRANT RESULT = " + grantResults.toString());

            /*if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onClickCallNumber();
            } else {
                System.out.println("COUCOU");
            }*/
        }
    }
}
