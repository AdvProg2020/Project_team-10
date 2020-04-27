package view;

import controller.AccountManager;
import controller.AdminManager;
import controller.SellerManager;
import model.*;
import view.menus.Menu;

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
        for (Account account : Shop.getShop().getAllAccounts()) {
            if (account.equals(Shop.getShop().getAccountByUsername(username))) {
                System.out.println(username + " deleted");
                Shop.getShop().getAllAccounts().remove(account);
                break;
            }
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

}
