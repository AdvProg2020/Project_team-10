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


}
