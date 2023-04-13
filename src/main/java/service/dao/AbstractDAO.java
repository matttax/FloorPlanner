package service.dao;

import org.springframework.expression.ParseException;
import service.db.JdbcTemplate;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractDAO<T, K> {
    protected final JdbcTemplate source;
    protected String tableName;

    public AbstractDAO(JdbcTemplate source) {
        this.source = source;
    }

    public Set<T> getAllData() throws SQLException, ParseException, IOException {
        return source.statement(
                stat -> {
                    Set<T> data = new HashSet<>();
                    ResultSet resSet = stat.executeQuery("SELECT * FROM floorplanner." + tableName);
                    while (resSet.next()) {
                        data.add(getObject(resSet));
                    }
                    return data;
                }
        );
    }

    public abstract T getEntity(K key) throws SQLException, ParseException, IOException;

    public abstract void insert(T entity) throws SQLException, IOException;

    public abstract void delete(K key) throws SQLException, IOException;

    protected abstract T getObject(String[] fields);
    protected abstract T getObject(ResultSet resSet) throws SQLException;
}
