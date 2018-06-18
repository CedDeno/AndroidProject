package be.technifutur.checkcleaning.listener;

import java.util.List;
import java.util.Map;

import be.technifutur.checkcleaning.entity.Control;

public interface OnGetControlsFinishedListener {

    void onGetControlsSuccess(Map<String, List<Control>> controls);
}
