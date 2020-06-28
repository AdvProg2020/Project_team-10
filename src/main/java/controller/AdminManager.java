package controller;

import model.*;
import model.requests.Request;
import view.CommandProcessor;
import view.menus.Menu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminManager {

    //Admin


    public static void deleteAccount(Account account) {
        Shop.getShop().getAllAccounts().remove(account);
        if (account instanceof Buyer) {
            Shop.getShop().getAllBuyers().remove(account);
        } else if (account instanceof Seller) {
            Shop.getShop().getAllSellers().remove(account);
        } else if (account instanceof Admin) {
            Shop.getShop().getAllAdmins().remove(account);
        }
    }

    public static void createDiscount(Date startDate, Date endDate, int percent,
                                      long maxAmountOfDiscount, int repeatDiscount, List<String> userNames) {
        Discount discount = new Discount(AccountManager.getLastDiscountCode() + 1, startDate, endDate, percent,
                maxAmountOfDiscount, repeatDiscount, userNames);
        Shop.getShop().getAllDiscounts().add(discount);
        for (String username : userNames) {
            ((Buyer) Shop.getShop().getAccountByUsername(username)).getDiscounts().add(discount);
            ((Buyer) Shop.getShop().getAccountByUsername(username)).getDiscountAndNumberOfAvailableDiscount().put(discount.getCode(), discount.getRepeatDiscount());
        }
    }

    public static void editDiscount(Date startDate, Date endDate, int percent,
                                    long maxAmountOfDiscount, int repeatDiscount, List<String> userNames, Discount discount) {
        discount.setStartDate(startDate);
        discount.setEndDate(endDate);
        discount.setPercent(percent);
        discount.setMaxAmountOfDiscount(maxAmountOfDiscount);
        discount.setRepeatDiscount(repeatDiscount);
        discount.setUserNames(userNames);
    }

    public static void acceptRequest(Request request) {
        request.accept();
        Shop.getShop().getAllRequests().remove(request);
    }

    public static void declineRequest(Request request) {
        Shop.getShop().getAllRequests().remove(request);
    }

    public static void editCategory(String oldName, String newName, List<String> newAttributes) {
        Category category = Shop.getShop().getCategoryByName(oldName);
        ArrayList<String> differencesOfTwoLists = new ArrayList<>(newAttributes);
        differencesOfTwoLists.removeAll(category.getAttributes());
        ArrayList<String> shouldBeRemoved = new ArrayList<>(category.getAttributes());
        shouldBeRemoved.removeAll(newAttributes);
        category.setName(newName);
        category.setAttributes(newAttributes);
        for (Good good : category.getGoods()) {
            good.setCategory(newName);
            for (String differencesOfTwoList : differencesOfTwoLists) {
                good.getCategoryAttribute().put(differencesOfTwoList, "");
            }
            for (String attribute : shouldBeRemoved) {
                good.getCategoryAttribute().remove(attribute);
            }
        }
    }

    public static void addCategory(String name, List<String> attributes) {
        Shop.getShop().getAllCategories().add(new Category(name, attributes));
    }

    public static void removeCategory(Category category) {
        Shop.getShop().getAllCategories().remove(category);
        Shop.getShop().getAllGoods().removeAll(category.getGoods());
    }


}