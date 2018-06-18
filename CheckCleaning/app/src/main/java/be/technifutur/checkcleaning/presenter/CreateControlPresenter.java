package be.technifutur.checkcleaning.presenter;

import be.technifutur.checkcleaning.entity.Control;
import be.technifutur.checkcleaning.fragment.CreateControlFragment;
import be.technifutur.checkcleaning.listener.OnCreateControlFinishedListener;
import be.technifutur.checkcleaning.repository.ControlRepository;

public class CreateControlPresenter implements OnCreateControlFinishedListener {

    private CreateControlFragment mView;
    private ControlRepository mRepo;
    private Control mControl;
    private String mBuildingId;

    public CreateControlPresenter(CreateControlFragment view, Control control) {
        this.mView = view;
        mRepo = new ControlRepository();
        mControl = control;
    }

    public void createControl(String category){

        mRepo.addControlToCategory(mBuildingId, category, mControl, this);
    }

    @Override
    public void onCreateControlSuccess() {

        mView.updateViewFromParent();
    }
}
