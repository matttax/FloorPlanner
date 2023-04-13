package controllers;

import domain.Zone;

public class AddProductsController {
    Zone zone;

    public AddProductsController(Zone zone) {
        this.zone = zone;
    }

    public Zone getZone() {
        return zone;
    }
}
