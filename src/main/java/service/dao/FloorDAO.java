package service.dao;

import domain.Floor;
import org.springframework.expression.ParseException;
import service.db.JdbcTemplate;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FloorDAO extends AbstractDAO<Floor, String[]> {
    public FloorDAO(JdbcTemplate source) {
        super(source);
        tableName = "floors";
    }

    @Override
    public Floor getEntity(String[] key) throws SQLException, ParseException, IOException {
        return source.statement(
                stat -> {
                    ResultSet resSet = stat.executeQuery("SELECT * FROM " + tableName
                            + " WHERE building_id=" + key[0] + " AND floor_number=" + key[1]);
                    if (resSet.next()) {
                        return getObject(resSet);
                    }
                    return null;
                }
        );
    }

    @Override
    public void insert(Floor floor) throws SQLException, IOException {
        source.preparedStatement("INSERT INTO " + tableName + " VALUES (?, ?, ?)",
                i -> {
                    i.setLong(1, floor.getBuildingId());
                    i.setInt(2, floor.getFloorNumber());
                    i.setString(3, floor.getMap());
                    i.execute();
                }
        );
    }

    @Override
    public void delete(String[] key) throws SQLException, IOException {
        source.statement(stat -> {
            stat.executeQuery("DELETE * FROM " + tableName
                    + " WHERE building_id=" + key[0] + " AND floor_number=" + key[1]);
        });
    }

    @Override
    protected Floor getObject(String[] fields) {
        return new Floor(Long.parseLong(fields[0]), Integer.parseInt(fields[1]), fields[2]);
    }

    @Override
    protected Floor getObject(ResultSet resSet) throws SQLException {
        long buildingId = resSet.getLong("building_id");
        int floorNumber = resSet.getInt("floor_number");
        String map = resSet.getString("map");
        return new Floor(buildingId, floorNumber, map);
    }
}
