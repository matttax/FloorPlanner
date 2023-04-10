package domain;

import lombok.Data;

@Data
public class Floor {
    long buildingId;
    int floorNumber;
    String map;
}
