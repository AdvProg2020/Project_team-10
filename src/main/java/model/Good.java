package model;

import controller.AccountManager;
import controller.FileHandler;
import controller.GoodsManager;

import java.util.*;

public class Good implements Comparable<Good> {
    private int id;
    private String status;
    private String name;
    private String company;
    private int number;
    private long price;
    private String sellerUsername;
    private String category;
    private Map<String, String> categoryAttribute;
    private String description;
    private List<Integer> allRates;
    private List<Comment> comments;
    private List<String> buyersUsername;
    private int visitNumber;
    private Map<Account, Integer> goodsInBuyerCart;
    private Date date;
    private int offId;


    public Good(int id, String name, String company, int number, long price, String sellerUsername, String category
            , HashMap categoryAttribute, String description) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.number = number;
        this.price = price;
        this.sellerUsername = sellerUsername;
        this.category = category;
        this.categoryAttribute = categoryAttribute;
        this.description = description;
        this.goodsInBuyerCart = new HashMap<>();
        this.allRates = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.date = new Date();
        AccountManager.increaseLastGoodId();
    }

    //todo


    public Good setVisitNumber(int visitNumber) {
        this.visitNumber = visitNumber;
        return this;
    }

    public void subtractNumber() {
        int numberOfGoodInCart = getGoodsInBuyerCart().get(AccountManager.getOnlineAccount());
        this.number -= numberOfGoodInCart;
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

    public List<String> getBuyersUsername() {
        return buyersUsername;
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

    public void setSeller(String sellerUsername) {
        this.sellerUsername = sellerUsername;
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

    public String getSellerUsername() {
        return sellerUsername;
    }

    public List<Integer> getAllRates() {
        return allRates;
    }

    @Override
    public String toString() {
        return "id: " + id + "\n" +
                "name: " + name + "\n" +
                "company: " + company + "\n" +
                "number: " + number + "\n" +
                "price: " + price + "\n" +
                "category: " + category + "\n" +
                "categoryAttribute: " + categoryAttribute + "\n" +
                "description: " + description + "\n" +
                "visit number: " + visitNumber + "\n--------------------------------------------------";
    }

    public String digestToString() {
        return  "price: " + price + "\n" +
                "category: " + category + "\n" +
                "description: " + description + "\n" +
                "seller: " + sellerUsername + "\n" +
                "average rate: " + this.calculateAverageRate() + "\n" +
                "discount: " + this.getAmountOfDiscount() +
                "\n--------------------------------------------------";
    }

    private long getAmountOfDiscount(){
        if (this.offId != 0){
            return Shop.getShop().getOffWithId(offId).getPercent() * (this.price /100);
        }
        return 0;
    }

    public String goodMenuToString() {
        return "id: " + id + "\n" +
                "name: " + name + "\n" +
                "price: " + price + "\n" +
                "category: " + category + "\n--------------------------------------------------";
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
        if (allRates.size() == 0) {
            return 0;
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

    public int getOffId() {
        return offId;
    }

    public void setOffId(int offId) {
        this.offId = offId;
    }
}
