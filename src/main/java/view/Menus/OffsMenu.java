package view.Menus;

public class OffsMenu extends Menu {

    public OffsMenu(Menu parentMenu) {
        super("offs menu", parentMenu);
        subMenus.put(1 , new ProductMenu(this));
        subMenus.put(2, new FilteringMenu(this));
    }



}

