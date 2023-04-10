package service.dao;

import domain.Building;
import org.springframework.expression.ParseException;
import service.db.JdbcTemplate;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuildingDAO extends AbstractDAO<Building, Integer> {
    public BuildingDAO(JdbcTemplate source) {
        super(source);
        tableName = "buildings";
    }

    @Override
    public Building getEntity(Integer key) throws SQLException, ParseException, IOException {
        return source.statement(
                stat -> {
                    ResultSet resSet = stat.executeQuery("SELECT * FROM " + tableName + " WHERE building_id=" + key);
                    if (resSet.next()) {
                        return getObject(resSet);
                    }
                    return null;
                }
        );
    }

    @Override
    public void insert(Building building) throws SQLException, IOException {
        source.preparedStatement("INSERT IGNORE INTO " + tableName + " VALUES (?, ?, ?, ?)",
                i -> {
                    i.setLong(1, building.getBuildingId());
                    i.setString(2, building.getName());
                    i.setString(3, building.getAddress());
                    i.setString(4, building.getCategories());
                    i.execute();
                }
        );
    }

    @Override
    public void delete(Integer key) throws SQLException, IOException {
        source.statement(stat -> {
            stat.executeQuery("DELETE * FROM " + tableName + " WHERE building_id=" + key);
        });
    }

    @Override
    protected Building getObject(String[] fields) {
        return null;
    }

    @Override
    protected Building getObject(ResultSet resSet) throws SQLException {
        return null;
    }
}
