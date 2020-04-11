package model;

import java.util.List;

public class Good {
    private String id;
    private String status;
    private String name;
    private String company;
    private int number;
    private long price;
    private Seller seller;
    private boolean isAvailable;
    private String category;
    private List<String> categoryAttribute;
    private String description;
    private float averageScore;
    private List<Comment> comments;
    private List<Buyer> buyers;
    private int visitNumber;


    public Good(String id, String status, String name, String company, int number, long price, Seller seller, String category, List<String> categoryAttribute, String description) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.company = company;
        this.number = number;
        this.price = price;
        this.seller = seller;
        this.category = category;
        this.categoryAttribute = categoryAttribute;
        this.description = description;
    }
}
