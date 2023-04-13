package GUI;

import controllers.AddProductsController;
import domain.Product;
import domain.Zone;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Objects;

public class AddProductsFrame extends JFrame {
    AddProductsController addProductsController;

    boolean newProduct;
    JTextPane name;
    JLabel nameLabel;


    JList<Product> productList;
    DefaultListModel<Product> productModel;
    JPanel productPanel;
    JSplitPane productPane;
    JPanel savePanel;

    StringPanel productNamePanel;
    NumericPanel productQuantityPanel;
    NumericPanel productPricePanel;
    StringPanel productCategoryPanel;

    AddProductsFrame(Zone zone) throws IOException {
        newProduct = false;
        addProductsController = new AddProductsController(zone);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(500, 500);
        
        name = new JTextPane();
        nameLabel = new JLabel("Name: ");

        productList = new JList<>();
        productModel = new DefaultListModel<>();
        productPane = new JSplitPane();

        this.add(getNamePanel(), BorderLayout.NORTH);



        productList.setModel(productModel);
        for (Product p : zone.getProducts()) {
            productModel.addElement(p);
        }

        productPane.setLeftComponent(getLeftPanel());

        productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
        productNamePanel = new StringPanel("Name: ");
        productQuantityPanel = new NumericPanel("Quantity: ");
        productPricePanel = new NumericPanel("Price: ");
        productCategoryPanel = new StringPanel("Category: ");
        productPanel.add(productNamePanel);
        productPanel.add(productQuantityPanel);
        productPanel.add(productPricePanel);
        productPanel.add(productCategoryPanel);

        savePanel = new JPanel();
        savePanel.add(getDeleteButton());
        savePanel.add(getSaveButton());


        productPane.setRightComponent(getRightPanel());
        productPane.setDividerLocation(this.getWidth() / 3);

        productList.getSelectionModel().addListSelectionListener(e -> {
            Product p = productList.getSelectedValue();
            if (p != null) {
                productNamePanel.text.setText(p.getName());
                productQuantityPanel.number.setValue(p.getQuantity());
                productPricePanel.number.setValue(p.getPrice());
                productCategoryPanel.text.setText(String.valueOf(p.getCategory()));
            }
            newProduct = false;
        });

        this.add(getAddButton(), BorderLayout.SOUTH);
        this.add(productPane, BorderLayout.CENTER);
        this.setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (!Objects.equals(name.getText(), "")) {
                    addProductsController.getZone().setName(name.getText());
                }
            }
        });
    }
    
    private JPanel getNamePanel() {
        JPanel panel = new JPanel(new FlowLayout());
        name.setText(addProductsController.getZone().getName());
        panel.add(name, FlowLayout.LEFT);
        panel.add(nameLabel, FlowLayout.LEFT);
        return panel;
    }

    private JPanel getRightPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(productPanel, BorderLayout.NORTH);
        panel.add(savePanel, BorderLayout.CENTER);
        return panel;
    }

    private JScrollPane getLeftPanel() {
        return new JScrollPane(productList);
    }

    private JButton getDeleteButton() {
        JButton button = new JButton("Delete");
        button.addActionListener(e -> {
            int index = productList.getSelectedIndex();
            if (index != -1) {
                addProductsController.getZone().getProducts().remove(productList.getSelectedValue());
                productList.remove(index);
                productModel.remove(index);
                productList.updateUI();
            }
        });
        return button;
    }

    private JButton getSaveButton() {
        JButton button = new JButton("Save");
        button.setMinimumSize(new Dimension(500, 105));
        button.addActionListener(e -> {
            Product p = new Product(productNamePanel.text.getText(),
                    (Long) productQuantityPanel.number.getValue(),
                    (Long) productPricePanel.number.getValue(),
                    productCategoryPanel.text.getText());
            if (!newProduct) {
                int index = productList.getSelectedIndex();
                addProductsController.getZone().getProducts().remove(productList.getSelectedValue());
                productList.remove(index);
                productModel.remove(index);
            }
            productModel.addElement(p);
            addProductsController.getZone().getProducts().add(p);
            productList.updateUI();
        });
        return button;
    }

    private JButton getAddButton() {
        JButton button = new JButton("Add");
        button.setPreferredSize(new Dimension(100, 50));
        button.addActionListener(e -> {
            productNamePanel.text.setText("");
            productQuantityPanel.number.setValue(0L);
            productPricePanel.number.setValue(0L);
            productCategoryPanel.text.setText("");
            newProduct = true;
        });
        return button;
    }
}
