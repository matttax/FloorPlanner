import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import jep.*;
import service.db.DbInit;
import service.db.JdbcTemplate;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

    static {
        String pythonFolder = "/home/matttax/IdeaProjects/FloorPlanner/JEP";
        String jepPath = pythonFolder + "/jep/libjep.jnilib";
        if (!Files.exists(Path.of(jepPath))){
            jepPath = pythonFolder + "/jep/libjep.so";
        }
        MainInterpreter.setJepLibraryPath(jepPath);
        jep.JepConfig jepConf = new JepConfig();
        jepConf.addIncludePaths("/src/main/java/");
        jepConf.addIncludePaths(pythonFolder);
        jepConf.redirectStdout(System.out);
        SharedInterpreter.setConfig(jepConf);
    }

    public static void main(String[] args) throws IOException, JepException, SQLException {
        MysqlConnectionPoolDataSource pool = new MysqlConnectionPoolDataSource();
        JdbcTemplate source = new JdbcTemplate(pool);
        DbInit dbInit = new DbInit(source);
        dbInit.create();
        new MapFrame("test/map1.jpg");
    }

    public static ArrayList<ArrayList<Long>> getMatrix(String file) {
        SharedInterpreter subInterp = new SharedInterpreter();
        subInterp.eval("path = '" + file + "'");
        subInterp.runScript("src/main/PythonScripts/image_to_map_script.py");
        ArrayList<ArrayList<Long>> mapMatrix = (ArrayList<ArrayList<Long>>) subInterp.getValue("matrix");
        subInterp.close();
        return mapMatrix;
    }

    public static ArrayList<ArrayList<Integer>> mergeZones(String file, Point p1, Point p2) {
        SharedInterpreter subInterp = new SharedInterpreter();
        subInterp.eval(String.format("point1 = (%d, %d)", p1.y, p1.x));
        subInterp.eval(String.format("point2 = (%d, %d)", p2.y, p2.x));
        subInterp.eval("path = '" + file + "'");
        subInterp.runScript("src/main/PythonScripts/merge_zones_script.py");
        ArrayList<ArrayList<Integer>> lines = (ArrayList<ArrayList<Integer>>) subInterp.getValue("lines");
        subInterp.close();
        return lines;
    }
}
