package GUI;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

public class NumericPanel extends JPanel {

    JLabel label;
    JFormattedTextField number;

    public NumericPanel(String labelText) {
        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setGroupingUsed(false);
        NumberFormatter numberFormatter = new NumberFormatter(format);
        numberFormatter.setValueClass(Long.class);
        numberFormatter.setAllowsInvalid(false); //this is the key
        number = new JFormattedTextField(numberFormatter);
        number.setPreferredSize(new Dimension(100,20));

        setLayout(new FlowLayout());
        this.add(number, FlowLayout.LEFT);
        label = new JLabel(labelText);
        this.add(label, FlowLayout.LEFT);
        this.setMaximumSize(new Dimension(1000, 50));
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
    }
}
