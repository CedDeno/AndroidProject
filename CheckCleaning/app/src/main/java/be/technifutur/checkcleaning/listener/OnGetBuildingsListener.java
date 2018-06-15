package be.technifutur.checkcleaning.listener;

import java.util.List;
import java.util.Map;

import be.technifutur.checkcleaning.entity.Building;

public interface OnGetBuildingsListener {

    void onGetBuildingsSuccess(List<Building> buildings);
}
