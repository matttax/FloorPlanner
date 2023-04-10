import GUI.MapFrame;
import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import jep.*;
import service.db.DbInit;
import service.db.JdbcTemplate;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws IOException, JepException, SQLException {
        MysqlConnectionPoolDataSource pool = new MysqlConnectionPoolDataSource();
        JdbcTemplate source = new JdbcTemplate(pool);
        DbInit dbInit = new DbInit(source);
        dbInit.create();
        new MapFrame("test/map1.jpg");
    }
}
