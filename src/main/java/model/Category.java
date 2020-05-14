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

    public String getName() {
        return name;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", attributes=" + attributes +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }
}
