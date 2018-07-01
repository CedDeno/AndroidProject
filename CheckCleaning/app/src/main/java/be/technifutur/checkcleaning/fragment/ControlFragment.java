package be.technifutur.checkcleaning.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import be.technifutur.checkcleaning.R;
import be.technifutur.checkcleaning.Util.CustomProgress;
import be.technifutur.checkcleaning.activity.BottomBarActivity;
import be.technifutur.checkcleaning.entity.Building;
import be.technifutur.checkcleaning.entity.Control;
import be.technifutur.checkcleaning.entity.ControlDB;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ControlFragment extends Fragment implements View.OnClickListener {

    Unbinder unbinder;
    @BindView(R.id.custom_progress_kitchenette)
    CustomProgress customProgressKitchenette;
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
    @BindView(R.id.custom_progress_shower)
    CustomProgress customProgressShower;
    @BindView(R.id.custom_progress_parking)
    CustomProgress customProgressParking;


    private BottomBarActivity mActivity;
    private Building mBuilding;
    private Map<String, List<Control>> mControls;
    private Map<String, Integer> mControlNb;

    public ControlFragment() {

    }

    public static ControlFragment newInstance(BottomBarActivity activity, Building building) {

        ControlFragment fragment = new ControlFragment();
        fragment.mActivity = activity;
        fragment.mBuilding = building;
        fragment.mControls = new HashMap<>();
        fragment.mControlNb = new HashMap<>();
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

        customProgressKitchenette.setOnClickListener(this);
        customProgressParking.setOnClickListener(this);
        customProgressShower.setOnClickListener(this);
        customProgressWc.setOnClickListener(this);
        customProgressRestaurant.setOnClickListener(this);
        customProgressRelaxationArea.setOnClickListener(this);
        customProgressOpenSpace.setOnClickListener(this);
        customProgressOffice.setOnClickListener(this);
        customProgressMeetingRoom.setOnClickListener(this);

        initControlNbMap();

        setCustomProgress(customProgressKitchenette, "Kitchenette", mControls.get(getString(R.string.kitchenette_category_key)), mControlNb.get("kitchenette"));
        setCustomProgress(customProgressMeetingRoom, "Salle de réunion", mControls.get(getString(R.string.meeting_room_category_key)), mControlNb.get("meeting_room"));
        setCustomProgress(customProgressOffice, "Bureau", mControls.get(getString(R.string.office_category_key)), mControlNb.get("office"));
        setCustomProgress(customProgressOpenSpace, "Espace libre", mControls.get(getString(R.string.open_space_category_key)), mControlNb.get("open_space"));
        setCustomProgress(customProgressRelaxationArea, "Zone détente", mControls.get(getString(R.string.relaxation_area_category_key)), mControlNb.get("relaxation_area"));
        setCustomProgress(customProgressRestaurant, "Restaurant", mControls.get(getString(R.string.restaurant_category_key)), mControlNb.get("restaurant"));
        setCustomProgress(customProgressWc, "W.C.", mControls.get(getString(R.string.wc_category_key)), mControlNb.get("wc"));
        setCustomProgress(customProgressShower, "Douche", mControls.get(getString(R.string.shower_category_key)), mControlNb.get("shower"));
        setCustomProgress(customProgressParking, "Parking", mControls.get(getString(R.string.parkin_category_key)), mControlNb.get("parking"));

        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    public void setCustomProgress(CustomProgress cp, String name, List<Control> controls, int maxNb) {

        if (maxNb == 0) {

            cp.setOnClickListener(null);
            cp.setProgressBackgroundColor(getResources().getColor(R.color.colorGrey));
            cp.setText(name);
        }else{
            float floatNb = 0;

            if (controls != null){
                floatNb = ((float) controls.size() / ((float) maxNb));
            }
            cp.setMaximumPercentage(floatNb);
            cp.setText(name);
        }
    }

    public void prepareCustomsProgress() {
        customProgressKitchenette.setProgressColor(getResources().getColor(R.color.buttonColor));
        customProgressKitchenette.setProgressBackgroundColor(getResources().getColor(R.color.buttonColorLight));
        customProgressKitchenette.useRoundedRectangleShape(5.0f);
        customProgressKitchenette.setShowingPercentage(false);
        customProgressKitchenette.setTextSize(20);
        customProgressKitchenette.setTextColor(getResources().getColor(R.color.colorWhite));
        customProgressKitchenette.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);

        customProgressMeetingRoom.setProgressColor(getResources().getColor(R.color.buttonColor));
        customProgressMeetingRoom.setProgressBackgroundColor(getResources().getColor(R.color.buttonColorLight));
        customProgressMeetingRoom.useRoundedRectangleShape(5.0f);
        customProgressMeetingRoom.setShowingPercentage(false);
        customProgressMeetingRoom.setTextSize(20);
        customProgressMeetingRoom.setTextColor(getResources().getColor(R.color.colorWhite));
        customProgressMeetingRoom.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);

        customProgressOffice.setProgressColor(getResources().getColor(R.color.buttonColor));
        customProgressOffice.setProgressBackgroundColor(getResources().getColor(R.color.buttonColorLight));
        customProgressOffice.useRoundedRectangleShape(5.0f);
        customProgressOffice.setShowingPercentage(false);
        customProgressOffice.setTextSize(20);
        customProgressOffice.setTextColor(getResources().getColor(R.color.colorWhite));
        customProgressOffice.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);

        customProgressOpenSpace.setProgressColor(getResources().getColor(R.color.buttonColor));
        customProgressOpenSpace.setProgressBackgroundColor(getResources().getColor(R.color.buttonColorLight));
        customProgressOpenSpace.useRoundedRectangleShape(5.0f);
        customProgressOpenSpace.setShowingPercentage(false);
        customProgressOpenSpace.setTextSize(20);
        customProgressOpenSpace.setTextColor(getResources().getColor(R.color.colorWhite));
        customProgressOpenSpace.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);

        customProgressRelaxationArea.setProgressColor(getResources().getColor(R.color.buttonColor));
        customProgressRelaxationArea.setProgressBackgroundColor(getResources().getColor(R.color.buttonColorLight));
        customProgressRelaxationArea.useRoundedRectangleShape(5.0f);
        customProgressRelaxationArea.setShowingPercentage(false);
        customProgressRelaxationArea.setTextSize(20);
        customProgressRelaxationArea.setTextColor(getResources().getColor(R.color.colorWhite));
        customProgressRelaxationArea.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);

        customProgressRestaurant.setProgressColor(getResources().getColor(R.color.buttonColor));
        customProgressRestaurant.setProgressBackgroundColor(getResources().getColor(R.color.buttonColorLight));
        customProgressRestaurant.useRoundedRectangleShape(5.0f);
        customProgressRestaurant.setShowingPercentage(false);
        customProgressRestaurant.setTextSize(20);
        customProgressRestaurant.setTextColor(getResources().getColor(R.color.colorWhite));
        customProgressRestaurant.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);

        customProgressWc.setProgressColor(getResources().getColor(R.color.buttonColor));
        customProgressWc.setProgressBackgroundColor(getResources().getColor(R.color.buttonColorLight));
        customProgressWc.useRoundedRectangleShape(5.0f);
        customProgressWc.setShowingPercentage(false);
        customProgressWc.setTextSize(20);
        customProgressWc.setTextColor(getResources().getColor(R.color.colorWhite));
        customProgressWc.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);

        customProgressShower.setProgressColor(getResources().getColor(R.color.buttonColor));
        customProgressShower.setProgressBackgroundColor(getResources().getColor(R.color.buttonColorLight));
        customProgressShower.useRoundedRectangleShape(5.0f);
        customProgressShower.setShowingPercentage(false);
        customProgressShower.setTextSize(20);
        customProgressShower.setTextColor(getResources().getColor(R.color.colorWhite));
        customProgressShower.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);

        customProgressParking.setProgressColor(getResources().getColor(R.color.buttonColor));
        customProgressParking.setProgressBackgroundColor(getResources().getColor(R.color.buttonColorLight));
        customProgressParking.useRoundedRectangleShape(5.0f);
        customProgressParking.setShowingPercentage(false);
        customProgressParking.setTextSize(20);
        customProgressParking.setTextColor(getResources().getColor(R.color.colorWhite));
        customProgressParking.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {

        String title;
        String key;
        int nbMaxInCategory;

        switch (view.getId()) {

            case R.id.custom_progress_kitchenette:
                title = getString(R.string.kitchenette_category_title);
                key = getString(R.string.kitchenette_category_key);
                nbMaxInCategory = mBuilding.getStructure().getKitchenette();
                break;

            case R.id.custom_progress_meeting_room:
                title = getString(R.string.meeting_room_category_title);
                key = getString(R.string.meeting_room_category_key);
                nbMaxInCategory = mBuilding.getStructure().getMeeting_room();
                break;

            case R.id.custom_progress_office:
                title = getString(R.string.office_category_title)
                ;
                key = getString(R.string.office_category_key);
                nbMaxInCategory = mBuilding.getStructure().getOffice();
                break;

            case R.id.custom_progress_open_space:
                title = getString(R.string.open_space_category_title);
                key = getString(R.string.open_space_category_key);
                nbMaxInCategory = mBuilding.getStructure().getOpen_space();
                break;

            case R.id.custom_progress_relaxation_area:
                title = getString(R.string.relaxation_area_category_title);
                key = getString(R.string.relaxation_area_category_key);
                nbMaxInCategory = mBuilding.getStructure().getRelaxation_area();
                break;

            case R.id.custom_progress_restaurant:
                title = getString(R.string.restaurant_category_title);
                key = getString(R.string.restaurant_category_key);
                nbMaxInCategory = mBuilding.getStructure().getRestaurant();
                break;

            case R.id.custom_progress_wc:
                title = getString(R.string.wc_category_title);
                key = getString(R.string.wc_category_key);
                nbMaxInCategory = mBuilding.getStructure().getWc();
                break;
            case R.id.custom_progress_shower:
                title = getString(R.string.shower_category_title);
                key = getString(R.string.shower_category_key);
                nbMaxInCategory = mBuilding.getStructure().getShower();
                break;
            case R.id.custom_progress_parking:
                title = getString(R.string.parking_category_title);
                key = getString(R.string.parkin_category_key);
                nbMaxInCategory = mBuilding.getStructure().getParking();
                break;

            default:
                title = "";
                key = "";
                nbMaxInCategory = 0;
        }

        Fragment fragment = CreateControlFragment.newInstance(this, (CustomProgress) view, key, title, nbMaxInCategory, mControls);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.animator.fade_in_bottom, android.R.animator.fade_out);
        ft.replace(R.id.fragment_root_control_id, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
        mActivity.setFragmentIsOpen(true);
        mActivity.getViewPager().setEnable(false);
    }

    private void initControlNbMap() {

        int roomCount = mBuilding.getStructure().getKitchenette();
        if (roomCount == 0){
            mControlNb.put("kitchenette", 0);
        }else if(roomCount < 10) {
            mControlNb.put("kitchenette", 1);
        }else{
            mControlNb.put("kitchenette", roomCount / 10);
        }

        roomCount = mBuilding.getStructure().getMeeting_room();
        if (roomCount == 0){
            mControlNb.put("meeting_room", 0);
        }else if(roomCount < 10) {
            mControlNb.put("meeting_room", 1);
        }else{
            mControlNb.put("meeting_room", roomCount / 10);
        }

        roomCount = mBuilding.getStructure().getOffice();
        if (roomCount == 0){
            mControlNb.put("office", 0);
        }else if(roomCount < 10) {
            mControlNb.put("office", 1);
        }else{
            mControlNb.put("office", roomCount / 10);
        }

        roomCount = mBuilding.getStructure().getOpen_space();
        if (roomCount == 0){
            mControlNb.put("open_space", 0);
        }else if(roomCount < 10) {
            mControlNb.put("open_space", 1);
        }else{
            mControlNb.put("open_space", roomCount / 10);
        }

        roomCount = mBuilding.getStructure().getParking();
        if (roomCount == 0){
            mControlNb.put("parking", 0);
        }else if(roomCount < 10) {
            mControlNb.put("parking", 1);
        }else{
            mControlNb.put("parking", roomCount / 10);
        }

        roomCount = mBuilding.getStructure().getRelaxation_area();
        if (roomCount == 0){
            mControlNb.put("relaxation_area", 0);
        }else if(roomCount < 10) {
            mControlNb.put("relaxation_area", 1);
        }else{
            mControlNb.put("relaxation_area", roomCount / 10);
        }

        roomCount = mBuilding.getStructure().getRestaurant();
        if (roomCount == 0){
            mControlNb.put("restaurant", 0);
        }else if(roomCount < 10) {
            mControlNb.put("restaurant", 1);
        }else{
            mControlNb.put("restaurant", roomCount / 10);
        }

        roomCount = mBuilding.getStructure().getShower();
        if (roomCount == 0){
            mControlNb.put("shower", 0);
        }else if(roomCount < 10) {
            mControlNb.put("shower", 1);
        }else{
            mControlNb.put("shower", roomCount / 10);
        }

        roomCount = mBuilding.getStructure().getWc();
        if (roomCount == 0){
            mControlNb.put("wc", 0);
        }else if(roomCount < 10) {
            mControlNb.put("wc", 1);
        }else{
            mControlNb.put("wc", roomCount / 10);
        }
    }

    public void updateAfterCreateControl(){
        mActivity.setFragmentIsOpen(false);
        mActivity.getViewPager().setEnable(true);
    }

}
