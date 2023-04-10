package controllers;

import domain.Zone;
import python.PythonReader;

import java.awt.*;
import java.util.*;

public class MapController {
    ArrayList<Zone> zones;
    String file;

    public MapController(String file) {
        this.file = file;
        zones = new ArrayList<>();
    }

    public Zone getZone(Set<Point> selectedZone) {
        Zone currentZone = null;
        for (Zone z : zones) {
            if (selectedZone.contains(z.getPointOnMap())) {
                currentZone = z;
                break;
            }
        }
        if (currentZone == null) {
            currentZone = new Zone("zone " + (zones.size() + 1), selectedZone.iterator().next());
        }
        return currentZone;
    }

    public void mergeZones(Set<Point> selectedZone1, Set<Point> selectedZone2, ArrayList<ArrayList<Long>> matrix) {
        if (selectedZone1.isEmpty() && selectedZone2.isEmpty()) {
                return;
        }
        ArrayList<ArrayList<Integer>> lines = PythonReader.mergeZones(this.file + "_current.jpg",
                    selectedZone1.iterator().next(),
                    selectedZone2.iterator().next());
        selectedZone1.clear();
        selectedZone2.clear();
        for (var line : lines) {
            System.out.println(line);
            if (Objects.equals(line.get(0), line.get(2))) {
                for (int i = line.get(1); i <= line.get(3); i++) {
                    matrix.get(i).set(line.get(0), 0L);
                }
            } else {
                for (int i = line.get(0); i <= line.get(2); i++) {
                    matrix.get(line.get(1)).set(i, 0L);
                }
            }
        }
    }

    public String mapToXml(ArrayList<ArrayList<Long>> matrix) {
        StringBuilder sb = new StringBuilder();
        sb.append("<root>\n");
        sb.append("\t<map>\n");
        sb.append("\t\t<height>").append(matrix.size()).append("</height>\n");
        sb.append("\t\t<width>").append(matrix.get(0).size()).append("</width>\n");
        sb.append("\t\t<matrix>\n");
        for (ArrayList<Long> row : matrix) {
            sb.append("\t\t\t<row>").append(row.toString()
                    .replace("[", "").replace("]", ""))
                    .append("</row>\n");
        }
        sb.append("\t\t</matrix>\n");
        sb.append("\t</map>\n");
        sb.append("\t<zones>\n");
        for (Zone z : zones) {
            sb.append(z.toXml("\t")).append("\n");
        }
        sb.append("\t</zones>\n");
        sb.append("</root>\n");
        return sb.toString();
    }
}
