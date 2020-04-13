package view.Menus;

import view.Menus.Menu;

import java.util.HashMap;

public class MainMenu extends Menu {

    public MainMenu() {
        super("Main menu", null);
        //subMenus = new HashMap<>();
        subMenus.put(1, new UserMenu(this));
        subMenus.put(2, new GoodsMenu(this));
        subMenus.put(3, new OffsMenu(this));
        //this.setSubMenus(subMenus);
    }

    public Menu getRegisterMenu() {
        return new Menu("register", this) {
            @Override
            public void show() {
                System.out.println("show register");
            }

            @Override
            public void execute() {
                System.out.println("exe register");
            }
        };
    }

    public Menu getLoginMenu() {
        return new Menu("login", this) {
            @Override
            public void show() {
                System.out.println("show login");
            }

            @Override
            public void execute() {
                System.out.println("exe login");
            }
        };
    }

    public Menu getLogoutMenu() {
        return new Menu("logout", this) {
            @Override
            public void show() {
                System.out.println("show logout");
            }

            @Override
            public void execute() {
                System.out.println("exe logout");
            }
        };
    }


}
