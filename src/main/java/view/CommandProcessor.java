package view;

import controller.AccountManager;
import controller.AdminManager;
import view.menus.Menu;

import java.util.ArrayList;
import java.util.Scanner;
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
            if (AccountManager.canRegister(username)) {
                return true;
            } else {
                System.out.println("username exists");
                return false;
            }
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
            if (!checkNameInvalidation(company)){
                return false;
            }

        } else if (type == 3 && !isAdminRegistered){
            typeName = "admin";
            isAdminRegistered = true;
        }else {
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
                CommandProcessor.processRegister();
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

    public static void processRegister() {
        ArrayList<String> info = new ArrayList<>();
        int flag = 1;
        Menu.scanner.nextLine();
        while (true) {
            if (flag == 1) {
                System.out.print("enter your username: ");
                String username = Menu.scanner.nextLine();
                if (checkUsernameInvalidation(username)) {
                    info.add(username);
                    flag += 1;
                }
            } else if (flag == 2) {
                System.out.print("enter your password: ");
                String password = Menu.scanner.nextLine();
                if (checkPasswordInvalidation(password)) {
                    info.add(password);
                    flag += 1;
                }
            } else if (flag == 3) {
                if (isAdminRegistered){
                    System.out.print("enter your type:\n1: buyer\n2: seller\n");
                }else{
                    System.out.print("enter your type:\n1: buyer\n2: seller\n3: admin\n");
                }
                int type = Menu.scanner.nextInt();
                Menu.scanner.nextLine();
                if (checkTypeInvalidation(type)) {
                    info.add(typeName);
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
                    AccountManager.register(info.get(0), info.get(1), info.get(2)
                            , info.get(3), info.get(4), info.get(5), info.get(6) ,info.get(7));
                    break;
                }
            }
        }
    }

    public static void processLogin() {
        if (AccountManager.getOnlineAccount() != null) {
            System.out.println("you are logged in");
        } else {
            String username = null, password;
            int flag = 0;
            Menu.scanner.nextLine();
            while (true) {
                if (flag == 0) {
                    System.out.println("enter your username: ");
                    username = Menu.scanner.nextLine();
                    flag++;
                    continue;
                } else {
                    System.out.println("enter your password: ");
                    password = Menu.scanner.nextLine();
                    break;
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

    public static void processLogout(){
        if (AccountManager.getOnlineAccount() == null){
            System.out.println("no body is logged in");
        } else {
            System.out.println("logout successful");
            AccountManager.setOnlineAccount(null);
            Menu.setIsLogged(false);
        }
    }
}
