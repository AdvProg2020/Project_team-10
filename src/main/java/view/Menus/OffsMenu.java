package view.Menus;

public class OffsMenu extends Menu {

    public OffsMenu(Menu parentMenu) {
        super("offs menu", parentMenu);
        subMenus.put(1 , new FilteringMenu(this));
        subMenus.put(2, new FilteringMenu(this));
    }


}

