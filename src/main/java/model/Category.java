package model;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    private List<String> attributes;
    private List<Good> goods;

    public Category(String name, List<String> attributes) {
        this.name = name;
        this.attributes = attributes;
        this.goods = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public List<Good> getGoods() {
        return goods;
    }

    @Override
    public String toString() {
        return name + "  " +
                "attributes: ";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }
}
