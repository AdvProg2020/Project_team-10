package model;

import controller.AccountManager;
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
    private List<Integer> allScores;
    private List<Comment> comments;
    private List<String> buyersUsername;
    private int visitNumber;
    private Map<String, Integer> goodsInBuyerCart;
    private Date date;
    private int offId;
    private String imagePath;
    private String videoPath;


    public Good(int id, String name, String company, int number, long price, String sellerUsername, String category
            , HashMap categoryAttribute, String description, String imagePath) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.number = number;
        this.price = price;
        this.sellerUsername = sellerUsername;
        this.category = category;
        this.categoryAttribute = categoryAttribute;
        this.description = description;
        this.imagePath = imagePath;
        this.goodsInBuyerCart = new HashMap<>();
        this.allScores = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.date = new Date();
        this.buyersUsername = new ArrayList<>();
        AccountManager.increaseLastGoodId();
    }

    //todo
    public Good(int id, String name, String company, int number, long price, String sellerUsername, String category, Map<String, String> categoryAttribute, String description) {
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
        this.allScores = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.date = new Date();
        this.buyersUsername = new ArrayList<>();
        AccountManager.increaseLastGoodId();

    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public void setVisitNumber(int visitNumber) {
        this.visitNumber = visitNumber;
    }

    public void subtractNumber(Account account) {
        int numberOfGoodInCart = getGoodsInBuyerCart().get(account.getUsername());
        this.number -= numberOfGoodInCart;
    }

    public Map<String, Integer> getGoodsInBuyerCart() {
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

    public String getImagePath() {
        return imagePath;
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

    public List<Integer> getAllScores() {
        return allScores;
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
        for (Integer rate : allScores) {
            sum += rate;
        }
        if (allScores.size() == 0) {
            return 0;
        }
        return sum / allScores.size();
    }


    @Override
    public int compareTo(Good good) {
//        switch (GoodsManager.getKindOfSorts().get()) {
//            case "time":
//                return this.date.compareTo(good.date);
//            case "score":
//                return (int) (good.calculateAverageRate() - this.calculateAverageRate());
//            case "visit number":
//                return good.visitNumber - this.visitNumber;
//            default:
//                return (int) (good.price - this.price);
//        }
        return 0;
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
