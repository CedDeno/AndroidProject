package be.technifutur.checkcleaning.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import be.technifutur.checkcleaning.R;
import be.technifutur.checkcleaning.Util.CustomProgress;
import be.technifutur.checkcleaning.entity.Building;
import be.technifutur.checkcleaning.entity.Control;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ControlFragment extends Fragment implements View.OnClickListener {

    Unbinder unbinder;
    @BindView(R.id.custom_progress_cafetaria)
    CustomProgress customProgressCafetaria;
    @BindView(R.id.custom_progress_meeting_room)
    CustomProgress customProgressMeetingRoom;
    @BindView(R.id.custom_progress_office)
    CustomProgress customProgressOffice;
    @BindView(R.id.custom_progress_open_space)
    CustomProgress customProgressOpenSpace;
    @BindView(R.id.custom_progress_relaxation_area)
    CustomProgress customProgressRelaxationArea;
    @BindView(R.id.custom_progress_restaurant)
    CustomProgress customProgressRestaurant;
    @BindView(R.id.custom_progress_wc)
    CustomProgress customProgressWc;

    private Building mBuilding;
    private Map<String, List<Control>> mControls;

    public ControlFragment() {

    }

    public static ControlFragment newInstance(Building building, Map<String, List<Control>> controls) {

        ControlFragment fragment = new ControlFragment();
        fragment.mBuilding = building;
        fragment.mControls = controls;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control, container, false);
        ButterKnife.bind(this, view);

        prepareCustomsProgress();

        customProgressCafetaria.setOnClickListener(this);

        setCustomProgress(customProgressCafetaria, "Cafétaria", mControls.get("cafetaria").size(), mBuilding.getCafetaria_count());
        setCustomProgress(customProgressMeetingRoom, "Salle de réunion", mControls.get("meeting_room").size(), mBuilding.getMeeting_room_count());
        setCustomProgress(customProgressOffice, "Bureau", mControls.get("office").size(), mBuilding.getOffice_count());
        setCustomProgress(customProgressOpenSpace, "Espace libre", mControls.get("open_space").size(), mBuilding.getOpen_space_count());
        setCustomProgress(customProgressRelaxationArea, "Zone détente", mControls.get("relaxation_area").size(), mBuilding.getRelaxation_area_count());
        setCustomProgress(customProgressRestaurant, "Restaurant", mControls.get("restaurant").size(), mBuilding.getRestaurant_count());
        setCustomProgress(customProgressWc, "WC", mControls.get("wc").size(), mBuilding.getWc_count());

        unbinder = ButterKnife.bind(this, view);

        return view;
    }
    
    public void setCustomProgress(CustomProgress cp, String name, int currentNb, int maxNb){

        float floatNb = ((float)currentNb / (float)maxNb);
        cp.setMaximumPercentage(floatNb);
        cp.setText(name);
    }

    public void prepareCustomsProgress(){
        customProgressCafetaria.setProgressColor(getResources().getColor(R.color.buttonColor));
        customProgressCafetaria.setProgressBackgroundColor(getResources().getColor(R.color.buttonColorLight));
        customProgressCafetaria.useRoundedRectangleShape(30.0f);
        customProgressCafetaria.setShowingPercentage(false);
        customProgressCafetaria.setTextSize(20);
        customProgressCafetaria.setTextColor(getResources().getColor(R.color.colorWhite));
        customProgressCafetaria.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);

        customProgressMeetingRoom.setProgressColor(getResources().getColor(R.color.buttonColor));
        customProgressMeetingRoom.setProgressBackgroundColor(getResources().getColor(R.color.buttonColorLight));
        customProgressMeetingRoom.useRoundedRectangleShape(30.0f);
        customProgressMeetingRoom.setShowingPercentage(false);
        customProgressMeetingRoom.setTextSize(20);
        customProgressMeetingRoom.setTextColor(getResources().getColor(R.color.colorWhite));
        customProgressMeetingRoom.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);

        customProgressOffice.setProgressColor(getResources().getColor(R.color.buttonColor));
        customProgressOffice.setProgressBackgroundColor(getResources().getColor(R.color.buttonColorLight));
        customProgressOffice.useRoundedRectangleShape(30.0f);
        customProgressOffice.setShowingPercentage(false);
        customProgressOffice.setTextSize(20);
        customProgressOffice.setTextColor(getResources().getColor(R.color.colorWhite));
        customProgressOffice.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);

        customProgressOpenSpace.setProgressColor(getResources().getColor(R.color.buttonColor));
        customProgressOpenSpace.setProgressBackgroundColor(getResources().getColor(R.color.buttonColorLight));
        customProgressOpenSpace.useRoundedRectangleShape(30.0f);
        customProgressOpenSpace.setShowingPercentage(false);
        customProgressOpenSpace.setTextSize(20);
        customProgressOpenSpace.setTextColor(getResources().getColor(R.color.colorWhite));
        customProgressOpenSpace.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);

        customProgressRelaxationArea.setProgressColor(getResources().getColor(R.color.buttonColor));
        customProgressRelaxationArea.setProgressBackgroundColor(getResources().getColor(R.color.buttonColorLight));
        customProgressRelaxationArea.useRoundedRectangleShape(30.0f);
        customProgressRelaxationArea.setShowingPercentage(false);
        customProgressRelaxationArea.setTextSize(20);
        customProgressRelaxationArea.setTextColor(getResources().getColor(R.color.colorWhite));
        customProgressRelaxationArea.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);

        customProgressRestaurant.setProgressColor(getResources().getColor(R.color.buttonColor));
        customProgressRestaurant.setProgressBackgroundColor(getResources().getColor(R.color.buttonColorLight));
        customProgressRestaurant.useRoundedRectangleShape(30.0f);
        customProgressRestaurant.setShowingPercentage(false);
        customProgressRestaurant.setTextSize(20);
        customProgressRestaurant.setTextColor(getResources().getColor(R.color.colorWhite));
        customProgressRestaurant.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);

        customProgressWc.setProgressColor(getResources().getColor(R.color.buttonColor));
        customProgressWc.setProgressBackgroundColor(getResources().getColor(R.color.buttonColorLight));
        customProgressWc.useRoundedRectangleShape(30.0f);
        customProgressWc.setShowingPercentage(false);
        customProgressWc.setTextSize(20);
        customProgressWc.setTextColor(getResources().getColor(R.color.colorWhite));
        customProgressWc.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {

        String title;

        switch (v.getId()){

            case R.id.custom_progress_cafetaria :
                title = "Cafétaria";
                break;

            case R.id.custom_progress_meeting_room :
                title = "Salle de réunion";
                break;

            case R.id.custom_progress_office :
                title = "Bureau";
                break;

            case R.id.custom_progress_open_space :
                title = "Espace libre";
                break;

            case R.id.custom_progress_relaxation_area :
                title = "Zone détente";
                break;

            case R.id.custom_progress_restaurant :
                title = "Restaurant";
                break;

            case R.id.custom_progress_wc :
                title = "W.C.";
                break;
        }


    }
}
