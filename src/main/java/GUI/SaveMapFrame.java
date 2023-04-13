package GUI;

import domain.Building;

import javax.swing.*;
import java.awt.*;

public class SaveMapFrame extends JFrame {

    JPanel dataPanel;
    JPanel savePanel;

    public SaveMapFrame() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(300, 180);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
        StringPanel buildingNamePanel = new StringPanel("Building name: ");
        StringPanel buildingAddressPanel = new StringPanel("Building address: ");
        NumericPanel floorNumberPanel = new NumericPanel("Floor number: ");
        dataPanel.add(buildingNamePanel);
        dataPanel.add(buildingAddressPanel);
        dataPanel.add(floorNumberPanel);

        savePanel = new JPanel();
        JButton cancelButton = new JButton(("Cancel"));
        JButton saveButton = new JButton(("Save"));

        cancelButton.addActionListener(e -> this.dispose());
        saveButton.addActionListener(e -> {
            Building building = new Building(buildingNamePanel.text.getText(),
                    buildingAddressPanel.text.getText());
        });
        savePanel.add(cancelButton);
        savePanel.add(saveButton);

        this.add(dataPanel, BorderLayout.NORTH);
        this.add(savePanel, BorderLayout.CENTER);
        this.setVisible(true);
    }
}
