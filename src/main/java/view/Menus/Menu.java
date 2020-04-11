package view.Menus;

import java.util.HashMap;
import java.util.Scanner;

public abstract class Menu {
    public static Scanner scanner;
    private String name;
    protected Menu parentMenu;
    protected HashMap<Integer, Menu> subMenus;

    public Menu(String name, Menu parentMenu) {
        subMenus = new HashMap<>();
        this.name = name;
        this.parentMenu = parentMenu;
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
        if (this.parentMenu != null)
            System.out.println((subMenus.size() + 1) + ": back");
        else
            System.out.println((subMenus.size() + 1) + ": exit");
    }

    public void execute() {
        Menu nextMenu = null;
        int selectedMenu = scanner.nextInt();
        if (selectedMenu == subMenus.size() + 1) {
            if (this.parentMenu == null) {
                System.exit(1);
            } else {
                nextMenu = this.parentMenu;
            }
        } else if((selectedMenu > subMenus.size() + 1) || (selectedMenu < 1)) {
            System.out.println("you must choose one of following options");
            nextMenu = this;
        } else {
            nextMenu = subMenus.get(selectedMenu);
        }
        nextMenu.show();
        nextMenu.execute();
    }
}
