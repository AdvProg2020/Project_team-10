package view;

import controller.AccountManager;
import controller.BuyerManager;
import model.*;
import view.menus.Menu;

import java.util.*;

import static view.CommandProcessor.*;

public class Purchase {

    private static Menu cartMenu;

    public static void setCartMenu(Menu cartMenu) {
        Purchase.cartMenu = cartMenu;
    }

    public static void giveReceiverInformation() {
        System.out.println("receiver information");
        List<String> receiverInformation = new ArrayList<>();
        String name = null;
        int flag = 1;
        String phoneNumber = null;
        String address = null;
        String zipCode = null;
        while (true) {
            if (flag == 1) {
                System.out.print("receiver name: ");
                name = Menu.scanner.nextLine();
                if (checkNameInvalidation(name)) {
                    receiverInformation.add(name);
                    flag += 1;
                }
            } else if (flag == 2) {
                System.out.print("receiver phone number: ");
                phoneNumber = Menu.scanner.nextLine();
                if (checkPhoneNumberInvalidation(phoneNumber)) {
                    receiverInformation.add(phoneNumber);
                    flag += 1;
                }
            } else if (flag == 3) {
                System.out.print("receiver address: ");
                address = Menu.scanner.nextLine();
                receiverInformation.add(address);
                flag += 1;
            } else if (flag == 4) {
                System.out.print("zip code: ");
                zipCode = Menu.scanner.nextLine();
                if (zipCode.length() == 10) {
                    receiverInformation.add(zipCode);
                    break;
                } else {
                    System.out.println("The format is incorrect");
                }
            } //TODO
        }
    }

    public static void giveDiscountCode() {
        while (true) {
            System.out.println("do you have a discount code? \n1: yes\n2: no");
            int selected = Integer.parseInt(Menu.scanner.nextLine());
            if (selected == 1) {
                processGiveDiscountCode();
                break;
            } else if (selected == 2) {
                payment(BuyerManager.getPriceAfterApplyOff(((Buyer) AccountManager.getOnlineAccount()).getCart()), null);
                break;
            } else {
                System.out.println("you must choose one of following options");
            }
        }
    }

    private static void processGiveDiscountCode() {
        System.out.print("enter code: ");
        int code = Integer.parseInt(Menu.scanner.nextLine());
        Date currentDate = new Date();
        Discount discount = ((Buyer) AccountManager.getOnlineAccount()).getDiscountByCode(code);
        if (discount == null) {
            System.out.println("discount does not exist");
            giveDiscountCode();
        } else if (discount.getStartDate().after(currentDate)) {
            System.out.println("It is not yet time to use the discount code");
            giveDiscountCode();
        } else if (discount.getEndDate().before(currentDate)) {
            System.out.println("The discount code has expired");
            giveDiscountCode();
        } else {
            System.out.println("Discount code applied");
            payment(getFinalTotalPrice(discount), discount);
        }
    }

    public static double getFinalTotalPrice(Discount discount) {
        if (BuyerManager.getPriceAfterApplyOff(((Buyer) AccountManager.getOnlineAccount()).getCart()) * (discount.getPercent() / 100.0) > discount.getMaxAmountOfDiscount()) {
            return (BuyerManager.getPriceAfterApplyOff(((Buyer) AccountManager.getOnlineAccount()).getCart()) - discount.getMaxAmountOfDiscount());
        } else {
            return BuyerManager.getPriceAfterApplyOff(((Buyer) AccountManager.getOnlineAccount()).getCart()) * ((100.0 - discount.getPercent()) / 100.0);
        }
    }

    public static void payment(double finalPrice, Discount discount) {
        System.out.println("Payment page");
        System.out.println("total price: " + BuyerManager.getPriceAfterApplyOff(((Buyer) AccountManager.getOnlineAccount()).getCart()));
        System.out.println("payable amount: " + ((long) finalPrice));
        System.out.println("1: confirm\n2: increase credit\n3: back");
        int selected = Integer.parseInt(Menu.scanner.nextLine());
        if (selected == 1) {
            if (canPay(finalPrice)) {
                pay(finalPrice, discount);
            } else {
                System.out.println("your credit is not enough");
            }
        } else if (selected == 2) {
            CommandProcessor.processIncreaseCredit();
            payment(finalPrice, discount);
        } else if (selected != 3) {
            System.out.println("you must choose one of following options");
            payment(finalPrice, discount);
        }
    }

    public static boolean canPay(double finalPrice) {
        return finalPrice <= (AccountManager.getOnlineAccount()).getCredit();
    }

    public static void pay(double finalPrice, Discount currentDiscount) {
        Buyer currentBuyer = ((Buyer) AccountManager.getOnlineAccount());
        currentBuyer.subtractCredit(finalPrice);
        Set<Seller> sellers = new HashSet<>();
        for (Good good : currentBuyer.getCart()) {
            good.subtractNumber();
            good.getBuyersUsername().add(currentBuyer.getUsername());
            sellers.add(((Seller) Shop.getShop().getAccountByUsername(good.getSellerUsername())));
        }
        for (int discountCode : currentBuyer.getDiscountAndNumberOfAvailableDiscount().keySet()) {
            if (currentDiscount != null && discountCode == currentDiscount.getCode()) {
                int number = currentBuyer.getDiscountAndNumberOfAvailableDiscount().get(discountCode);
                currentBuyer.getDiscountAndNumberOfAvailableDiscount().put(discountCode, number - 1);
                if (number == 1) {
                    currentBuyer.getDiscountAndNumberOfAvailableDiscount().remove(discountCode);
                }
            }
        }
        makeLogs(sellers, finalPrice);
        currentBuyer.getGoods().addAll(currentBuyer.getCart());
        currentBuyer.getCart().clear();
    }

    private static void makeLogs(Set<Seller> sellers, double finalPrice) {
        Buyer currentBuyer = ((Buyer) AccountManager.getOnlineAccount());
        Map<String, List<Good>> sellersToHisGoods = new HashMap<>();
        for (Seller seller : sellers) {
            ArrayList<Good> goodsOfOneSeller = new ArrayList<>();
            for (Good good : currentBuyer.getCart()) {
                if (good.getSellerUsername().equals(seller.getUsername())) {
                    goodsOfOneSeller.add(good);
                }
            }
            sellersToHisGoods.put(seller.getUsername(), goodsOfOneSeller);
            seller.increaseCredit(BuyerManager.getPriceAfterApplyOff(goodsOfOneSeller));
            seller.getSellerLogs().add(new SellerLog(AccountManager.getLastSellerLogId() + 1, new Date(), ((long) finalPrice),
                    BuyerManager.getTotalPrice() - BuyerManager.getPriceAfterApplyOff(currentBuyer.getCart()), sellersToHisGoods.get(seller),
                    currentBuyer.getUsername(), "received"));
        }
        currentBuyer.getBuyerLogs().add(new BuyerLog(AccountManager.getLastBuyerLogId() + 1, new Date(), ((long) finalPrice),
                ((long) (BuyerManager.getPriceAfterApplyOff(((Buyer) AccountManager.getOnlineAccount()).getCart()) - finalPrice)), sellersToHisGoods, "paid"));

    }

}