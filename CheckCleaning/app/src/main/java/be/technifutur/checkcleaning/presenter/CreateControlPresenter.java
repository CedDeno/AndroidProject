package be.technifutur.checkcleaning.presenter;

import be.technifutur.checkcleaning.entity.Control;
import be.technifutur.checkcleaning.fragment.CreateControlFragment;
import be.technifutur.checkcleaning.listener.OnCreateControlFinishedListener;
import be.technifutur.checkcleaning.repository.ControlRepository;

public class CreateControlPresenter implements OnCreateControlFinishedListener {

    private CreateControlFragment mView;
    private ControlRepository mRepo;
    private String mBuildingId;

    public CreateControlPresenter(CreateControlFragment view) {
        this.mView = view;
        mRepo = new ControlRepository();
    }

    public void createControl(String category, Control newControl){

        mRepo.addControlToCategory(mBuildingId, category, newControl, this);
    }

    @Override
    public void onCreateControlSuccess() {

        mView.updateViewFromParent();
    }
}
