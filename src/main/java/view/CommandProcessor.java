package view;

import controller.AccountManager;
import view.Menus.Menu;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandProcessor {

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
        return true;
    }

    public static boolean checkEmailInvalidation(String email) {
        return true;
    }

    public static boolean checkPhoneNumberInvalidation(String phoneNumber) {
        return true;
    }

    public static boolean checkNameInvalidation(String name) {
        return getMatcher(name, "(\\w+\\s?)+").matches();
    }

    public static boolean checkCreditInvalidation(long credit) {
        return false;
    }

    public static boolean checkPercentInvalidation(int percent) {
        return false;
    }

    public static boolean checkTypeInvalidation(String type) {
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
                AccountManager.logout();
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
                System.out.print("enter your type: ");
                String type = Menu.scanner.nextLine();
                if (checkTypeInvalidation(type)) {
                    info.add(type);
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
                    AccountManager.register(info.get(0), info.get(1), info.get(2)
                            , info.get(3), info.get(4), info.get(5), info.get(6));
                    break;
                }
            }
        }
    }

    public static void processLogin() {
    }


}
