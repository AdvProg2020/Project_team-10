package controller;

import model.*;
import model.requests.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SellerManager {


    public static void addProduct(String name, String company, int number, long price, String category,
                                  HashMap<String, String> categoryAttribute, String description) {
        //TODO
        Good good = new Good(AccountManager.getLastGoodId() + 1, name, company, number, price, AccountManager.getOnlineAccount().getUsername(), category, categoryAttribute, description);
        ((Seller) AccountManager.getOnlineAccount()).getGoods().add(good);
        Shop.getShop().getAllGoods().add(good);
        Shop.getShop().getCategoryByName(category).getGoods().add(good);
        Shop.getShop().getAllRequests().add(new AddProductRequest(AccountManager.getOnlineAccount(), AccountManager.getLastRequestId() + 1,
                AccountManager.getLastGoodId() + 1, name, company, number, price, category, categoryAttribute, description));
    }

    public static void editProduct(int id, String name, String company, int number, long price, String category,
                                   HashMap<String, String> categoryAttribute, String description) {
        //TODO
//        ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id).setName(name);
//        ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id).setCompany(company);
//        ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id).setNumber(number);
//        ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id).setPrice(price);
//        ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id).setCategory(category);
//        ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id).setCategoryAttribute(categoryAttribute);
//        ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id).setDescription(description);

        Shop.getShop().getAllRequests().add(new EditProductRequest(AccountManager.getOnlineAccount(),
                AccountManager.getLastRequestId() + 1, id, name, company, number, price, category, categoryAttribute, description));
    }

    public static void removeProduct(Good good) {
        ((Seller) AccountManager.getOnlineAccount()).getGoods().remove(good);
        Shop.getShop().getAllGoods().remove(good);
        Shop.getShop().getCategoryByName(good.getCategory()).getGoods().remove(good);
    }

    public static void editOff(int id, List<Good> goods, Date startDate, Date endDate, int percent) {
        Shop.getShop().getAllRequests().add(new EditOffRequest(AccountManager.getOnlineAccount(),
                AccountManager.getLastRequestId() + 1, id, goods, startDate, endDate, percent));
    }

    public static void addOff(List<Good> goods, Date startDate, Date endDate, int percent) {
        //todo
        Off off = new Off(AccountManager.getLastOffId() + 1, goods, startDate, endDate, percent);
        Shop.getShop().getAllOffs().add(off);
        ((Seller) AccountManager.getOnlineAccount()).getOffs().add(off);
        for (Good good : goods) {
            good.setOffId(off.getId());
        }
        //todo
        Shop.getShop().getAllRequests().add(new AddOffRequest(AccountManager.getOnlineAccount(),
                AccountManager.getLastRequestId() + 1, AccountManager.getLastOffId() + 1, goods, startDate, endDate, percent));
    }


}
