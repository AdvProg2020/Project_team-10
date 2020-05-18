package view;

import controller.AccountManager;
import controller.BuyerManager;
import model.*;
import view.menus.Menu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
                payment(BuyerManager.getTotalPrice(), null);
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
        if (BuyerManager.getTotalPrice() * (discount.getPercent() / 100.0) > discount.getMaxAmountOfDiscount()) {
            return (BuyerManager.getTotalPrice() - discount.getMaxAmountOfDiscount());
        } else {
            return BuyerManager.getTotalPrice() * ((100.0 - discount.getPercent()) / 100.0);
        }
    }

    public static void payment(double finalPrice, Discount discount) {
        System.out.println("Payment page");
        System.out.println("total price: " + BuyerManager.getTotalPrice());
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

    private static boolean canPay(double finalPrice) {
        return finalPrice <= (AccountManager.getOnlineAccount()).getCredit();
    }

    private static void pay(double finalPrice, Discount currentDiscount) {
        Buyer onlineAccount = ((Buyer) AccountManager.getOnlineAccount());
        onlineAccount.subtractCredit(finalPrice);
        for (Good good : onlineAccount.getCart()) {
            good.subtractNumber();
            good.getBuyers().add(onlineAccount);
        }
        for (Discount discount : onlineAccount.getDiscountAndNumberOfAvailableDiscount().keySet()) {
            if (discount == currentDiscount) {
                int number = onlineAccount.getDiscountAndNumberOfAvailableDiscount().get(discount);
                onlineAccount.getDiscountAndNumberOfAvailableDiscount().put(discount, number - 1);
                if (number == 1) {
                    onlineAccount.getDiscountAndNumberOfAvailableDiscount().remove(discount);
                }
            }
        }
        System.out.println("The purchase was successful");
    }

    private static void checkUsernameInvalidation() {
    }
}