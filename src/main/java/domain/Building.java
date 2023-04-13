package domain;

import lombok.Data;

@Data
public class Building {
    long buildingId;
    String name;
    String address;
    String categories;

    public Building(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Building(long buildingId, String name, String address, String categories) {
        this(name, address);
        this.buildingId = buildingId;
        this.categories = categories;
    }
}
