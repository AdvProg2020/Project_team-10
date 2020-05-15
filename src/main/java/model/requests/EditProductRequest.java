package model.requests;

import model.Account;
import model.Admin;
import model.Seller;

import java.util.List;

public class EditProductRequest extends Request {

    private int goodId;
    private String name;
    private String company;
    private int number;
    private long price;
    private String category;
    private List<String> categoryAttribute;
    private String description;

    public EditProductRequest(Account account, int id, int goodId, String name, String company, int number, long price,
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
        this.requestName = "edit product request";
        this.acceptMessage = "product with id " + id + " edited";
        this.declineMessage = "request of edit of product with id " + id + " was declined";
    }

    @Override
    public void accept() {
        ((Seller) account).getProductWithId(goodId).setName(name);
        ((Seller) account).getProductWithId(goodId).setCompany(company);
        ((Seller) account).getProductWithId(goodId).setNumber(number);
        ((Seller) account).getProductWithId(goodId).setPrice(price);
        ((Seller) account).getProductWithId(goodId).setCategory(category);
        ((Seller) account).getProductWithId(goodId).setCategoryAttribute(categoryAttribute);
        ((Seller) account).getProductWithId(goodId).setDescription(description);
    }
}
