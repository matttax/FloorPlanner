CREATE TABLE IF NOT EXISTS buildings(
    building_id     INTEGER PRIMARY KEY,
    name            VARCHAR(50) NOT NULL,
    address         VARCHAR(100),
    category_tree   LONGTEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS floors(
    building_id     INTEGER NOT NULL REFERENCES buildings(building_id),
    floor_number    INTEGER NOT NULL,
    map             LONGTEXT NOT NULL,
    PRIMARY KEY (building_id, floor_number)
);