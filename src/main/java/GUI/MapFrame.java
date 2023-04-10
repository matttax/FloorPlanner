package GUI;

import controllers.MapController;
import python.PythonReader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class MapFrame extends JFrame {

    MapPanel panel;
    String file;
    ArrayList<ArrayList<Long>> matrix;
    MapController mapController;

    public MapFrame(String file) {
        this.file = file;
        mapController = new MapController(file);
        this.matrix = loadFile();
        this.panel = new MapPanel(matrix);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(matrix.get(0).size()+150, matrix.size()+150);
        JPanel upperButtonPanel = new JPanel(new BorderLayout());
        upperButtonPanel.setLayout(new FlowLayout());
        JButton b1 = new JButton("Save map");
        JButton b2 = new JButton("New map");
        b2.addActionListener(e -> {
            JFileChooser openFile = new JFileChooser("/home/matttax/IdeaProjects/FloorPlanner/test/");
            int ret = openFile.showDialog(null, "Открыть файл");
            if (ret == JFileChooser.APPROVE_OPTION) {
                new MapFrame(String.valueOf(openFile.getSelectedFile()));
            }
        });
        JButton b3 = new JButton("New zone");
        JButton b4 = new JButton("Merge zones");
        b4.addActionListener(e -> {
            mapController.mergeZones(panel.selectedRight1, panel.selectedRight2, panel.matrix);
            panel.selectedZone.clear();
            panel.repaint();
        });
        b3.addActionListener(e -> {
            try {
                new AddProductsFrame(mapController.getZone(panel.selectedZone));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        upperButtonPanel.add(b4, FlowLayout.LEFT);
        upperButtonPanel.add(b3, FlowLayout.LEFT);
        upperButtonPanel.add(b1, FlowLayout.LEFT);
        upperButtonPanel.add(b2, FlowLayout.LEFT);
        this.add(panel, BorderLayout.CENTER);
        this.add(upperButtonPanel, BorderLayout.NORTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private ArrayList<ArrayList<Long>> loadFile() {
        return PythonReader.getMatrix(file);
    }

}
