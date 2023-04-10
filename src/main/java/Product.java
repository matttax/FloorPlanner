import javax.swing.*;

public class Product {
    String name;
    long quantity;
    long price;
    String category;

    public Product(String name, long quantity, long price, String category) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }

    @Override
    public String toString() {
        return name;
    }
}
