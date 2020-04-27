package view;

import controller.AccountManager;
import controller.AdminManager;
import model.*;
import view.menus.Menu;

import java.util.*;
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
            return true;
        } else {
            System.out.println("invalid email");
            return false;
        }

    }

    public static boolean checkPhoneNumberInvalidation(String phoneNumber) {
        if (getMatcher(phoneNumber, "09\\d{9}").matches()) {
            return true;
        } else {
            System.out.println("invalid phone number");
            return false;
        }
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
            if (!checkNameInvalidation(company)) {
                return false;
            }

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

    public static void processAddDiscountCode() {
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
                Shop shop = Shop.getShop();
                while (!(people = Menu.scanner.nextLine()).equalsIgnoreCase("end")) {
                    for (Account allAccount : shop.getAllAccounts()) {
                        if (allAccount.equals(shop.getAccountByUsername(people))) {
                            allPeople.add(shop.getAccountByUsername(people));
                        } else {
                            System.out.println("username not exists");
                        }
                    }
                }
                AdminManager.createDiscount(startDate1, endDate1, percent, amount, repeat, allPeople);
                System.out.println("The discount code was successfully created");
                break;
            }
        }
    }

    private static Date getDateByString(String dateInput) {
        Calendar calendar = Calendar.getInstance();
        String regex = "(\\d\\d)\\/(\\d\\d)\\/(\\d\\d\\d\\d) (\\d\\d):(\\d\\d)";
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

    public static void printShowPersonalInfo(){
        System.out.println(AccountManager.getOnlineAccount());
    }

    public static void printShowAllDiscount(){
        for (Discount discount : Shop.getShop().getAllDiscount()) {
            System.out.println(discount);
        }
    }
}
