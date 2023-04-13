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
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(matrix.get(0).size()+150, matrix.size()+150);
        this.panel = new MapPanel(matrix);
        this.add(panel, BorderLayout.CENTER);
        this.add(initializeButtonPanel(), BorderLayout.NORTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private ArrayList<ArrayList<Long>> loadFile() {
        return PythonReader.getMatrix(file);
    }

    private JButton getSaveButton() {
        JButton button = new JButton("Save map");
        button.addActionListener(e -> {
            new SaveMapFrame();
        });
        return button;
    }

    private  JButton getNewMapButton() {
        JButton button = new JButton("New map");
        button.addActionListener(e -> {
            JFileChooser openFile = new JFileChooser("/home/matttax/IdeaProjects/FloorPlanner/test/");
            int ret = openFile.showDialog(null, "Открыть файл");
            if (ret == JFileChooser.APPROVE_OPTION) {
                new MapFrame(String.valueOf(openFile.getSelectedFile()));
            }
        });
        return button;
    }

    private JButton getNewZoneButton() {
        JButton button = new JButton("New zone");
        button.addActionListener(e -> {
            try {
                new AddProductsFrame(mapController.getZone(panel.selectedZone));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        return button;
    }

    private JButton getMergeZonesButton() {
        JButton button = new JButton("Merge zones");
        button.addActionListener(e -> {
            mapController.mergeZones(panel.selectedRight1, panel.selectedRight2, panel.matrix);
            panel.selectedZone.clear();
            panel.repaint();
        });
        return button;
    }

    private JPanel initializeButtonPanel() {
        JButton saveMapButton = getSaveButton();
        JButton newMapButton = getNewMapButton();
        JButton newZoneButton = getNewZoneButton();
        JButton mergeZonesButton = getMergeZonesButton();
        JPanel panel = new JPanel(new BorderLayout());
        panel.setLayout(new FlowLayout());
        panel.add(mergeZonesButton, FlowLayout.LEFT);
        panel.add(newZoneButton, FlowLayout.LEFT);
        panel.add(saveMapButton, FlowLayout.LEFT);
        panel.add(newMapButton, FlowLayout.LEFT);
        return panel;
    }
}
