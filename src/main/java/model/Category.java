package model;

import java.util.List;

public class Category {
    private String name;
    private List<String> attributes;
    private List<Good> goods;

    public Category(String name, List<String> attributes) {
        this.name = name;
        this.attributes = attributes;
    }
}
