package domain;

import lombok.Data;

@Data
public class Building {
    long buildingId;
    String name;
    String address;
    String categories;
}
