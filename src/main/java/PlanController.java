import java.awt.*;
import java.util.*;

public class PlanController {
    Map<Point, Zone> zones;
    String file;

    public PlanController(String file) {
        this.file = file;
        zones = new HashMap<>();
    }

    public Zone getZone(Set<Point> selectedZone) {
        Zone currentZone = null;
        for (Point p : zones.keySet()) {
            if (selectedZone.contains(p)) {
                currentZone = zones.get(p);
                break;
            }
        }
        if (currentZone == null) {
            currentZone = new Zone("zone " + (zones.keySet().size() + 1));
            zones.put(selectedZone.iterator().next(), currentZone);
        }
        return currentZone;
    }

    public void mergeZones(Set<Point> selectedZone1, Set<Point> selectedZone2, ArrayList<ArrayList<Long>> matrix) {
        if (selectedZone1.isEmpty() && selectedZone2.isEmpty()) {
                return;
        }
        ArrayList<ArrayList<Integer>> lines = Main.mergeZones(this.file + "_current.jpg",
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
}
