package view;

import controller.*;
import model.*;
import controller.SellerManager;
import model.requests.Request;
import view.menus.Menu;
import view.menus.ProductMenu;

import java.util.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandProcessor {

    private static String typeName;
    private static boolean isAdminRegistered;
    private static String company;

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

    //seller

    public static void showCompanyInfo() {
        System.out.println("company: " + ((Seller) AccountManager.getOnlineAccount()).getCompany());
    }

    public static void showSalesHistory() {
        for (SellerLog sellerLog : ((Seller) AccountManager.getOnlineAccount()).getSellerLogs()) {
            System.out.println(sellerLog);
        }
    }

    public static void showHisProducts() {
        Collections.sort(((Seller) AccountManager.getOnlineAccount()).getGoods());
        for (Good good : ((Seller) AccountManager.getOnlineAccount()).getGoods()) {
            System.out.println(good);
        }
    }

    public static boolean showBuyersOfThisProduct(int id) {
        Good good = ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id);
        if (good != null) {
            System.out.println(good.getBuyersUsername());
            return true;
        }
        return false;
    }

    public static void viewBalance() {
        System.out.println(AccountManager.getOnlineAccount().getCredit());
    }

    public static void processRegister(boolean register) {
        ArrayList<String> info = new ArrayList<>();
        int flag = 1;
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
                        System.out.println(" The new manager was registered successfully");
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
                    AccountManager.editPersonalInfo(info.get(0), info.get(1), info.get(2), info.get(3), info.get(4));
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
        String username = Menu.scanner.nextLine();
        if (AdminManager.deleteAccount(username)) {
            System.out.println(username + " deleted");
        } else {
            System.out.println(username + " not exist");
        }
    }

    public static void processRemoveProductById() {
        while (true) {
            System.out.println("enter your id");
            int id = Menu.scanner.nextInt();
            Good good = ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id);
            if (good == null) {
                System.out.println("product with id " + id + " doesnt exist");
            } else {
                SellerManager.removeProduct(good);
                System.out.println("product with id " + id + " removed successfully");
                break;
            }
        }

    }

    public static void processAddDiscountCode(Discount discount, boolean editDiscount) {
        String startDate;
        String endDate;
        Date startDate1 = null;
        Date endDate1 = null;
        int percent = 0;
        long amount = 0;
        int repeat = 0;
        String people;
        List<String> allPeople = new ArrayList<>();
        int flag = 1;
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
                percent = Integer.parseInt(Menu.scanner.nextLine());
                if (percent < 100 && percent > 0) {
                    flag += 1;
                }
            } else if (flag == 4) {
                System.out.print("Enter the maximum discount amount: ");
                amount = Long.parseLong(Menu.scanner.nextLine());
                flag += 1;
            } else if (flag == 5) {
                System.out.print("Enter the number of repetitions of the discount code: ");
                repeat = Integer.parseInt(Menu.scanner.nextLine());
                flag += 1;
            } else {
                System.out.println("Enter the people covered by the discount code: ");
                Shop shop = Shop.getShop();
                while (!(people = Menu.scanner.nextLine()).equalsIgnoreCase("end")) {
                    if (shop.getAccountByUsername(people) != null) {
                        allPeople.add(people);
                        System.out.println("username added");
                    } else {
                        System.out.println("username not exists");
                    }
                }
                if (editDiscount) {
                    AdminManager.editDiscount(startDate1, endDate1, percent, amount, repeat, allPeople, discount);
                    System.out.println("discount code edited");
                } else {
                    AdminManager.createDiscount(startDate1, endDate1, percent, amount, repeat, allPeople);
                    System.out.println("The discount code was successfully created");
                }
                break;
            }
        }
    }

    public static void processAddOrEditProduct(boolean add) {
        String name;
        String company;
        int number = 0;
        long price = 0;
        String category = null;
        String description;
        HashMap<String, String> categoryAttributes = new HashMap<>();
        int flag = 1;
        int id = 0;
        if (!add) {
            while (true) {
                System.out.println("enter your id");
                id = Menu.scanner.nextInt();
                Menu.scanner.nextLine();
                if (((Seller) AccountManager.getOnlineAccount()).getProductWithId(id) != null) {
                    break;
                } else {
                    System.out.println("product with id " + id + " does not exist");
                }
            }
        }
        System.out.print("enter name of the product: ");
        name = Menu.scanner.nextLine();
        System.out.print("enter company of the product: ");
        company = Menu.scanner.nextLine();
        while (true) {
            if (flag == 1) {
                System.out.print("enter number of the product: ");
                number = Integer.parseInt(Menu.scanner.nextLine());
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
                    System.out.print(attribute + ": ");
                    categoryAttributes.put(attribute, Menu.scanner.nextLine());
                }
                System.out.println("write any description about your product");
                description = Menu.scanner.nextLine();
                if (add) {
                    SellerManager.addProduct(name, company, number, price, category, categoryAttributes, description);
                } else {
                    SellerManager.editProduct(id, name, company, number, price, category, categoryAttributes, description);
                }
                break;
            }
        }
    }

    public static void processShowProductByIdForSeller() {
        System.out.println("enter your id");
        int id = Menu.scanner.nextInt();
        Good good = ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id);
        if (good == null) {
            System.out.println("product with this id doesnt exist");
        } else {
            System.out.println(good);
        }
    }

    public static void processShowBuyersForSeller() {
        while (true) {
            System.out.println("enter your id");
            int id = Menu.scanner.nextInt();
            Good good = ((Seller) AccountManager.getOnlineAccount()).getProductWithId(id);
            if (good == null) {
                System.out.println("product with this id doesnt exist");
            } else {
                System.out.println(good.getBuyersUsername());
                break;
            }
        }
    }

    public static void processEditDiscountCode() {
        System.out.print("enter the Discount code for edit: ");
        int code;
        code = Menu.scanner.nextInt();
        Discount discount = Shop.getShop().getDiscountWithCode(code);
        if (discount == null) {
            System.out.println("discount code not exist");
        } else {
            processAddDiscountCode(discount, true);
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
            calendar.set(Calendar.DAY_OF_MONTH, dateSplit[1]);
            calendar.set(Calendar.YEAR, dateSplit[2]);
            calendar.set(Calendar.HOUR, dateSplit[3]);
            calendar.set(Calendar.MINUTE, dateSplit[4]);
            return calendar.getTime();
        }
        return null;
    }

    public static void showPersonalInfo() {
        System.out.println(AccountManager.getOnlineAccount());
    }

    public static void showDiscountForAdmin() {
        System.out.print("enter the discount code: ");
        int code = Integer.parseInt(Menu.scanner.nextLine());
        Discount discount = Shop.getShop().getDiscountWithCode(code);
        if (discount != null) {
            System.out.print(discount);
            for (String username : discount.getUserNames()) {
                System.out.print("|‌" + username + "|‌");
            }
            System.out.println();
        } else {
            System.out.println("discount code does not exist");
        }
    }

    public static void showAllDiscountCodesForAdmin() {
        for (Discount allDiscount : Shop.getShop().getAllDiscounts()) {
            System.out.print(allDiscount);
            for (String username : allDiscount.getUserNames()) {
                System.out.print("|‌" + username + "|‌");
            }
            System.out.println("------------------------------------");
        }
    }

    public static void processRemoveDiscountCode() {
        System.out.print("enter the discount code for remove: ");
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
        Buyer currentBuyer = (Buyer) AccountManager.getOnlineAccount();
        for (Good good : currentBuyer.getCart()) {
            System.out.print(good.goodMenuToString());
            System.out.println("number of this product in your cart: " + good.getGoodsInBuyerCart().get(currentBuyer));
        }
    }

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
        //TODO
    }

    public static void showAllOrders() {
        for (BuyerLog buyerLog : ((Buyer) AccountManager.getOnlineAccount()).getBuyerLogs()) {
            System.out.println(buyerLog);
        }
    }

    public static void showOrder(int id) {
        BuyerLog buyerLog = ((Buyer) AccountManager.getOnlineAccount()).getBuyerLogWithId(id);
        if (buyerLog == null) {
            System.out.println("order with id : " + id + " is not exist");
        } else {
            System.out.println(buyerLog);
        }
    }

    public static void showAllDiscountsCodeForBuyer() {
        for (Discount discount : ((Buyer) AccountManager.getOnlineAccount()).getDiscounts()) {
            System.out.println(discount.toStringForBuyer());
        }
    }

    public static void processAddOrEditCategory(boolean edit, String oldName) {
        System.out.print("Enter category name: ");
        String newName = Menu.scanner.nextLine();
        String attributeString;
        List<String> attribute = new ArrayList<>();
        Category category = Shop.getShop().getCategoryByName(newName);
        if (category == null || edit) {
            System.out.println("Enter the attributes (enter \"end\" to complete this step): ");
            while (!(attributeString = Menu.scanner.nextLine()).trim().equalsIgnoreCase("end")) {
                attribute.add(attributeString);
            }
            if (edit) {
                AdminManager.editCategory(oldName, newName, attribute);
                System.out.println("category edited");
            } else {
                AdminManager.addCategory(newName, attribute);
                System.out.println("category added");
            }
        } else {
            System.out.println("category exist");
        }
    }

    public static void processEditCategory() {
        System.out.print("Enter last category name: ");
        String oldName = Menu.scanner.nextLine();
        if (Shop.getShop().getCategoryByName(oldName) != null) {
            processAddOrEditCategory(true, oldName);
        } else {
            System.out.println("category not exist");
        }


    }

    public static void showAllCategories() {
        for (Category category : Shop.getShop().getAllCategories()) {
            System.out.print(category);
            for (String attribute : category.getAttributes()) {
                System.out.print("|" + attribute + "| ");
            }
            System.out.println();
        }

    }

    public static void viewAllUsers() {
        for (Account account : Shop.getShop().getAllAccounts()) {
            if (account != AccountManager.getOnlineAccount()) {
                System.out.println(account.getUsername());
            }
        }
    }

    public static void processShowAllRequests() {
        for (Request request : Shop.getShop().getAllRequests()) {
            System.out.println(request);
        }
    }

    public static void processIncreaseNumberOfProductInCart() {
        System.out.print("enter product id: ");
        int id = Integer.parseInt(Menu.scanner.nextLine());
        Good good = ((Buyer) AccountManager.getOnlineAccount()).getGoodInCartById(id);
        if (good == null) {
            System.out.println("product with id " + id + " does not exist.");
        } else {
            if (!BuyerManager.canIncrease(good)) {
                System.out.println("There are no more of these products available");
            } else {
                System.out.println("the good was increased");
            }
        }
    }

    public static void processDecreaseNumberOfProductInCart() {
        System.out.print("enter product id: ");
        int id = Integer.parseInt(Menu.scanner.nextLine());
        Good good = ((Buyer) AccountManager.getOnlineAccount()).getGoodInCartById(id);
        if (good == null) {
            System.out.println("product with id " + id + " does not exist.");
        } else {
            if (!BuyerManager.canDecrease(good)) {
                System.out.println("The good was removed from the shopping cart");
            } else {
                System.out.println("the good was decreased");
            }
        }
    }

    public static void processShowRequestDetail() {
        System.out.println("enter your id");
        int id = Integer.parseInt(Menu.scanner.nextLine());
        Request request = Shop.getShop().getRequestById(id);
        if (request == null) {
            System.out.println("request with id " + id + " does not exist");
        } else {
            System.out.println(request);
        }
    }

    public static void processAcceptRequest() {
        System.out.println("enter your id");
        int id = Integer.parseInt(Menu.scanner.nextLine());
        Request request = Shop.getShop().getRequestById(id);
        if (request == null) {
            System.out.println("request with id " + id + " does not exist");
        } else {
            System.out.println(request.getAcceptMessage());
            AdminManager.acceptRequest(request);
        }

    }

    public static void processDeclineRequest() {
        System.out.println("enter your id");
        int id = Integer.parseInt(Menu.scanner.nextLine());
        Request request = Shop.getShop().getRequestById(id);
        if (request == null) {
            System.out.println("request with id " + id + " does not exist");
        } else {
            System.out.println(request.getDeclineMessage());
            AdminManager.declineRequest(request);
        }

    }

    public static void showAvailableSort() {
        System.out.println("available sort :\n1: time \n2: score \n3: visit number\n4: price\n5: back");
    }

    public static void getKindOfSort(Menu currentMenu) {
        int selectedSort = Integer.parseInt(Menu.scanner.nextLine());
        if (selectedSort > 0 && selectedSort < 6) {
            if (selectedSort == 1) {
                GoodsManager.setKindOfSort("time");
            } else if (selectedSort == 2) {
                GoodsManager.setKindOfSort("score");
            } else if (selectedSort == 3) {
                GoodsManager.setKindOfSort("visit number");
            } else if (selectedSort == 4) {
                GoodsManager.setKindOfSort("price");
            }
            currentMenu.getParentMenu().show();
            currentMenu.getParentMenu().execute();
        } else {
            System.out.println("you must choose one of following options");
            currentMenu.show();
            currentMenu.execute();
        }
    }

    public static void showProductAttribute() {
        System.out.println(GoodsManager.getCurrentGood());
    }

    public static void processCompare() {
        System.out.print("enter product id : ");
        int id = Integer.parseInt(Menu.scanner.nextLine());
        Good good = Shop.getShop().getProductWithId(id);
        Good currentGood = GoodsManager.getCurrentGood();

        if (good == null) {
            System.out.println("product with id " + id + "does not exist.");
        } else {
            if (good.getCategory().equals(currentGood.getCategory())) {
                System.out.println("name: " + currentGood.getName() + " || " + good.getName());
                System.out.println("price: " + currentGood.getPrice() + " || " + good.getPrice());
                System.out.println("score: " + currentGood.calculateAverageRate() + " || " + good.calculateAverageRate());
                System.out.println("company: " + currentGood.getCompany() + " || " + good.getCompany());
                System.out.println("description: " + currentGood.getDescription() + " || " + good.getDescription());
                System.out.println("category: " + currentGood.getCategory() + " || " + good.getCategory());
                System.out.println("attribute:");
                for (String attribute : currentGood.getCategoryAttribute().keySet()) {
                    System.out.println(attribute + ": " + currentGood.getCategoryAttribute().get(attribute)
                            + " || " + good.getCategoryAttribute().get(attribute));
                }
            } else {
                System.out.println("You have to choose the same category of goods");
            }
        }

    }

    public static void showComments() {
        for (Comment comment : GoodsManager.getCurrentGood().getComments()) {
            System.out.println(comment);
        }
    }

    public static void processAddComment() {
        System.out.print("title : ");
        String title = Menu.scanner.nextLine();
        System.out.println("content : ");
        String content = Menu.scanner.nextLine();
        GoodsManager.getCurrentGood().getComments().add(new Comment(AccountManager.getOnlineAccount(), GoodsManager.getCurrentGood().getId(),
                "title : " + title + "\n" + "content : " + content));

    }

    public static void processRemoveCategory() {
        System.out.print("enter category name: ");
        String name = Menu.scanner.nextLine();
        Category category = Shop.getShop().getCategoryByName(name);
        if (category == null) {
            System.out.println("category does not exist");
        } else {

            System.out.println("category removed");
        }
    }

//    public static void processRemoveProduct() {
//        System.out.print("enter product id: ");
//        int id = Integer.parseInt(Menu.scanner.nextLine());
//        Good good = Shop.getShop().getProductWithId(id);
//        if (good == null) {
//            System.out.println("product not exist");
//        } else {
//            SellerManager.removeProduct(good);
//            System.out.println("product removed");
//        }
//    }

    public static void showProductsInGoodsMenu() {
        Collections.sort(GoodsManager.getFilteredGoods());
        for (Good good : GoodsManager.getFilteredGoods()) {
            System.out.println(good.goodMenuToString());
        }
    }

    public static void enterProductMenu(Menu parentMenu) {
        System.out.print("enter product id: ");
        int id = Integer.parseInt(Menu.scanner.nextLine());
        Good good = Shop.getShop().getProductWithId(id);
        if (good == null) {
            System.out.println("product with id " + id + " does not exist.");
        } else {
            ProductMenu productMenu = new ProductMenu(parentMenu);
            GoodsManager.setCurrentGood(good);
            good.increaseVisitNumber();
            productMenu.show();
            productMenu.execute();
        }
    }

    public static void showAvailableFilters() {
        System.out.println("available filter\n1: category \n2: product name \n3: company\n4: price\n5: available goods\n6: back");
    }

    public static void getKindOfFilter(Menu currentMenu, List<Good> filteredList) {
        int selectedFilter = Integer.parseInt(Menu.scanner.nextLine());
        if (selectedFilter > 0 && selectedFilter < 7) {
            if (selectedFilter == 1) {
                categoryFilter(filteredList);
            } else if (selectedFilter == 2) {
                productNameFilter(filteredList);
            } else if (selectedFilter == 3) {
                companyFilter(filteredList);
            } else if (selectedFilter == 4) {
                priceFilter(filteredList);
            } else if (selectedFilter == 5) {
                availableGoodsFilter(filteredList);
            }
            currentMenu.getParentMenu().show();
            currentMenu.getParentMenu().execute();
        } else {
            System.out.println("you must choose one of following options");
            currentMenu.show();
            currentMenu.execute();
        }

    }

    private static void availableGoodsFilter(List<Good> filteredList) {
        ArrayList<Good> shouldBeRemoved = new ArrayList<>();
        for (Good good : filteredList) {
            if (good.getNumber() <= 0) {
                shouldBeRemoved.add(good);
            }
        }
        filteredList.removeAll(shouldBeRemoved);
        GoodsManager.getKindOfFilter().put("available goods", "available goods");
    }

    private static void categoryFilter(List<Good> filteredList) {
        ArrayList<Good> shouldBeRemoved = new ArrayList<>();
        System.out.print("enter your category: ");
        String category = Menu.scanner.nextLine();
        for (Good good : filteredList) {
            if (!good.getCategory().equals(category)) {
                shouldBeRemoved.add(good);
            }
        }
        filteredList.removeAll(shouldBeRemoved);
        GoodsManager.getKindOfFilter().put("category", category);
    }

    private static void companyFilter(List<Good> filteredList) {
        ArrayList<Good> shouldBeRemoved = new ArrayList<>();
        System.out.print("enter your company: ");
        String company = Menu.scanner.nextLine();
        for (Good good : filteredList) {
            if (!good.getCompany().equals(company)) {
                shouldBeRemoved.add(good);
            }
        }
        filteredList.removeAll(shouldBeRemoved);
        GoodsManager.getKindOfFilter().put("company", company);
    }

    private static void productNameFilter(List<Good> filteredList) {
        ArrayList<Good> shouldBeRemoved = new ArrayList<>();
        System.out.print("enter your product name: ");
        String productName = Menu.scanner.nextLine();
        for (Good good : filteredList) {
            if (!good.getName().equals(productName)) {
                shouldBeRemoved.add(good);
            }
        }
        filteredList.removeAll(shouldBeRemoved);
        GoodsManager.getKindOfFilter().put("name", productName);
    }

    private static void priceFilter(List<Good> filteredList) {
        ArrayList<Good> shouldBeRemoved = new ArrayList<>();
        System.out.print("enter minimum of price: ");
        int minimum = Integer.parseInt(Menu.scanner.nextLine());
        System.out.print("enter maximum of price: ");
        int maximum = Integer.parseInt(Menu.scanner.nextLine());
        for (Good good : filteredList) {
            if (good.getPrice() > maximum || good.getPrice() < minimum) {
                shouldBeRemoved.add(good);
            }
        }
        filteredList.removeAll(shouldBeRemoved);
        GoodsManager.getKindOfFilter().put("price", minimum + " to " + maximum);
    }

    public static void showCurrentFilters() {
        for (String key : GoodsManager.getKindOfFilter().keySet()) {
            System.out.println(key + ": " + GoodsManager.getKindOfFilter().get(key));
        }
    }

    public static void disableFilter(Menu currentMenu, List<Good> filteredGoods) {
        int selectedFilter = Integer.parseInt(Menu.scanner.nextLine());
        if (selectedFilter > 0 && selectedFilter < 7) {
            if (selectedFilter == 1) {
                disableOneFilter("category", filteredGoods);
            } else if (selectedFilter == 2) {
                disableOneFilter("name", filteredGoods);
            } else if (selectedFilter == 3) {
                disableOneFilter("company", filteredGoods);
            } else if (selectedFilter == 4) {
                disableOneFilter("price", filteredGoods);
            } else if (selectedFilter == 5) {
                disableOneFilter("available goods", filteredGoods);
            }
            processDisableFilter(currentMenu, filteredGoods);
            currentMenu.getParentMenu().show();
            currentMenu.getParentMenu().execute();
        } else {
            System.out.println("you must choose one of following options");
            currentMenu.show();
            currentMenu.execute();
        }

    }

    private static void disableOneFilter(String filter, List<Good> filteredGoods) {
        for (String key : GoodsManager.getKindOfFilter().keySet()) {
            if (key.equals(filter)) {
                GoodsManager.getKindOfFilter().remove(key);
                break;
            }
        }
    }

    private static void processDisableFilter(Menu currentMenu, List<Good> filteredGoods) {
        filteredGoods.clear();
        filteredGoods.addAll(Shop.getShop().getAllGoods());
        ArrayList<Good> shouldBeRemoved = new ArrayList<>();
        for (String type : GoodsManager.getKindOfFilter().keySet()) {
            switch (type) {
                case "category": {
                    String valueOfMap = GoodsManager.getKindOfFilter().get("category");
                    for (Good good : filteredGoods) {
                        if (!valueOfMap.equals(good.getCategory())) {
                            shouldBeRemoved.add(good);
                        }
                    }
                    filteredGoods.removeAll(shouldBeRemoved);
                    break;
                }
                case "company": {
                    String valueOfMap = GoodsManager.getKindOfFilter().get("company");
                    for (Good good : filteredGoods) {
                        if (!valueOfMap.equals(good.getCompany())) {
                            shouldBeRemoved.add(good);
                        }
                    }
                    filteredGoods.removeAll(shouldBeRemoved);
                    break;
                }
                case "name": {
                    String valueOfMap = GoodsManager.getKindOfFilter().get("name");
                    for (Good good : filteredGoods) {
                        if (!valueOfMap.equals(good.getName())) {
                            shouldBeRemoved.add(good);
                        }
                    }
                    filteredGoods.removeAll(shouldBeRemoved);
                    break;
                }
                case "price": {
                    String valueOfMap = GoodsManager.getKindOfFilter().get("price");
                    String[] m = valueOfMap.split(" to ");
                    int minimum = Integer.parseInt(m[0]);
                    int maximum = Integer.parseInt(m[1]);
                    for (Good good : filteredGoods) {
                        if (good.getPrice() > maximum || good.getPrice() < minimum) {
                            shouldBeRemoved.add(good);
                        }
                    }
                    filteredGoods.removeAll(shouldBeRemoved);
                    break;
                }
                case "available goods": {
                    for (Good good : filteredGoods) {
                        if (good.getNumber() <= 0) {
                            shouldBeRemoved.add(good);
                        }
                    }
                    filteredGoods.removeAll(shouldBeRemoved);
                    break;
                }
            }
        }


    }

    public static void showProductsInOffsMenu() {
        for (Good good : GoodsManager.getFilteredGoodsInOffs()) {
            System.out.println("id: " + good.getId() + "\n" + "name: " + good.getName() + "\n" + "price: "
                    + good.getPrice() + "\n" + "off price: " + good.getPrice() * ((100 - Shop.getShop().getOffWithId(good.getOffId()).getPercent()) / 100.0)
                    + "-----------------------------------------");
        }
    }

    public static void processAddOrEditOff(boolean edit, int offId) {
        String startDate;
        String endDate;
        Date startDate1 = null;
        Date endDate1 = null;
        int discount = 0;
        List<Good> goods = new ArrayList<>();
        String id = null;
        int flag = 1;
        while (true) {
            if (flag == 1) {
                System.out.println("enter products id(enter  \"end\" to complete this step): ");
                while (!(id = Menu.scanner.nextLine()).equalsIgnoreCase("end")) {
                    if (Shop.getShop().getProductWithId(Integer.parseInt(id)) != null) {
                        goods.add(Shop.getShop().getProductWithId(Integer.parseInt(id)));
                        System.out.println("product added");
                    } else {
                        System.out.println("the product does not exist");
                    }
                }
                flag++;
            } else if (flag == 2) {
                System.out.print("Enter the start date[Month/Day/Years Hour:Minutes]: ");
                startDate = Menu.scanner.nextLine();
                if (getDateByString(startDate) != null) {
                    startDate1 = getDateByString(startDate);
                    flag++;
                } else {
                    System.out.println("format not corrected");
                }
            } else if (flag == 3) {
                System.out.print("Enter the end date[Month/Day/Years Hour:Minutes]: ");
                endDate = Menu.scanner.nextLine();
                if (getDateByString(endDate) != null) {
                    endDate1 = getDateByString(endDate);
                    flag++;
                } else {
                    System.out.println("format not corrected");
                }
            } else if (flag == 4) {
                System.out.print("Enter the off percentage: ");
                discount = Menu.scanner.nextInt();
                if (discount > 100 || discount < 0) {
                    System.out.println("format is not correct");
                } else {
                    break;
                }
            }
        }
        if (edit) {
            System.out.println("the edit off request sent");
            SellerManager.editOff(offId, goods, startDate1, endDate1, discount);
        } else {
            System.out.println("the add off request sent");
            SellerManager.addOff(goods, startDate1, endDate1, discount);
        }
    }

    public static void processEditOff() {
        System.out.print("enter off id: ");
        int id = Integer.parseInt(Menu.scanner.nextLine());
        if (Shop.getShop().getOffWithId(id) != null) {
            processAddOrEditOff(true, id);
        } else {
            System.out.println("the off is not exist");
        }
    }

    public static void showAnOff() {
        System.out.print("enter off id: ");
        int id = Integer.parseInt(Menu.scanner.nextLine());
        Off off = Shop.getShop().getOffWithId(id);
        if (off == null) {
            System.out.println("the off does not exist");
        } else {
            System.out.println(off);
        }
    }

    public static void showAllOffsForSeller() {
        for (Off off : ((Seller) AccountManager.getOnlineAccount()).getOffs()) {
            System.out.println(off);
        }
    }

    public static void processAddToCart() {
        Good currentGood = GoodsManager.getCurrentGood();
        Buyer currentBuyer = ((Buyer) AccountManager.getOnlineAccount());
        if (currentBuyer.getGoodInCartById(currentGood.getId()) == null) {
            if (currentGood.getNumber() > 0) {
                currentBuyer.getCart().add(currentGood);
                currentGood.getGoodsInBuyerCart().put(currentBuyer, 1);
                System.out.println("the product added");
            } else {
                System.out.println("this product is not available");
            }
        } else {
            System.out.println("this product has already been added");
        }
    }

    public static void processIncreaseCredit() {
        System.out.println("enter the amount of credit charge: ");
        long credit = Integer.parseInt(Menu.scanner.nextLine());
        (AccountManager.getOnlineAccount()).increaseCredit(credit);
        System.out.println("Your credit has increased by " + credit + " units");
    }
}

