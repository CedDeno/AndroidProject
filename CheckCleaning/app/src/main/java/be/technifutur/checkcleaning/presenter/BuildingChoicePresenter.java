package be.technifutur.checkcleaning.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import be.technifutur.checkcleaning.activity.BuildingChoiceActivity;
import be.technifutur.checkcleaning.listener.OnGetBuildingsListener;
import be.technifutur.checkcleaning.entity.Building;
import be.technifutur.checkcleaning.repository.BuildingRepository;

public class BuildingChoicePresenter implements OnGetBuildingsListener{

    private final BuildingChoiceActivity mView;
    private final BuildingRepository mRepo;
    private List<Building> mBuildings;

    public BuildingChoicePresenter(BuildingChoiceActivity view) {
        this.mView = view;
        this.mRepo = new BuildingRepository();
    }

    public void loadBuildingsByUser(List<String> buildings_id) {

        mRepo.getByUserId(buildings_id, this);
    }

    public void onGetBuildingsSuccess(List<Building> buildings) {

        mBuildings = buildings;
        mView.loadPicker(buildings);
    }
}
