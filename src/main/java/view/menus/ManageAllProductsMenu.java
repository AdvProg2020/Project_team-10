package view.menus;

public class ManageAllProductsMenu extends Menu {
    public ManageAllProductsMenu(Menu parentMenu) {
        super("manage all products", parentMenu);
        subMenus.put(1 , getRemove());
    }

    private Menu getRemove(){
        return new LastMenu("remove" , this) {
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
