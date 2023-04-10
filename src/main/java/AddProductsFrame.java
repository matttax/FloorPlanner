import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class AddProductsFrame extends JFrame {

    boolean newProduct;
    JTextPane name;
    JLabel nameLabel;


    JList<Product> productList;
    DefaultListModel<Product> productModel;
    JPanel productPanel;
    JSplitPane productPane;

    JButton addButton;
    JButton saveProductButton;

    AddProductsFrame(Zone zone) throws IOException {
        newProduct = false;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(500, 500);

        JPanel namePanel = new JPanel(new FlowLayout());
        name = new JTextPane();
        name.setText(zone.name);
        nameLabel = new JLabel("Name: ");
        namePanel.add(name, FlowLayout.LEFT);
        namePanel.add(nameLabel, FlowLayout.LEFT);
        this.add(namePanel, BorderLayout.NORTH);

        productList = new JList<>();
        productModel = new DefaultListModel<>();
        productPane = new JSplitPane();
        productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));

        productList.setModel(productModel);
        for (Product p : zone.products) {
            productModel.addElement(p);
        }

        productPane.setLeftComponent(new JScrollPane(productList));

        StringPanel productNamePanel = new StringPanel("Name: ");
        NumericPanel productQuantityPanel = new NumericPanel("Quantity: ");
        NumericPanel productPricePanel = new NumericPanel("Price: ");
        StringPanel productCategoryPanel = new StringPanel("Category: ");
        productPanel.add(productNamePanel);
        productPanel.add(productQuantityPanel);
        productPanel.add(productPricePanel);
        productPanel.add(productCategoryPanel);

        JPanel savePanel = new JPanel();
        saveProductButton = new JButton("Save");
        JButton deleteProductButton = new JButton("Delete");
        deleteProductButton.addActionListener(e -> {
            int index = productList.getSelectedIndex();
            if (index != -1) {
                zone.products.remove(productList.getSelectedValue());
                productList.remove(index);
                productModel.remove(index);
                productList.updateUI();
            }
        });
        saveProductButton.setMinimumSize(new Dimension(500, 105));
        savePanel.add(saveProductButton);
        savePanel.add(deleteProductButton);
        saveProductButton.addActionListener(e -> {
            Product p = new Product(productNamePanel.text.getText(),
                    (Long) productQuantityPanel.number.getValue(),
                    (Long) productPricePanel.number.getValue(),
                    productCategoryPanel.text.getText());
            if (!newProduct) {
                int index = productList.getSelectedIndex();
                zone.products.remove(productList.getSelectedValue());
                productList.remove(index);
                productModel.remove(index);
            }
            productModel.addElement(p);
            zone.products.add(p);
            productList.updateUI();
        });


        JPanel allPanel = new JPanel(new BorderLayout());
        allPanel.add(productPanel, BorderLayout.NORTH);
        allPanel.add(savePanel, BorderLayout.CENTER);

        productPane.setRightComponent(allPanel);
        productPane.setDividerLocation(this.getWidth() / 3);

        productList.getSelectionModel().addListSelectionListener(e -> {
            Product p = productList.getSelectedValue();
            if (p != null) {
                productNamePanel.text.setText(p.name);
                productQuantityPanel.number.setValue(p.quantity);
                productPricePanel.number.setValue(p.price);
                productCategoryPanel.text.setText(String.valueOf(p.category));
            }
            newProduct = false;
        });

        addButton = new JButton("Add");
        addButton.setPreferredSize(new Dimension(100, 50));
        addButton.addActionListener(e -> {
            productNamePanel.text.setText("");
            productQuantityPanel.number.setValue(0L);
            productPricePanel.number.setValue(0L);
            productCategoryPanel.text.setText("");
            newProduct = true;
        });
        this.add(addButton, BorderLayout.SOUTH);

        this.add(productPane, BorderLayout.CENTER);
        this.setVisible(true);
    }
}
