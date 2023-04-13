package domain;

import java.util.ArrayList;

public class Category {
    String name;
    ArrayList<Category> subcategories;

    public Category(String name) {
        this.name = name;
    }
}
