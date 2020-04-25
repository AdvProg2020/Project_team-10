package view.menus;

public class OffsMenu extends Menu {

    public OffsMenu(Menu parentMenu) {
        super("offs menu", parentMenu);
        subMenus.put(1, getShowOffs());
        subMenus.put(2 , new ProductMenu(this));
        subMenus.put(3, new FilteringMenu(this));
        subMenus.put(4, new SortMenu(this));
    }

    private LastMenu getShowOffs() {
        return new LastMenu("show offs", this) {
            @Override
            public void show() {
                super.show();
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }



}

