package be.technifutur.checkcleaning.listener;

import java.util.List;
import java.util.Map;

import be.technifutur.checkcleaning.entity.Control;
import be.technifutur.checkcleaning.entity.ControlDB;

public interface OnGetControlsFinishedListener {

    void onGetControlsSuccess(List<ControlDB> controls);
}
