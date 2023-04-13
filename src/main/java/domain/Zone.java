package domain;

import lombok.Data;

import java.awt.*;
import java.util.ArrayList;

@Data
public class Zone {
    String name;
    Point pointOnMap;
    ArrayList<Product> products;

    public Zone(String name, Point pointOnMap) {
        this.name = name;
        this.pointOnMap = pointOnMap;
        this.products = new ArrayList<>();
    }

    public String toXml(String tab) {
        StringBuilder sb = new StringBuilder();
        sb.append(tab).append("<zone>\n");
        sb.append(tab).append("\t<name>").append(name).append("</name>\n");
        sb.append(tab).append("\t<point>").append(pointOnMap.x).append(" ").append(pointOnMap.y).append("</point>\n");
        sb.append(tab).append("\t<products>");
        for (Product p : products) {
            sb.append(tab).append(p.toXml(tab + "\t")).append("\n");
        }
        sb.append("</zone>");
        return sb.toString();
    }
}
