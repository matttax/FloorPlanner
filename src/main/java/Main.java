import GUI.MapFrame;
import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import jep.*;
import service.dao.FloorDAO;
import service.db.DbInit;
import service.db.JdbcTemplate;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        MysqlConnectionPoolDataSource pool = new MysqlConnectionPoolDataSource();
        JdbcTemplate source = null;
        DbInit dbInit = null;
        try {
            source = new JdbcTemplate(pool);
            dbInit = new DbInit(source);
            dbInit.create();
        } catch (SQLException | IOException e) {
            System.err.println("Unable to connect to the database");
        }
        try {
            new MapFrame("test/map1.jpg");
        } catch (Exception e) {
            System.err.println("File not found");
        }
    }
}
