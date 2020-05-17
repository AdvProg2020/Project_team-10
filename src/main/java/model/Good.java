package model;

import controller.AccountManager;
import controller.GoodsManager;

import java.util.*;

public class Good implements Comparable<Good>{
    private int id;
    private String status;
    private String name;
    private String company;
    private int number;
    private long price;
    private Seller seller;
    private String category;
    private Map<String, String> categoryAttribute;
    private String description;
    private List<Integer> allRates;
    private List<Comment> comments;
    private List<Buyer> buyers;
    private int visitNumber;
    private Map<Account, Integer> goodsInBuyerCart;
    private Date date;


    public Good(int id, String name, String company, int number, long price, Seller seller, String category, HashMap categoryAttribute, String description) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.number = number;
        this.price = price;
        this.seller = seller;
        this.category = category;
        this.categoryAttribute = categoryAttribute;
        this.description = description;
        this.goodsInBuyerCart = new HashMap<>();
        this.allRates = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.date = new Date();
    }

    public Map<Account, Integer> getGoodsInBuyerCart() {
        return goodsInBuyerCart;
    }

    public int getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public String getCategory() {
        return category;
    }



    public List<Comment> getComments() {
        return comments;
    }

    public String getDescription() {
        return description;
    }

    public int getVisitNumber() {
        return visitNumber;
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


    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
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

    @Override
    public String toString() {
        return  "id: " + id + "\n" +
                "name: " + name + "\n" +
                "company: " + company + "\n" +
                "number: " + number + "\n" +
                "price: " + price + "\n" +
                "category: " + category + "\n" +
                "categoryAttribute: " + categoryAttribute + "\n" +
                "description: " + description + "\n" +
                 "visit number: " + visitNumber + "\n--------------------------------------------------";
    }

    public void setCategoryAttribute(Map<String, String> categoryAttribute) {
        this.categoryAttribute = categoryAttribute;
    }

    public Map<String, String> getCategoryAttribute() {
        return categoryAttribute;
    }

    public float calculateAverageRate() {
        float sum = 0;
        for (Integer rate : allRates) {
            sum += rate;
        }
        return sum / allRates.size();
    }


    @Override
    public int compareTo(Good good) {
        switch (GoodsManager.getKindOfSort()) {
            case "time":
                return this.date.compareTo(good.date);
            case "score":
                return (int) (good.calculateAverageRate() - this.calculateAverageRate());
            case "visit number":
                return good.visitNumber - this.visitNumber;
            default:
                return (int) (good.price - this.price);
        }
    }

    public void increaseVisitNumber() {
        this.visitNumber += 1;
    }
}
