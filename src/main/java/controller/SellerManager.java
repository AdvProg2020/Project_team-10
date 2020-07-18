package controller;

import model.*;
import model.requests.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SellerManager {


    public static void addProduct(Account account, String name, String company, int number, long price, String category,
                                  HashMap<String, String> categoryAttribute, String description, String imagePath, String videoPath) {
        //TODO
        Good good = new Good(AccountManager.getLastGoodId() + 1, name, company, number, price, account.getUsername(), category, categoryAttribute, description, imagePath);
        ((Seller) account).getGoods().add(good);
        Shop.getShop().getAllGoods().add(good);
        Shop.getShop().getCategoryByName(category).getGoods().add(good);
        good.setVideoPath(videoPath);
        Shop.getShop().getAllRequests().add(new AddProductRequest(account, AccountManager.getLastRequestId() + 1,
                AccountManager.getLastGoodId() + 1, name, company, number, price, category, categoryAttribute, description, imagePath));
    }

    public static void editProduct(Account account, int id, String name, String company, int number, long price, String category,
                                   HashMap<String, String> categoryAttribute, String description) {
        //TODO
//        ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id).setName(name);
//        ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id).setCompany(company);
//        ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id).setNumber(number);
//        ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id).setPrice(price);
//        ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id).setCategory(category);
//        ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id).setCategoryAttribute(categoryAttribute);
//        ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id).setDescription(description);

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

    public static void editOff(Account account, int id, List<Good> goods, Date startDate, Date endDate, int percent) {
        Shop.getShop().getAllRequests().add(new EditOffRequest(account, AccountManager.getLastRequestId() + 1, id,
                goods, startDate, endDate, percent));
    }

    public static void addOff(Account account, List<Integer> goodsId, Date startDate, Date endDate, int percent) {
        //todo
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

    public static void removeOff(Account account, int offId) {
        Off off = Shop.getShop().getOffWithId(offId);
        for (Integer goodId : off.getGoodsId()) {
            Shop.getShop().getProductWithId(goodId).setOffId(0);
        }
        ((Seller) account).getOffs().remove(off);
        Shop.getShop().getAllOffs().remove(off);
    }


}
