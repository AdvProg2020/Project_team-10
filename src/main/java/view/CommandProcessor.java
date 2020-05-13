package view;

import controller.AccountManager;
import controller.AdminManager;
import model.*;
import controller.SellerManager;
import model.*;
import view.menus.Menu;

import java.util.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandProcessor {

    private static String typeName;
    private static boolean isAdminRegistered;
    private static String company;
    private static boolean editDiscount;

    public static void setEditDiscount(boolean editDiscount) {
        CommandProcessor.editDiscount = editDiscount;
    }

    public static Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        matcher.find();
        return matcher;
    }

    public static boolean checkUsernameInvalidation(String username) {
        if (getMatcher(username, "\\w+").matches()) {
            return true;
        } else {
            System.out.println("invalid username");
            return false;
        }
    }

    public static boolean checkPasswordInvalidation(String password) {
        if (getMatcher(password, "(?=.*[0-9])(?=.*[a-z]).{8,}").matches()) {
            return true;
        } else {
            System.out.println("invalid password");
            return false;
        }
    }

    public static boolean checkEmailInvalidation(String email) {
        if (getMatcher(email, "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b").matches()) {
            if (Shop.getShop().isThereEmail(email)) {
                System.out.println("email exists");
                return false;
            }
        } else {
            System.out.println("invalid email");
            return false;
        }
        return true;
    }

    public static boolean checkPhoneNumberInvalidation(String phoneNumber) {
        if (getMatcher(phoneNumber, "09\\d{9}").matches()) {
            if (Shop.getShop().isTherePhoneNumber(phoneNumber)) {
                System.out.println("phone number exits");
                return false;
            }
        } else {
            System.out.println("invalid phone number");
            return false;
        }
        return true;
    }

    public static boolean checkNameInvalidation(String name) {
        if (getMatcher(name, "(\\w+\\s?)+").matches()) {
            return true;
        } else {
            System.out.println("invalid name");
            return false;
        }

    }

    public static boolean checkCreditInvalidation(long credit) {
        return false;
    }

    public static boolean checkPercentInvalidation(int percent) {
        return false;
    }

    public static boolean checkTypeInvalidation(int type) {
        if (type == 1) {
            typeName = "buyer";
        } else if (type == 2) {
            typeName = "seller";
            System.out.print("enter your company name: ");
            company = Menu.scanner.nextLine();
            return checkNameInvalidation(company);
        } else if (type == 3 && !isAdminRegistered) {
            typeName = "admin";
            isAdminRegistered = true;
        } else {
            System.out.println("you must choose one of follow options");
            return false;
        }
        return true;
    }

    public static Menu getRegisterMenu() {
        return new Menu("register") {
            @Override
            public void show() {
                System.out.println("register panel");
            }

            @Override
            public void execute() {
                CommandProcessor.processRegister(true);
            }
        };
    }

    public static Menu getLoginMenu() {
        return new Menu("login") {
            @Override
            public void show() {
                System.out.println("login panel");
            }

            @Override
            public void execute() {
                CommandProcessor.processLogin();
            }
        };
    }

    public static Menu getLogoutMenu() {
        return new Menu("logout") {
            @Override
            public void show() {

            }

            @Override
            public void execute() {
                processLogout();
            }
        };
    }

    public static void processRegister(boolean register) {
        ArrayList<String> info = new ArrayList<>();
        int flag = 1;
        Menu.scanner.nextLine();
        while (true) {
            if (flag == 1) {
                System.out.print("enter your username: ");
                String username = Menu.scanner.nextLine();
                if (checkUsernameInvalidation(username)) {
                    if (AccountManager.canRegister(username)) {
                        info.add(username);
                        flag += 1;
                    } else {
                        System.out.println("username exists");
                    }
                }
            } else if (flag == 2) {
                System.out.print("enter your password: ");
                String password = Menu.scanner.nextLine();
                if (checkPasswordInvalidation(password)) {
                    info.add(password);
                    flag += 1;
                }
            } else if (flag == 3) {
                if (register) {
                    if (isAdminRegistered) {
                        System.out.print("enter your type:\n1: buyer\n2: seller\n");
                    } else {
                        System.out.print("enter your type:\n1: buyer\n2: seller\n3: admin\n");
                    }
                    int type = Menu.scanner.nextInt();
                    Menu.scanner.nextLine();
                    if (checkTypeInvalidation(type)) {
                        info.add(typeName);
                        flag += 1;
                    }
                } else {
                    flag += 1;
                }
            } else if (flag == 4) {
                System.out.print("enter your first name: ");
                String firstName = Menu.scanner.nextLine();
                if (checkNameInvalidation(firstName)) {
                    info.add(firstName);
                    flag += 1;
                }
            } else if (flag == 5) {
                System.out.print("enter your last name: ");
                String lastName = Menu.scanner.nextLine();
                if (checkNameInvalidation(lastName)) {
                    info.add(lastName);
                    flag += 1;
                }
            } else if (flag == 6) {
                System.out.print("enter your email: ");
                String email = Menu.scanner.nextLine();
                if (checkEmailInvalidation(email)) {
                    info.add(email);
                    flag += 1;
                }
            } else {
                System.out.print("enter your phone number: ");
                String phoneNumber = Menu.scanner.nextLine();
                if (checkPhoneNumberInvalidation(phoneNumber)) {
                    info.add(phoneNumber);
                    info.add(company);
                    if (register) {
                        AccountManager.register(info.get(0), info.get(1), info.get(2)
                                , info.get(3), info.get(4), info.get(5), info.get(6), info.get(7));
                        System.out.println(info.get(0) + " was registered successfully");
                    } else {
                        AccountManager.register(info.get(0), info.get(1), "admin"
                                , info.get(2), info.get(3), info.get(4), info.get(5), info.get(6));
                        System.out.println(info.get(0) + " The new manager was successfully registered");
                    }
                    break;
                }
            }
        }
    }

    public static void processLogin() {
        if (AccountManager.getOnlineAccount() != null) {
            System.out.println("you are logged in");
        } else {
            String username = null, password = null;
            int flag = 0;
            Menu.scanner.nextLine();
            while (flag < 2) {
                if (flag == 0) {
                    System.out.println("enter your username: ");
                    username = Menu.scanner.nextLine();
                    if (checkUsernameInvalidation(username)) {
                        flag++;
                    }
                } else if (flag == 1) {
                    System.out.println("enter your password: ");
                    password = Menu.scanner.nextLine();
                    if (checkPasswordInvalidation(password)) {
                        flag++;
                    }
                }
            }
            if (AccountManager.login(username, password)) {
                System.out.println("login successful");
            } else {
                System.out.println("username/password is incorrect");
            }
        }
    }

    public static void processEditProfile() {
        int flag = 1;
        ArrayList<String> info = new ArrayList<>();
        Menu.scanner.nextLine();
        while (true) {
            if (flag == 1) {
                System.out.print("enter your new password: ");
                String password = Menu.scanner.nextLine();
                if (checkPasswordInvalidation(password)) {
                    info.add(password);
                    flag += 1;
                }
            } else if (flag == 2) {
                System.out.print("enter your new first name: ");
                String firstName = Menu.scanner.nextLine();
                if (checkNameInvalidation(firstName)) {
                    info.add(firstName);
                    flag += 1;
                }
            } else if (flag == 3) {
                System.out.print("enter your new last name: ");
                String lastName = Menu.scanner.nextLine();
                if (checkNameInvalidation(lastName)) {
                    info.add(lastName);
                    flag += 1;
                }
            } else if (flag == 4) {
                System.out.print("enter your new email: ");
                String email = Menu.scanner.nextLine();
                if (checkEmailInvalidation(email)) {
                    info.add(email);
                    flag += 1;
                }
            } else {
                System.out.print("enter your phone number: ");
                String phoneNumber = Menu.scanner.nextLine();
                if (checkPhoneNumberInvalidation(phoneNumber)) {
                    info.add(phoneNumber);
                    AdminManager.editPersonalInfo(info.get(0), info.get(1), info.get(2), info.get(3), info.get(4));
                    break;
                }
            }
        }
    }

    public static void processLogout() {
        if (AccountManager.getOnlineAccount() == null) {
            System.out.println("no body is logged in");
        } else {
            System.out.println("logout successful");
            AccountManager.setOnlineAccount(null);
            Menu.setIsLogged(false);
        }
    }

    public static void processDeleteAccountByAdmin() {
        System.out.print("Enter the desired username: ");
        Menu.scanner.nextLine();
        String username = Menu.scanner.nextLine();
        if (AdminManager.deleteAccount(username)) {
            System.out.println(username + " deleted");
        } else {
            System.out.println(username + " not exist");
        }
    }

    public static void processRemoveProductById() {
        System.out.println("Enter the desired id: ");
        Menu.scanner.nextLine();
        int id = Menu.scanner.nextInt();

    }

    public static void processAddDiscountCode(Discount discount) {
        String startDate;
        String endDate;
        Date startDate1 = null;
        Date endDate1 = null;
        int percent = 0;
        long amount = 0;
        int repeat = 0;
        String people;
        List<Account> allPeople = new ArrayList<>();
        int flag = 1;
        Menu.scanner.nextLine();
        while (true) {
            if (flag == 1) {
                System.out.print("Enter the start date[Month/Day/Years Hour:Minutes]: ");
                startDate = Menu.scanner.nextLine();
                if (getDateByString(startDate) != null) {
                    startDate1 = getDateByString(startDate);
                    flag += 1;
                } else {
                    System.out.println("format not corrected");
                }
            } else if (flag == 2) {
                System.out.print("Enter the end date[Month/Day/Years Hour:Minutes]: ");
                endDate = Menu.scanner.nextLine();
                if (getDateByString(endDate) != null) {
                    endDate1 = getDateByString(endDate);
                    flag += 1;
                } else {
                    System.out.println("format not corrected");
                }
            } else if (flag == 3) {
                System.out.print("Enter the discount percentage: ");
                percent = Menu.scanner.nextInt();
                if (percent < 100 && percent > 0) {
                    flag += 1;
                }
            } else if (flag == 4) {
                System.out.print("Enter the maximum discount amount: ");
                amount = Menu.scanner.nextLong();
                flag += 1;
            } else if (flag == 5) {
                System.out.print("Enter the number of repetitions of the discount code: ");
                repeat = Menu.scanner.nextInt();
                flag += 1;
            } else {
                System.out.println("Enter the people covered by the discount code: ");
                Menu.scanner.nextLine();
                Shop shop = Shop.getShop();
                while (!(people = Menu.scanner.nextLine()).equalsIgnoreCase("end")) {
                    if (shop.getAccountByUsername(people) != null) {
                        allPeople.add(shop.getAccountByUsername(people));
                        System.out.println("username added");
                    } else {
                        System.out.println("username not exists");
                    }
                }
                if (editDiscount) {
                    AdminManager.editDiscount(startDate1, endDate1, percent, amount, repeat, allPeople , discount);
                    System.out.println("discount code edited");
                    editDiscount = false;
                } else {
                    AdminManager.createDiscount(startDate1, endDate1, percent, amount, repeat, allPeople);
                    System.out.println("The discount code was successfully created");
                }
                break;
            }
        }
    }

    public static void processEditDiscountCode() {
        System.out.print("enter the Discount code for edit: ");
        Menu.scanner.nextLine();
        int code;
        code = Menu.scanner.nextInt();
        Discount discount = Shop.getShop().getDiscountWithCode(code);
        if (discount == null){
            System.out.println("discount code not exist");
        }else {
            CommandProcessor.setEditDiscount(true);
            CommandProcessor.processAddDiscountCode(discount);
        }

    }

    public static void processAddProduct() {
        String name;
        String company;
        int number = 0;
        long price = 0;
        String category = null;
        String description;
        ArrayList<String> categoryAttributes = new ArrayList<>();
        int flag = 1;
        Menu.scanner.nextLine();
        System.out.print("enter name of the product: ");
        name = Menu.scanner.nextLine();
        System.out.print("enter company of the product: ");
        company = Menu.scanner.nextLine();
        while (true) {
            if (flag == 1) {
                System.out.print("enter number of the product: ");
                number = Menu.scanner.nextInt();
                Menu.scanner.nextLine();
                if (number < 1) {
                    System.out.println("your number must be larger than 0");
                } else {
                    flag += 1;
                }
            } else if (flag == 2) {
                System.out.print("enter price of the product: ");
                price = Menu.scanner.nextLong();
                Menu.scanner.nextLine();
                if (price < 1) {
                    System.out.println("your price must be larger than 0");
                } else {
                    flag += 1;
                }
            } else if (flag == 3) {
                System.out.print("enter category of the product: ");
                category = Menu.scanner.nextLine();
                if (Shop.getShop().getCategoryByName(category) == null) {
                    System.out.println("this category dose not exist");
                } else {
                    flag += 1;
                }
            } else {
                for (String attribute : Shop.getShop().getCategoryByName(category).getAttributes()) {
                    System.out.println(attribute + " :");
                    categoryAttributes.add(Menu.scanner.nextLine());
                }
                System.out.println("write any description about your product");
                description = Menu.scanner.nextLine();
                SellerManager.addProduct(AccountManager.getLastGoodId() + 1, name, company, number, price, category,
                        categoryAttributes, description);
                break;
            }
        }
    }

    public static void processShowProductByIdForSeller() {
        while (true) {
            System.out.println("enter your id");
            int id = Menu.scanner.nextInt();
            Good good = ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id);
            if (good == null) {
                System.out.println("product with this id doesnt exist");
            } else {
                System.out.println(good);
                break;
            }
        }
    }

    public static void processShowBuyersForSeller() {
        while (true) {
            int id = Menu.scanner.nextInt();
            Good good = ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id);
            if (good == null) {
                System.out.println("product with this id doesnt exist");
            } else {
                System.out.println(good.getBuyers());
                break;
            }
        }
    }

    private static Date getDateByString(String dateInput) {
        Calendar calendar = Calendar.getInstance();
        String regex = "(\\d\\d)/(\\d\\d)/(\\d\\d\\d\\d) (\\d\\d):(\\d\\d)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dateInput);
        int[] dateSplit = new int[5];
        if (getMatcher(dateInput, regex).matches()) {
            while (matcher.find()) {
                for (int i = 0; i < 5; i++) {
                    dateSplit[i] = Integer.parseInt(matcher.group(i + 1));
                }
            }
            calendar.set(Calendar.MONTH, dateSplit[0]);
            calendar.set(Calendar.DATE, dateSplit[1]);
            calendar.set(Calendar.YEAR, dateSplit[2]);
            calendar.set(Calendar.HOUR, dateSplit[3]);
            calendar.set(Calendar.MINUTE, dateSplit[4]);
            return calendar.getTime();
        }
        return null;
    }

    public static void printShowPersonalInfo() {
        System.out.println(AccountManager.getOnlineAccount());
    }

    public static void showDiscount() {
        System.out.print("enter the discount code: ");
        Menu.scanner.nextLine();
        int code = Menu.scanner.nextInt();
        Discount discount = Shop.getShop().getDiscountWithCode(code);
        if (discount != null) {
            System.out.print(discount);
            for (Account user : discount.getUsers()) {
                System.out.print("|‌" + user.getUsername() + "|‌");
            }
            System.out.println();
        } else {
            System.out.println("discount code not exist");
        }
    }

    public static void showAllDiscountCode() {
        for (Discount allDiscount : Shop.getShop().getAllDiscounts()) {
            System.out.print(allDiscount);
            for (Account user : allDiscount.getUsers()) {
                System.out.print("|‌" + user.getUsername() + "|‌");
            }
            System.out.println();
        }
    }

    public static void removeDiscountCode() {
        System.out.print("enter the discount code for remove: ");
        Menu.scanner.nextLine();
        int code = Menu.scanner.nextInt();
        Discount discount = Shop.getShop().getDiscountWithCode(code);
        if (discount != null) {
            Shop.getShop().getAllDiscounts().remove(discount);
            System.out.println("discount code removed");
        } else {
            System.out.println("discount code not exist");
        }

    }

    public static void showProductsInCart() {
        for (Good good : ((Buyer) AccountManager.getOnlineAccount()).getCart()) {
            System.out.println("name: " + good.getName() + " id: " + good.getId());
        }
    }

    //برای این باید فکر کنیم
    public static void showProductInCart(int id) {
        int counter = 0;
        Good good = ((Buyer) AccountManager.getOnlineAccount()).getGoodInCartById(id);
        if (good != null) {
            System.out.println("name: " + good.getName() + " id: " + good.getId());
            counter++;
        }
        if (counter == 0) {
            System.out.println("product with id : " + id + " is not exist");
        }
    }

    public static void showAllOrders() {
        for (Log log : AccountManager.getOnlineAccount().getLogs()) {
            System.out.println(log.toString());
        }
    }

    public static void showOrder(int id) {
        int counter = 0;
        for (Log log : AccountManager.getOnlineAccount().getLogs()) {
            if (log.getId() == id) {
                System.out.println(log.toString());
                counter++;
            }
        }
        if (counter == 0) {
            System.out.println("order with id : " + id + " is not exist");
        }
    }

    //چرا توی discount menu برای این جیزی نداریم؟؟؟؟
    public static void showAllDiscountsCode() {
        for (Discount discount : AccountManager.getOnlineAccount().getDiscounts()) {
            System.out.println(discount.toString());
        }
    }

}

