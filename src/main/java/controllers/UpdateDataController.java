package controllers;

import domain.Building;
import domain.Zone;

public class UpdateDataController {
    Building building;
    Zone zone;
    public UpdateDataController(Building building, Zone zone) {
        this.building = building;
        this.zone = zone;
    }
}
