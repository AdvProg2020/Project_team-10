package model.requests;

import controller.AccountManager;
import model.*;

import java.util.List;

public class AddProductRequest extends Request {

    private int goodId;
    private String name;
    private String company;
    private int number;
    private long price;
    private String category;
    private List<String> categoryAttribute;
    private String description;

    public AddProductRequest(Account account, int id, int goodId, String name, String company, int number, long price,
                             String category, List<String> categoryAttribute, String description) {
        super(account, id);
        this.goodId = goodId;
        this.name = name;
        this.company = company;
        this.number = number;
        this.price = price;
        this.category = category;
        this.categoryAttribute = categoryAttribute;
        this.description = description;
    }

    @Override
    public void accept() {
        Good good = new Good(goodId, name, company, number, price, ((Seller) account), category, categoryAttribute, description);
        Shop.getShop().getAllGoods().add(good);
        ((Seller) account).getGoods().add(good);
        message = "the product added";
    }
}
