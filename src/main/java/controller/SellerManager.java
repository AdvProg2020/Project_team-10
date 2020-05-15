package controller;

import model.*;
import model.requests.*;

import java.util.Date;
import java.util.List;

public class SellerManager {


    public static void addProduct(String name, String company, int number, long price, String category,
                                  List<String> categoryAttribute, String description) {
        //TODO
        //((Seller) AccountManager.getOnlineAccount()).getGoods().add(new Good(10, name, company, number, price, ((Seller) AccountManager.getOnlineAccount()), category, categoryAttribute, description));
        Shop.getShop().getAllRequests().add(new AddProductRequest(AccountManager.getOnlineAccount(), AccountManager.getLastRequestId() + 1,
                AccountManager.getLastGoodId() + 1, name, company, number, price, category, categoryAttribute, description));
    }

    public static void editProduct(int id, String name, String company, int number, long price, String category,
                                   List<String> categoryAttribute, String description) {
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

    public static boolean removeProduct(int id) {
        Good good = ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id);
        if (good != null) {
            ((Seller) AccountManager.getOnlineAccount()).getGoods().remove(good);
            return true;
        }
        return false;
    }

    public static boolean editOff(int id, List<Good> goods, Date startDate, Date endDate, int discount) {
        Off off = ((Seller) AccountManager.getOnlineAccount()).getOffWithId(id);
        if (off != null) {
            Shop.getShop().getAllRequests().add(new EditOffRequest(AccountManager.getOnlineAccount(),
                    AccountManager.getLastRequestId() + 1, id, goods, startDate, endDate, discount));
            return true;
        }
        return false;
    }

    public static boolean addOff(int id, List<Good> goods, Date startDate, Date endDate, int discount) {
        Off off = ((Seller) AccountManager.getOnlineAccount()).getOffWithId(id);
        if (off != null) {
            Shop.getShop().getAllRequests().add(new AddOffRequest(AccountManager.getOnlineAccount(),
                    AccountManager.getLastRequestId() + 1, id, goods, startDate, endDate, discount));
            return true;
        }
        return false;

    }


}
