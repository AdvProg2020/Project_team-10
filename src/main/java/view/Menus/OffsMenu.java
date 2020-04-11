package view.Menus;

public class OffsMenu extends Menu {

    public OffsMenu(Menu parentMenu) {
        super("offs menu", parentMenu);
        //product page
        subMenus.put(2, new FilteringMenu(this));
    }


}

