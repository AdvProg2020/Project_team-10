package controller;

import model.*;
import model.requests.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SellerManager {


    public static void addProduct(String username, String name, String company, int number, long price, String category,
                                  HashMap<String, String> categoryAttribute, String description, String imagePath, String videoPath) {
        //TODO
        Account account = Shop.getShop().getAccountByUsername(username);
        Good good = new Good(AccountManager.getLastGoodId() + 1, name, company, number, price, account.getUsername(), category, categoryAttribute, description, imagePath);
        good.setVideoPath(videoPath);
        ((Seller) account).getGoods().add(good);
        Shop.getShop().getAllGoods().add(good);
        Shop.getShop().getCategoryByName(category).getGoods().add(good);
        Shop.getShop().getAllRequests().add(new AddProductRequest(account, AccountManager.getLastRequestId() + 1,
                AccountManager.getLastGoodId() + 1, name, company, number, price, category, categoryAttribute, description, imagePath));
    }

    public static void editProduct(Account account, int id, String name, String company, int number, long price, String category,
                                   HashMap<String, String> categoryAttribute, String description) {
        //TODO
        ((Seller) account).getProductWithId(id).setName(name);
        ((Seller) account).getProductWithId(id).setCompany(company);
        ((Seller) account).getProductWithId(id).setNumber(number);
        ((Seller) account).getProductWithId(id).setPrice(price);
        ((Seller) account).getProductWithId(id).setCategory(category);
        ((Seller) account).getProductWithId(id).setCategoryAttribute(categoryAttribute);
        ((Seller) account).getProductWithId(id).setDescription(description);

        Shop.getShop().getAllRequests().add(new EditProductRequest(account,
                AccountManager.getLastRequestId() + 1, id, name, company, number, price, category, categoryAttribute, description));
    }

    public static void removeProduct(int goodId) {
        Good good = Shop.getShop().getProductWithId(goodId);
        ((Seller) Shop.getShop().getAccountByUsername(good.getSellerUsername())).getGoods().remove(good);
        Shop.getShop().getAllGoods().remove(good);
//        GoodsManager.getFilteredGoods().remove(good);
        Shop.getShop().getCategoryByName(good.getCategory()).getGoods().remove(good);
    }

    public static void editOff(String username, int id, List<Good> goods, Date startDate, Date endDate, int percent) {
        Account account = Shop.getShop().getAccountByUsername(username);
        Shop.getShop().getAllRequests().add(new EditOffRequest(account, AccountManager.getLastRequestId() + 1, id,
                goods, startDate, endDate, percent));
    }

    public static void addOff(String username, List<Integer> goodsId, Date startDate, Date endDate, int percent) {
        //todo
        Account account = Shop.getShop().getAccountByUsername(username);
        Off off = new Off(AccountManager.getLastOffId() + 1, goodsId, startDate, endDate, percent);
        Shop.getShop().getAllOffs().add(off);
        ((Seller) account).getOffs().add(off);
        for (Integer goodId : goodsId) {
            Shop.getShop().getProductWithId(goodId).setOffId(off.getId());
        }
        //todo
        Shop.getShop().getAllRequests().add(new AddOffRequest(account,
                AccountManager.getLastRequestId() + 1, AccountManager.getLastOffId() + 1, goodsId, startDate, endDate, percent));
    }

    public static void removeOff(String username, int offId) {
        Account account = Shop.getShop().getAccountByUsername(username);
        Off off = Shop.getShop().getOffWithId(offId);
        for (Integer goodId : off.getGoodsId()) {
            Shop.getShop().getProductWithId(goodId).setOffId(0);
        }
        ((Seller) account).getOffs().remove(off);
        Shop.getShop().getAllOffs().remove(off);
    }


}
