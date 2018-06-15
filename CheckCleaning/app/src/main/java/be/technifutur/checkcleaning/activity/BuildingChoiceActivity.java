package be.technifutur.checkcleaning.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.NumberPicker;
import java.util.List;
import be.technifutur.checkcleaning.R;
import be.technifutur.checkcleaning.entity.Building;
import be.technifutur.checkcleaning.entity.User;
import be.technifutur.checkcleaning.presenter.BuildingChoicePresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BuildingChoiceActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    @BindView(R.id.picker_building)
    NumberPicker pickerBuilding;

    private BuildingChoicePresenter mPresenter;
    private User mUser;
    private List<Building> mBuildings;
    private Building selectedBuilding;
    private boolean isNewBuilding;
    private final int CREATION_BUILD_REQUEST_CODE = 145;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_choice);
        ButterKnife.bind(this);

        setTitle("Selection d'un chantier");

        mUser = getIntent().getExtras().getParcelable("user");
        isNewBuilding = getIntent().getBooleanExtra("isNewBuilding", false);
        mPresenter = new BuildingChoicePresenter(this);
        mPresenter.loadBuildingsByUser(mUser.getBuildings_id());
        pickerBuilding.setOnValueChangedListener(this);
    }

    @OnClick(R.id.btnChoiceBuilding)
    public void onBuildingSelect() {

        Intent intent = new Intent(this, BottomBarActivity.class);
        intent.putExtra("user", mUser);
        intent.putExtra("building", selectedBuilding);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btnCreateBuilding)
    public void onCreateButtonPress() {

        Intent intent = new Intent(this, CreateBuildingActivity.class);
        intent.putExtra("user", mUser);
        startActivity(intent);
    }

    public void loadPicker(List<Building> buildings) {

        mBuildings = buildings;

        int size = mBuildings.size();

        if (size > 0) {
            pickerBuilding.setMinValue(0);
            pickerBuilding.setMaxValue(size - 1);
            String[] myArray = new String[size];

            for (int i = 0; i < size; i++) {
                myArray[i] = mBuildings.get(i).getName();
            }

            pickerBuilding.setWrapSelectorWheel(true);
            pickerBuilding.setDisplayedValues(myArray);

            if (isNewBuilding){
                selectedBuilding = mBuildings.get(size - 1);
                pickerBuilding.setValue(size - 1);
                isNewBuilding = false;
            }else{
                selectedBuilding = mBuildings.get(0);
            }
        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        System.out.println("isNewBuilding = " + isNewBuilding);
        System.out.println("new Val = " + newVal);
        selectedBuilding = mBuildings.get(newVal);
    }
}
