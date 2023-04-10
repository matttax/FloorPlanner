package domain;

import lombok.Data;

@Data
public class Product {
    String name;
    long quantity;
    double price;
    String category;

    public Product(String name, long quantity, long price, String category) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }

    public String toXml(String tab) {
        StringBuilder sb = new StringBuilder();
        sb.append(tab).append("<product>\n");
        sb.append(tab).append("\t<name>").append(name).append("</name>\n");
        sb.append(tab).append("\t<quantity>").append(quantity).append("</quantity>\n");
        sb.append(tab).append("\t<price>").append(price).append("</price>\n");
        sb.append(tab).append("\t<category>").append(name).append("</category>\n");
        sb.append(tab).append("</product>");
        return sb.toString();
    }

    @Override
    public String toString() {
        return name;
    }
}
