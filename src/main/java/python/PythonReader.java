package python;

import jep.JepConfig;
import jep.MainInterpreter;
import jep.SharedInterpreter;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PythonReader {

    static {
        String currentFolder = Paths.get("").toAbsolutePath().toString();
        String pythonFolder = currentFolder + "/JEP";
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
