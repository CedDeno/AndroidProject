package be.technifutur.checkcleaning.listener;

import java.util.List;
import be.technifutur.checkcleaning.entity.Control;

public interface OnGetControlsFinishedListener {

    void onGetControlsSuccess(List<Control> controls);
}
