package be.technifutur.checkcleaning.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import be.technifutur.checkcleaning.R;
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

        if(buildAddressEditText.getText().length() > 0 && buildNameEditText.getText().length() > 0){

            String name = buildNameEditText.getText().toString();
            String address = buildAddressEditText.getText().toString();
            mPresenter.prepareNewBuilding(name, address);
        }else{
            Toast.makeText(this, "Champs incorrects.", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     *
     * @param building
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
