package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class MapPanel extends JPanel {
    ArrayList<ArrayList<Long>> matrix;
    Set<Point> selectedRight1, selectedRight2;
    Set<Point> selectedZone;
    boolean firstLastFilled;

    public MapPanel(ArrayList<ArrayList<Long>> matrix) {
        this.matrix = matrix;
        firstLastFilled = false;
        selectedRight1 = new HashSet<>();
        selectedRight2 = new HashSet<>();
        selectedZone = new HashSet<>();
        this.addMouseListener(new ClickListener());
        this.setPreferredSize(new Dimension(matrix.get(0).size(), matrix.size()));

    }

    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        for (int i =  0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(0).size(); j++) {
                Point p = new Point(i, j);
                if (matrix.get(i).get(j) == 1) {
                    g2D.setColor(Color.BLACK);
                } else if (selectedRight1.contains(p)
                        || selectedRight2.contains(p)) {
                    g2D.setColor(Color.GRAY);
                } else if (selectedZone.contains(p)) {
                    g2D.setColor(Color.PINK);
                } else {
                    g2D.setColor(Color.WHITE);
                }
                g2D.drawLine(j, i, j, i);
            }
        }
    }

    public void drawMerging(ArrayList<ArrayList<Integer>> lines) {
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

        repaint();
    }

    private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            Point firstPoint = new Point(e.getY(), e.getX());
            Set<Point> selected = null;
            if (SwingUtilities.isRightMouseButton(e)) {
                if (firstLastFilled) {
                    selectedRight1.clear();
                } else {
                    selectedRight2.clear();
                }

                if (selectedRight1.isEmpty()) {
                    selected = selectedRight1;
                    firstLastFilled = false;
                } else if (selectedRight2.isEmpty()) {
                    selected = selectedRight2;
                    firstLastFilled = true;
                }
            } else {
                selected = selectedZone;
                selected.clear();
            }
            selectZone(firstPoint, selected);
            repaint();
        }

        private void selectZone(Point fromPoint, Set<Point> selected) {
            Deque<Point> queue = new ArrayDeque<>();
            queue.add(fromPoint);
            while (!queue.isEmpty()) {
                Point top = queue.pop();
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (top.y + i < 0 || top.y + i >= matrix.get(0).size() || top.x + j < 0 || top.x + j >= matrix.size()) {
                            continue;
                        }
                        Point p = new Point(top.x + j, top.y + i);
                        if (matrix.get(top.x + j).get(top.y + i) == 0 && !selected.contains(p)) {
                            queue.push(p);
                            selected.add(p);
                        }
                    }
                }
            }
        }
    }
}
