import java.util.ArrayList;

public class Zone {
    String name;
    ArrayList<Product> products;

    public Zone(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }
}
