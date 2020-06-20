package model.requests;

import controller.AccountManager;
import model.*;

import java.util.HashMap;
import java.util.List;

public class AddProductRequest extends Request {

    private int goodId;
    private String name;
    private String company;
    private int number;
    private long price;
    private String category;
    private HashMap<String, String> categoryAttribute;
    private String description;
    private String imagePath;

    public AddProductRequest(Account account, int id, int goodId, String name, String company, int number, long price,
                             String category, HashMap<String, String> categoryAttribute, String description, String imagePath) {
        super(account, id);
        this.goodId = goodId;
        this.name = name;
        this.company = company;
        this.number = number;
        this.price = price;
        this.category = category;
        this.categoryAttribute = categoryAttribute;
        this.description = description;
        this.imagePath = imagePath;
        this.requestName = "add product request";
        this.acceptMessage = "product with id " + id + " added";
        this.declineMessage = "request of add of product with id " + id + " was declined";
    }

    @Override
    public void accept() {
        Good good = new Good(goodId, name, company, number, price, account.getUsername(), category, categoryAttribute,
                description, imagePath);
        Shop.getShop().getAllGoods().add(good);
        ((Seller) account).getGoods().add(good);
    }
}
