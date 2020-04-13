package view.Menus;

import controller.AccountManager;

import java.util.HashMap;
import java.util.Scanner;

public abstract class Menu {
    public static Scanner scanner;
    private String name;
    protected Menu parentMenu;
    protected HashMap<Integer, Menu> subMenus;
    protected static boolean isLogged = true;
    protected static MainMenu mainMenu;

    public Menu(String name, Menu parentMenu) {
        subMenus = new HashMap<>();
        this.name = name;
        this.parentMenu = parentMenu;
    }

    public static void setMainMenu(MainMenu mainMenu) {
        Menu.mainMenu = mainMenu;
    }

    public static void setIsLogged(boolean isLogged) {
        Menu.isLogged = isLogged;
    }

    public void setSubMenus(HashMap<Integer, Menu> subMenus) {
        this.subMenus = subMenus;
    }

    public String getName() {
        return name;
    }

    public static void setScanner(Scanner scanner) {
        Menu.scanner = scanner;
    }

    public void show() {

        System.out.println(this.getName());
        for (Integer subNumber : subMenus.keySet()) {
            System.out.println(subNumber + ": " + subMenus.get(subNumber).getName());
        }
        if (isLogged) {
            System.out.println((subMenus.size() + 1) + ": logout");
            if (this.parentMenu != null) {
                System.out.println((subMenus.size() + 2) + ": back");
            } else {
                System.out.println((subMenus.size() + 2) + ": exit");
            }
        } else {
            System.out.println((subMenus.size() + 1) + ": register");
            System.out.println((subMenus.size() + 2) + ": login");
            if (this.parentMenu != null) {
                System.out.println((subMenus.size() + 3) + ": back");
            } else {
                System.out.println((subMenus.size() + 3) + ": exit");
            }
        }
    }

    public void execute() {
        Menu nextMenu = null;
        int selectedMenu = scanner.nextInt();
        if (isLogged) {
            if (selectedMenu == subMenus.size() + 2) {
                if (this.parentMenu == null) {
                    System.exit(1);
                } else {
                    nextMenu = this.parentMenu;
                }
            } else if ((selectedMenu > subMenus.size() + 2) || (selectedMenu < 1)) {
                System.out.println("you must choose one of following options");
                nextMenu = this;
            } else {
                if (selectedMenu == subMenus.size() + 1) {
                    mainMenu.getLogoutMenu().show();
                    mainMenu.getLogoutMenu().execute();
                    nextMenu = mainMenu;
                } else {
                    nextMenu = subMenus.get(selectedMenu);
                }
            }
        } else {
            if (selectedMenu == subMenus.size() + 3) {
                if (this.parentMenu == null) {
                    System.exit(1);
                } else {
                    nextMenu = this.parentMenu;
                }
            } else if ((selectedMenu > subMenus.size() + 3) || (selectedMenu < 1)) {
                System.out.println("you must choose one of following options");
                nextMenu = this;
            } else {
                if (selectedMenu == subMenus.size() + 1) {
                   mainMenu.getRegisterMenu().show();
                   mainMenu.getRegisterMenu().execute();
                    nextMenu = this;
                } else if (selectedMenu == subMenus.size() + 2) {
                   mainMenu.getLoginMenu().show();
                   mainMenu.getLoginMenu().execute();
                    nextMenu = this;
                } else {
                    nextMenu = subMenus.get(selectedMenu);
                }
            }

        }
        nextMenu.show();
        nextMenu.execute();
    }


}
