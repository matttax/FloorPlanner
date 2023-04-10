package GUI;

import javax.swing.*;
import java.awt.*;

public class StringPanel extends JPanel {

    JLabel label;
    JTextPane text;

    public StringPanel(String labelText) {
        setLayout(new FlowLayout());
        text = new JTextPane();
        text.setPreferredSize(new Dimension(100,20));
        this.add(text, FlowLayout.LEFT);
        label = new JLabel(labelText);
        this.add(label, FlowLayout.LEFT);
        this.setMaximumSize(new Dimension(1000, 50));
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

}
