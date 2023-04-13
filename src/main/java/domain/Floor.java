package domain;

import lombok.Data;

@Data
public class Floor {
    long buildingId;
    int floorNumber;
    String map;

    public Floor(long buildingId, int floorNumber, String map) {
        this.buildingId = buildingId;
        this.floorNumber = floorNumber;
        this.map = map;
    }
}
