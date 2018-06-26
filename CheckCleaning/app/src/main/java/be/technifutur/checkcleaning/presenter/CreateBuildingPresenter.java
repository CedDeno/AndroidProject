package be.technifutur.checkcleaning.presenter;

import be.technifutur.checkcleaning.activity.CreateBuildingActivity;
import be.technifutur.checkcleaning.entity.Structure;
import be.technifutur.checkcleaning.listener.OnCreateBuildingFinishedListener;
import be.technifutur.checkcleaning.entity.Building;
import be.technifutur.checkcleaning.entity.User;
import be.technifutur.checkcleaning.repository.BuildingRepository;
import be.technifutur.checkcleaning.repository.UserRepository;

public class CreateBuildingPresenter implements OnCreateBuildingFinishedListener {

    private CreateBuildingActivity mView;
    private UserRepository userRepo;
    private BuildingRepository buildingRepo;
    private User mUser;

    public CreateBuildingPresenter(User user, CreateBuildingActivity mView) {
        this.mView = mView;
        userRepo = new UserRepository();
        buildingRepo = new BuildingRepository();
        mUser = user;
    }

    public void prepareNewBuilding(String name, String address, Structure struct) {

        Building building = new Building();
        building.setName(name);
        building.setAddress(address);
        building.setStructure(struct);
        buildingRepo.createBuilding(building, this);
    }

    /**
     * Ajoute le Building à la liste du User et retourne la liste afin de l'insérer en DB.
     * (obligatoire avec Firebase de repousser toute la liste et d'écraser l'ancienne)
     * @param buildingId
     */
    @Override
    public void onCreateBuildingSuccess(String buildingId) {
        mUser.getBuildings_id().add(buildingId);
        userRepo.addBuildingToUser(mUser.getBuildings_id(), this);
    }

    /**
     * Listener afin d'appeler la vue à se fermer.
     */
    @Override
    public void onAddBuildingToUser() {
        mView.closeActivity();
    }
}
