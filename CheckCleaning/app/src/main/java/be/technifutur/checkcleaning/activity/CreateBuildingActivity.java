package be.technifutur.checkcleaning.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import be.technifutur.checkcleaning.R;
import be.technifutur.checkcleaning.entity.Structure;
import be.technifutur.checkcleaning.entity.User;
import be.technifutur.checkcleaning.presenter.CreateBuildingPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateBuildingActivity extends AppCompatActivity {

    @BindView(R.id.build_name_editText)
    EditText buildNameEditText;
    @BindView(R.id.build_address_editText)
    EditText buildAddressEditText;
    @BindView(R.id.build_cp_editText)
    EditText buildCpEditText;
    @BindView(R.id.build_city_editText)
    EditText buildCityEditText;
    @BindView(R.id.kitchenette_count_editText)
    EditText kitchenetteCountEditText;
    @BindView(R.id.meeting_room_count_editText)
    EditText meetingRoomCountEditText;
    @BindView(R.id.office_count_editText)
    EditText officeCountEditText;
    @BindView(R.id.open_space_count_editText)
    EditText openSpaceCountEditText;
    @BindView(R.id.relaxation_area_count_editText)
    EditText relaxationAreaCountEditText;
    @BindView(R.id.restaurant_count_editText)
    EditText restaurantCountEditText;
    @BindView(R.id.wc_count_editText)
    EditText wcCountEditText;
    @BindView(R.id.shower_count_editText)
    EditText showerCountEditText;
    @BindView(R.id.parking_count_editText)
    EditText parkingCountEditText;

    private CreateBuildingPresenter mPresenter;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_building);
        ButterKnife.bind(this);

        setTitle(getString(R.string.title_creationBuilding));
        mUser = getIntent().getExtras().getParcelable("user");
        mPresenter = new CreateBuildingPresenter(mUser, this);
    }

    @OnClick(R.id.build_create_button)
    public void onCreateButtonPress() {

        if (buildAddressEditText.getText().length() > 0 && buildNameEditText.getText().length() > 0) {

            String name = buildNameEditText.getText().toString();
            String address = buildAddressEditText.getText().toString() + ", " + buildCpEditText.getText().toString() + ", " + buildCityEditText.getText().toString();
            Structure struct = new Structure();
            struct.setKitchenette(Integer.parseInt(kitchenetteCountEditText.getText().toString()));
            struct.setMeeting_room(Integer.parseInt(meetingRoomCountEditText.getText().toString()));
            struct.setOffice(Integer.parseInt(officeCountEditText.getText().toString()));
            struct.setOpen_space(Integer.parseInt(openSpaceCountEditText.getText().toString()));
            struct.setParking(Integer.parseInt(parkingCountEditText.getText().toString()));
            struct.setRelaxation_area(Integer.parseInt(relaxationAreaCountEditText.getText().toString()));
            struct.setRestaurant(Integer.parseInt(restaurantCountEditText.getText().toString()));
            struct.setShower(Integer.parseInt(showerCountEditText.getText().toString()));
            struct.setWc(Integer.parseInt(wcCountEditText.getText().toString()));
            mPresenter.prepareNewBuilding(name, address, struct);
        } else {
            Toast.makeText(this, "Champs incorrects.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Crée un intentResult de BuildingChoiceActivity et renvoi un result_code et des datas
     * On récupère le building dans le nouvel intent via un putExtra et finish l'intent courant (CreateBuildingActivity)
     */

    public void closeActivity() {

        Intent intent = new Intent(this, BuildingChoiceActivity.class);
        intent.putExtra("user", mUser);
        intent.putExtra("isNewBuilding", true);
        startActivity(intent);

        /*Intent returnIntent = new Intent();
        returnIntent.putExtra("user", mUser);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
