package model;

import java.util.List;

public class Good {
    private int id;
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
    private List<Integer> allRates;
    private List<Comment> comments;
    private List<Buyer> buyers;
    private int visitNumber;
    private int numberOfChoose;


    public Good(int id, String name, String company, int number, long price, Seller seller, String category, List<String> categoryAttribute, String description) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.number = number;
        this.price = price;
        this.seller = seller;
        this.category = category;
        this.categoryAttribute = categoryAttribute;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public List<Buyer> getBuyers() {
        return buyers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCategoryAttribute(List<String> categoryAttribute) {
        this.categoryAttribute = categoryAttribute;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfChoose() {
        return numberOfChoose;
    }

    public void setNumberOfChoose(int numberOfChoose) {
        this.numberOfChoose = numberOfChoose;
    }

    public int getNumber() {
        return number;
    }

    public long getPrice() {
        return price;
    }

    public List<Integer> getAllRates() {
        return allRates;
    }
}
